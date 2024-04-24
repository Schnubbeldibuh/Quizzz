package de.dhbw.ase.play.games;

import de.dhbw.ase.Startable;
import de.dhbw.ase.user.in.UserIn;

public abstract class Game implements Startable {

    private final UserIn sc;
    private String username;

    public Game(UserIn sc) {
        this.sc = sc;
    }


    protected void indicateUser () throws ExitException {
        System.out.println();
        System.out.println("Bitte gebe deinen Usernamen ein:");
        String temp = sc.waitForNextLine(this);
        if (temp.equalsIgnoreCase("exit")){
            throw new ExitException();
        }

        username = temp;
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
