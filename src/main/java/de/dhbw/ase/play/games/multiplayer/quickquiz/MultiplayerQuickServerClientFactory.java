package de.dhbw.ase.play.games.multiplayer.quickquiz;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerClient;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerServer;
import de.dhbw.ase.play.games.multiplayer.core.ServerClientFactory;
import de.dhbw.ase.repository.QuestionRepositoryFilebased;
import de.dhbw.ase.repository.StatsRepositoryFilebased;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiplayerQuickServerClientFactory implements ServerClientFactory {

    public static final String GAMEMODE = "quick";

    private final UserIn sc;

    public MultiplayerQuickServerClientFactory(UserIn sc) {
        this.sc = sc;
    }

    @Override
    public MultiplayerServer createServer() {
        return new MultiplayerQuickServer(
                Quizzz.SERVER_PORT,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_MP),
                GAMEMODE);
    }

    @Override
    public MultiplayerClient createClient(String username) {

        return new MultiplayerQuickClient(
                sc, username,
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_MP_QUICK),
                List.of(CommunicationPrefixes.ANSWER_EVALUATION,
                        CommunicationPrefixes.NEXT_QUESTION,
                        CommunicationPrefixes.ROUND_FINISHED,
                        CommunicationPrefixes.START_GAME,
                        CommunicationPrefixes.RIGHT_ANSWER,
                        CommunicationPrefixes.STATS_TRANSFER_FINISHED,
                        CommunicationPrefixes.STATS_TRANSFER),
                GAMEMODE);
    }
}