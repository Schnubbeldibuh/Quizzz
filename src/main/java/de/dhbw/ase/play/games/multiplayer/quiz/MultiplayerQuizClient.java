package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.stats.persistance.PlayerStatsMPObject;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;

public class MultiplayerQuizClient extends MultiplayerClient {

    private boolean discardUserinput;

    public MultiplayerQuizClient(UserIn sc, String username, String filepath) {
        super(sc, username, filepath);

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

        if (discardUserinput) {
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
                discardUserinput = true;

                boolean evaluation =
                        Boolean.parseBoolean(input.substring(CommunicationPrefixes.ANSWER_EVALUATION.getLength()));

                if (evaluation) {
                    System.out.println("Die Antwort ist richtig :)");
                } else {
                    System.out.println("DU DUMME SAU");
                }

                break;

            case NEXT_QUESTION:
                discardUserinput = false;
                showQuestion(input);
                break;

            case STATS_TRANSFER:
                stats.add(
                        new PlayerStatsMPObject(input.substring(CommunicationPrefixes.STATS_TRANSFER.getLength())));
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
        if (discardUserinput) {
            return false;
        }

        return super.processUserInput(input);
    }
}
