package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.stats.persistance.PlayerStatsMPObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiplayerQuizClient extends MultiplayerClient {

    private boolean discardUserinput;

    public MultiplayerQuizClient(Scanner sc, String username, String filepath) {
        super(username, filepath);

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
        List<MultiplayerClient.Source> sourceList = new ArrayList<>();

        switch (CommunicationPrefixes.evaluateCase(input)) {
            case ROUND_FINISHED:
                return false;

            case START_GAME:
                System.out.println("Das Spiel startet.");
                sourceList.add(Source.SERVER);
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

                sourceList.add(Source.SERVER);
                break;

            case NEXT_QUESTION:
                discardUserinput = false;
                showQuestion(input);
                sourceList.add(Source.USER);
                break;

            case STATS_TRANSFER:
                stats.add(
                        new PlayerStatsMPObject(input.substring(CommunicationPrefixes.STATS_TRANSFER.getLength())));
                sourceList.add(Source.SERVER);
                break;

            case STATS_TRANSFER_FINISHED:
                writeStats();
                stats = new ArrayList<>();
                sourceList.add(Source.SERVER);
                break;
        }

        return true;
    }

    @Override
    protected boolean processUserInput(String input) {
        if (discardUserinput) {
            return false;
        }

        Integer selection = switch (input) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + input);
        };
        sendMessageToServer(CommunicationPrefixes.ANSWER.toString() + selection);
        List<MultiplayerClient.Source> sourceList = new ArrayList<>();
        sourceList.add(Source.SERVER);

        return true;
    }
}
