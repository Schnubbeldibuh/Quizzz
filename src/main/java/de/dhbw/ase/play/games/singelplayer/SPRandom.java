package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.user.in.UserIn;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SPRandom extends SingleplayerGame {

    private final List<SPQuiz> spGamesList;
    private SPQuiz randomGame;

    public SPRandom(UserIn sc, List<SPQuiz> spGamesList) {
        super(sc);
        this.spGamesList = spGamesList;
    }

    @Override
    protected void startGame() {
        Collections.shuffle(spGamesList);
        randomGame = spGamesList.get(0);
        randomGame.startGame();
    }

    @Override
    protected String getStatsFilesPath() {
        return randomGame.getStatsFilesPath();
    }

    @Override
    protected void writeStats() {
        randomGame.writeStats();
    }
}
