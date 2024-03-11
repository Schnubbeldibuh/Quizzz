package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;

import java.util.List;
import java.util.Scanner;

public class MultiplayerQuizClient extends MultiplayerClient {
    public MultiplayerQuizClient(Scanner sc, String username) {
        super(sc, username);
    }

    @Override
    protected boolean checkUserInput(String input) {
        return false;
    }

    @Override
    protected boolean checkServerInput(String input) {
        return false;
    }

    @Override
    protected List<MultiplayerClient.Source> processServerInput(String input) {
        return null;
    }

    @Override
    protected List<MultiplayerClient.Source> processUserInput(String input) {
        return null;
    }
}
