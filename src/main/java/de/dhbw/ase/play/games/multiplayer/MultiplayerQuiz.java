package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerGame;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

import java.util.Scanner;

public class MultiplayerQuiz extends MultiplayerGame {

    Scanner sc;
    public MultiplayerQuiz(Scanner sc) {
        super(sc);
        this.sc = sc;
    }

    @Override
    protected MultiplayerServer createServer() {
        return new MultiplayerQuizServer(Quizzz.SERVER_PORT);
    }

    @Override
    protected MultiplayerClient createClient(String username, MultiplayerServer server) {
        return new MultiplayerQuizClient(sc, username);
    }
}
