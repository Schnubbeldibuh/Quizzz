package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.multiplayer.core.ServerClientFactory;
import de.dhbw.ase.user.in.UserIn;

public class MultiplayerQuickServerClientFactory implements ServerClientFactory {

    private final UserIn sc;

    public MultiplayerQuickServerClientFactory(UserIn sc) {
        this.sc = sc;
    }

    @Override
    public MultiplayerServer createServer() {
        return new MultiplayerQuickServer(Quizzz.SERVER_PORT);
    }

    @Override
    public MultiplayerClient createClient(String username) {
        return new MultiplayerQuickClient(sc, username, Quizzz.FILE_STATS_MP_QUICK);
    }
}