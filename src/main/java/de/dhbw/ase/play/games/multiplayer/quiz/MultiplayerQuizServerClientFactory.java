package de.dhbw.ase.play.games.multiplayer.quiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.multiplayer.core.ServerClientFactory;
import de.dhbw.ase.repository.QuestionRepositoryFilebased;
import de.dhbw.ase.repository.StatsRepositoryFilebased;
import de.dhbw.ase.stats.persistance.PlayerStatsFQObject;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                List.of(CommunicationPrefixes.ANSWER_EVALUATION,
                        CommunicationPrefixes.NEXT_QUESTION,
                        CommunicationPrefixes.ROUND_FINISHED,
                        CommunicationPrefixes.START_GAME,
                        CommunicationPrefixes.STATS_TRANSFER_FINISHED,
                        CommunicationPrefixes.STATS_TRANSFER),
                GAMEMODE);
    }
}
