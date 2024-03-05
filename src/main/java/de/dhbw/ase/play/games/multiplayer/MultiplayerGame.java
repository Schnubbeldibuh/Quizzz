package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.Game;

import java.util.Scanner;

public abstract class MultiplayerGame extends Game {

    private final Scanner sc;
    private final MultiplayerClient client;
    private final MultiplayerServer server;

    public MultiplayerGame(Scanner sc, MultiplayerClient client, MultiplayerServer server) {
        super(sc);
        this.sc = sc;
        this.client = client;
        this.server = server;
    }

    @Override
    protected SelectedMenu.MenuSelection startGameMenu() {
        boolean host = askForHost();

        if (host) {
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
        client.start();
        // TODO
        // kennen müssen sich client und server nicht. Das wird über die verbindung gemacht
        return SelectedMenu.MenuSelection.BACK;
    }

    private boolean askForHost() {
        System.out.println();
        System.out.println("1 - Hosten");
        System.out.println("2 - Beitreten");

        Boolean p;
        do {
            String input = sc.nextLine();
            p = switch(input) {
                case "1" -> true;
                case "2" -> false;
                default -> null;
            };
        } while (p == null);
        return p;
    }
}
