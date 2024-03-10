package de.dhbw.ase.play.games;

import de.dhbw.ase.Startable;

import java.util.Scanner;

public abstract class Game implements Startable {
    // TODO zu util klasse umbauen und aus der Vererbungskette nehmen

    private final Scanner sc;
    private String username;

    public Game(Scanner sc) {
        this.sc = sc;
    }


    protected void indicateUser () {
        System.out.println();
        System.out.println("Bitte gebe deinen Usernamen ein:");
        username = sc.next();
    }

    protected boolean askUserForRetry() {
        System.out.println("1 - Erneut spielen");
        System.out.println("2 - Zurück zum Menü");

        // Es ist gewollt das alles außer 1 als zurück zum Menü interpretiert wird
        return sc.next().equals("1");
    }

    public String getUsername() {
        return username;
    }
}
