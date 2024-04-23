package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerGame;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.user.in.UserIn;

public class MultiplayerQuick extends MultiplayerGame {
    public MultiplayerQuick(UserIn sc) {
        super(sc);
    }

    @Override
    protected MultiplayerServer createServer() {
        return new MultiplayerQuickServer(Quizzz.SERVER_PORT);
    }

    @Override
    protected MultiplayerClient createClient(String username, MultiplayerServer server) {
        return new MultiplayerQuickClient(username, Quizzz.FILE_STATS_MP_QUICK);
    }
}
