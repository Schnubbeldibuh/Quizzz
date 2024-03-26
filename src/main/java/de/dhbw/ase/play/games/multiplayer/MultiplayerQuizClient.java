package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiplayerQuizClient extends MultiplayerClient {
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
        return input.startsWith("Answer evaluation:")
                || input.startsWith("Next question:")
                || input.startsWith("Round is finished!")
                || input.startsWith("start game");
    }

    @Override
    protected List<MultiplayerClient.Source> processServerInput(String input) {
        if (input.startsWith("Round is finished!")) {
            return new ArrayList<>();
        }
        List<MultiplayerClient.Source> sourceList = new ArrayList<>();
        if (input.startsWith("start game")) {
            System.out.println("Das Spiel startet.");
            sourceList.add(Source.SERVER);
            return sourceList;
        }
        if (input.startsWith("Answer evaluation:")) {
            boolean evaluation = Boolean.parseBoolean(input.substring("Answer evaluation:".length()));
            if (evaluation) {
                System.out.println("Die Antwort ist richtig :)");
            } else {
                System.out.println("DU DUMME SAU");
            }
            sourceList.add(Source.SERVER);
            return sourceList;
        }
        if (input.startsWith("Next question:")) {
            String question = input.substring("Next question:".length());
            String[] questionAnswersArray = question.split(";");
            System.out.println("Frage: " + questionAnswersArray[0]);
            System.out.println("A: " + questionAnswersArray[1]);
            System.out.println("B: " + questionAnswersArray[2]);
            System.out.println("C: " + questionAnswersArray[3]);
            System.out.println("D: " + questionAnswersArray[4]);
        }
        sourceList.add(Source.USER);
        return sourceList;
    }

    @Override
    protected List<MultiplayerClient.Source> processUserInput(String input) {
        Integer selection = switch (input) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + input);
        };
        sendMessageToServer("Answer:" + selection);
        List<MultiplayerClient.Source> sourceList = new ArrayList<>();
        sourceList.add(Source.SERVER);
        return sourceList;
    }
}
