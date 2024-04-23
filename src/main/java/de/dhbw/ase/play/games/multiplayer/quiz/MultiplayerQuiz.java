package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerGame;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.user.in.UserIn;

public class MultiplayerQuiz extends MultiplayerGame {

    public MultiplayerQuiz(UserIn sc) {
        super(sc);
    }

    @Override
    protected MultiplayerServer createServer() {
        return new MultiplayerQuizServer(Quizzz.SERVER_PORT);
    }

    @Override
    protected MultiplayerClient createClient(String username, MultiplayerServer server) {
        return new MultiplayerQuizClient(username, Quizzz.FILE_STATS_MP);
    }
}
