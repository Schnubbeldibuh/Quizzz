package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiplayerQuizClient extends MultiplayerClient {

    public MultiplayerQuizClient(Scanner sc, String username) {
        super(sc, username);

        validServerMessages.add(CommunicationPrefixes.ANSWER_EVALUATION);
        validServerMessages.add(CommunicationPrefixes.NEXT_QUESTION);
        validServerMessages.add(CommunicationPrefixes.ROUND_FINISHED);
        validServerMessages.add(CommunicationPrefixes.START_GAME);
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
