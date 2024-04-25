package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.multiplayer.core.ServerClientFactory;
import de.dhbw.ase.repository.QuestionRepositoryFilebased;
import de.dhbw.ase.repository.StatsRepositoryFilebased;
import de.dhbw.ase.user.in.UserIn;

public class MultiplayerQuizServerClientFactory implements ServerClientFactory {

    public static final String GAMEMODE = "normal";

    private final UserIn userIn;

    public MultiplayerQuizServerClientFactory(UserIn sc) {
        userIn = sc;
    }

    @Override
    public MultiplayerServer createServer() {
        return new MultiplayerQuizServer(
                Quizzz.SERVER_PORT,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_MP),
                GAMEMODE);
    }

    @Override
    public MultiplayerClient createClient(String username) {
        return new MultiplayerQuizClient(
                userIn, username,
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_MP),
                GAMEMODE);
    }
}
