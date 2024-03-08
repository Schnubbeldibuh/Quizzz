package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.Game;

import java.util.Scanner;

public abstract class SingeplayerGame extends Game {

    private final Scanner sc;

    public SingeplayerGame(Scanner sc) {
        super(sc);
        this.sc = sc;
    }

    protected abstract void startGame();

    @Override
    protected SelectedMenu.MenuSelection startGameMenu() {
        do {
            startGame();
        } while (askUserForRetry());
        return SelectedMenu.MenuSelection.BACK;
    }
}
