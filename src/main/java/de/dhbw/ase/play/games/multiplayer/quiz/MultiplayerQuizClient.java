package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiplayerQuizClient extends MultiplayerClient {

    private final CommunicationPrefixes[] validServerMessages = {
            CommunicationPrefixes.ANSWER_EVALUATION,
            CommunicationPrefixes.NEXT_QUESTION,
            CommunicationPrefixes.ROUND_FINISHED,
            CommunicationPrefixes.START_GAME
    };

    public MultiplayerQuizClient(Scanner sc, String username) {
        super(sc, username);
    }

    @Override
    protected boolean checkUserInput(String input) throws ExitException {
        if (input.equalsIgnoreCase("exit")) {
            throw new ExitException();
        }
        return input.equalsIgnoreCase("a")
                || input.equalsIgnoreCase("b")
                || input.equalsIgnoreCase("c")
                || input.equalsIgnoreCase("d");
    }

    @Override
    protected boolean checkServerInput(String input) {
        CommunicationPrefixes communicationPrefixes;

        try {
            communicationPrefixes = CommunicationPrefixes.evaluateCase(input);
        } catch (IllegalArgumentException e) {
            return false;
        }

        for (CommunicationPrefixes c: validServerMessages) {
            if (c == communicationPrefixes) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected List<MultiplayerClient.Source> processServerInput(String input) {
        List<MultiplayerClient.Source> sourceList = new ArrayList<>();

        switch (CommunicationPrefixes.evaluateCase(input)) {
            case ROUND_FINISHED:
                return new ArrayList<>();

            case START_GAME:
                System.out.println("Das Spiel startet.");
                sourceList.add(Source.SERVER);
                break;

            case ANSWER_EVALUATION:
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
                showQuestion(input);
                sourceList.add(Source.USER);
                break;
        }

        return sourceList;
    }
}
