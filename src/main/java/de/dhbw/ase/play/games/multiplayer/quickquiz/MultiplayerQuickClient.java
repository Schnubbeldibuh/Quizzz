package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiplayerQuickClient extends MultiplayerClient {

    public MultiplayerQuickClient(Scanner sc, String username) {
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
                || input.startsWith("start game")
                || input.startsWith("Right answer:");
    }

    @Override
    protected List<Source> processServerInput(String input) {
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
        if (input.startsWith("Right answer:")) {
            System.out.println(input.substring("Right answer:".length()) + " hat richtig geantwortet.");
            sourceList.add(Source.SERVER);
            return sourceList;
        }
        if (input.startsWith("Next question:")) {
            showQuestion(input);
        }
        sourceList.add(Source.USER);
        sourceList.add(Source.SERVER);
        return sourceList;
    }
}
