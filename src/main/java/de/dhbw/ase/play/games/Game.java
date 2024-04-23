package de.dhbw.ase.play.games;

import de.dhbw.ase.Startable;
import de.dhbw.ase.user.in.UserIn;

public abstract class Game implements Startable {
    // TODO zu util klasse umbauen und aus der Vererbungskette nehmen

    private final UserIn sc;
    private String username;

    public Game(UserIn sc) {
        this.sc = sc;
    }


    protected void indicateUser () {
        System.out.println();
        System.out.println("Bitte gebe deinen Usernamen ein:");
        username = sc.waitForNextLine(this);

    }

    protected boolean askUserForRetry() {
        System.out.println("1 - Erneut spielen");
        System.out.println("2 - Zurück zum Menü");

        // Es ist gewollt das alles außer 1 als zurück zum Menü interpretiert wird
        return sc.waitForNextLine(this).equals("1");
    }

    public String getUsername() {
        return username;
    }
}
