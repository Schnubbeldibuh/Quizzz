package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.multiplayer.core.ServerClientFactory;
import de.dhbw.ase.user.in.UserIn;

public class MultiplayerQuizServerClientFactory implements ServerClientFactory {

    private final UserIn userIn;

    public MultiplayerQuizServerClientFactory(UserIn sc) {
        userIn = sc;
    }

    @Override
    public MultiplayerServer createServer() {
        return new MultiplayerQuizServer(Quizzz.SERVER_PORT);
    }

    @Override
    public MultiplayerClient createClient(String username) {
        return new MultiplayerQuizClient(userIn, username, Quizzz.FILE_STATS_MP);
    }
}
