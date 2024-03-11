package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerGame;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

import java.util.Scanner;

public class MultiplayerQuiz extends MultiplayerGame {

    public MultiplayerQuiz(Scanner sc) {
        super(sc);
    }

    @Override
    protected MultiplayerServer createServer() {
        return null;
    }

    @Override
    protected MultiplayerClient createClient(String Username, MultiplayerServer server) {
        return null;
    }
}
