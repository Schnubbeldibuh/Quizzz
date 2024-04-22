package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.SelectedMenu;

import java.util.Scanner;

public class SPRandom extends SingleplayerGame {

    private String filePath;
    public SPRandom(Scanner sc) {
        super(sc);
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
