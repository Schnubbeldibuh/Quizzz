package de.dhbw.ase.play.games;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;

import java.util.Scanner;

public abstract class Game implements Startable {

    private final Scanner sc;
    private String username;

    public Game(Scanner sc) {
        this.sc = sc;
    }

    protected abstract void startGame();
    @Override
    public SelectedMenu.MenuSelection start() {
        indicateUser();
        startGame();
        return SelectedMenu.MenuSelection.BACK;
    }

    private void indicateUser () {
        System.out.println("Bitte gebe deinen Usernamen ein:");
        username = sc.next();
    }
}
