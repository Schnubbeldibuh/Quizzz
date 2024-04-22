package de.dhbw.ase.play.games.singelplayer;

import java.util.Scanner;

public class SPQuiz extends SingleplayerGame {

    private final String filePath;
    public SPQuiz(Scanner sc, String filePath) {
        super(sc);
        this.filePath = filePath;
    }

    @Override
    protected void startGame() {

    }

    @Override
    protected String getStatsFilesPath() {
        return null;
    }

    @Override
    protected void writeStats() {

    }
}
