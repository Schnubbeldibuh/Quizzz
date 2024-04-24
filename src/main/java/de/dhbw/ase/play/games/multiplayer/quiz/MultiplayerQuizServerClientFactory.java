package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.multiplayer.core.ServerClientFactory;
import de.dhbw.ase.play.games.repository.QuestionRepositoryFilebased;
import de.dhbw.ase.play.games.repository.StatsRepositoryFilebased;
import de.dhbw.ase.user.in.UserIn;

public class MultiplayerQuizServerClientFactory implements ServerClientFactory {

    private final UserIn userIn;

    public MultiplayerQuizServerClientFactory(UserIn sc) {
        userIn = sc;
    }

    @Override
    public MultiplayerServer createServer() {
        return new MultiplayerQuizServer(Quizzz.SERVER_PORT, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_MP));
    }

    @Override
    public MultiplayerClient createClient(String username) {
        return new MultiplayerQuizClient(userIn, username, StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_MP));
    }
}
