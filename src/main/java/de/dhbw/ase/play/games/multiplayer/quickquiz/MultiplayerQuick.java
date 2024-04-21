package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerGame;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;

import java.util.Scanner;

public class MultiplayerQuick extends MultiplayerGame {
    private final Scanner sc;
    public MultiplayerQuick(Scanner sc) {
        super(sc);
        this.sc = sc;
    }

    @Override
    protected MultiplayerServer createServer() {
        return new MultiplayerQuickServer(Quizzz.SERVER_PORT);
    }

    @Override
    protected MultiplayerClient createClient(String username, MultiplayerServer server) {
        return new MultiplayerQuickClient(sc, username, Quizzz.FILE_STATS_MP_QUICK);
    }
}
