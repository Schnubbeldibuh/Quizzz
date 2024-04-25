package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.PlayerStatsMPObject;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiplayerQuizClient extends MultiplayerClient {

    protected final StatsRepository statsRepository;
    protected List<PlayerStatsMPObject> stats = new ArrayList<>();

    public MultiplayerQuizClient(UserIn sc, String username, StatsRepository statsRepository, String gameMode) {
        super(sc, username, gameMode);
        this.statsRepository = statsRepository;

        validServerMessages.add(CommunicationPrefixes.ANSWER_EVALUATION);
        validServerMessages.add(CommunicationPrefixes.NEXT_QUESTION);
        validServerMessages.add(CommunicationPrefixes.ROUND_FINISHED);
        validServerMessages.add(CommunicationPrefixes.START_GAME);
        validServerMessages.add(CommunicationPrefixes.STATS_TRANSFER_FINISHED);
        validServerMessages.add(CommunicationPrefixes.STATS_TRANSFER);
    }

    @Override
    protected boolean checkUserInput(String input) throws ExitException {
        if (input.equalsIgnoreCase("exit")) {
            throw new ExitException();
        }

        if (isDiscardUserinput()) {
            return false;
        }

        return input.equalsIgnoreCase("a")
                || input.equalsIgnoreCase("b")
                || input.equalsIgnoreCase("c")
                || input.equalsIgnoreCase("d");
    }

    @Override
    protected boolean processServerInput(String input) {

        switch (CommunicationPrefixes.evaluateCase(input)) {
            case ROUND_FINISHED:
                return false;

            case START_GAME:
                System.out.println("Das Spiel startet.");
                break;

            case ANSWER_EVALUATION:
                setDiscardUserinput(true);

                boolean evaluation =
                        Boolean.parseBoolean(input.substring(CommunicationPrefixes.ANSWER_EVALUATION.getLength()));

                if (evaluation) {
                    System.out.println("Die Antwort ist richtig :)");
                } else {
                    System.out.println("DU DUMME SAU");
                }

                break;

            case NEXT_QUESTION:
                setDiscardUserinput(false);
                showQuestion(input);
                break;

            case STATS_TRANSFER:
                stats.add(
                        PlayerStatsMPObject.fromLine(
                                input.substring(CommunicationPrefixes.STATS_TRANSFER.getLength())));
                break;

            case STATS_TRANSFER_FINISHED:
                writeStats();
                stats = new ArrayList<>();
                break;
        }

        return true;
    }

    @Override
    protected boolean processUserInput(String input) {
        if (isDiscardUserinput()) {
            return false;
        }

        return super.processUserInput(input);
    }

    protected void writeStats() {
        List<PlayerStatsMPObject> file;
        try {
            file = new ArrayList<>(statsRepository.readStats(PlayerStatsMPObject::fromLine));
        } catch (CouldNotAccessFileException e) {
            System.out.println("Beim speichern der Stats ist ein Fehler aufgetreten.");
            System.out.println("Die Stats wurden nicht gespeichert.");
            return;
        }

        for (PlayerStatsMPObject p : stats) {
            int index = file.indexOf(p);
            if (index == -1) {
                file.add(p);
            } else {
                PlayerStatsMPObject oldStats = file.get(index);
                oldStats.add(p);
            }
        }

        Collections.sort(file);
        Collections.reverse(file);

        try {
            statsRepository.writeStats(file);
        } catch (CouldNotAccessFileException e) {
            System.out.println("Beim speichern der Stats ist ein Fehler aufgetreten.");
            System.out.println("Die Stats wurden möglicherweise nicht vollständig gespeichert.");
            return;
        }
    }
}
