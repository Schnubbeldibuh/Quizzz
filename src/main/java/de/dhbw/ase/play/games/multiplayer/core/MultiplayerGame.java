package de.dhbw.ase.play.games.multiplayer.core;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.Game;
import de.dhbw.ase.user.in.UserIn;

import java.net.UnknownHostException;

public class MultiplayerGame extends Game {

    private final UserIn sc;
    private final ServerClientFactory factory;
    private boolean host;
    private MultiplayerServer server;
    private MultiplayerClient client;

    private record ServerInfos(String host, int port) {}

    public MultiplayerGame(UserIn sc, ServerClientFactory factory) {
        super(sc);
        this.sc = sc;
        this.factory = factory;
    }

    @Override
    public SelectedMenu.MenuSelection start() {

        try {
            host = askForHost();
        } catch (ExitException e) {
            return SelectedMenu.MenuSelection.EXIT;
        }
        boolean contine;
        do {
            if (host) {
                try {
                    startServerAndResgisterHostclient();
                } catch (ExitException e) {
                    exit();
                    return SelectedMenu.MenuSelection.BACK;
                }
            } else {
                if (client == null) {
                    try {
                        registerClient();
                    } catch (ExitException e) {
                        exit();
                        return SelectedMenu.MenuSelection.BACK;
                    }
                }
                System.out.println();
                System.out.println("Warte auf den Host");
                System.out.println(("<exit> eingeben um zurück ins Menü zu gelangen"));
            }
            try {
                contine = client.start();
            } catch (ExitException e) {
                if (server != null) {
                    server.shutdown();
                }
                exit();
                return SelectedMenu.MenuSelection.BACK;
            }
            if (host)
                server.advanceGamestate();

        } while (contine && askUserForRetry());

        exit();
        return SelectedMenu.MenuSelection.BACK;
    }

    private void registerClient() throws ExitException {
        ServerInfos serverInfos;
        do {
            serverInfos = askForServerInfos();
            do {
                indicateUser();
                client = factory.createClient(getUsername());
                boolean successfullyRegistered;
                try {
                    successfullyRegistered = client.registerClient(serverInfos.host(), serverInfos.port());
                } catch (UnknownHostException e) {
                    System.out.println("Der Host existiert nicht. Bitte erneut versuchen");
                    serverInfos = null;
                    continue;
                } catch (UsernameAlreadyExistsException e) {
                    System.out.println("Username ist bereits vergeben. Bitte einen anderen wählen.");
                    client = null;
                    continue;
                }
                if (!successfullyRegistered) {
                    client = null;
                    System.out.println("Der Host hat unerwartet die Verbindung getrennt.");
                    return;
                }
                System.out.println("Erfolgreich beigetreten");
            } while (client == null);
        } while (serverInfos == null);
    }

    private void exit() {
        if (client != null) {
            client.disconnectClient();
        }

        if (host && server != null) {
            server.shutdown();
        }

        server = null;
        client = null;
    }

    private void startServerAndResgisterHostclient() throws ExitException {
        if (getUsername() == null) {
            indicateUser();
        }
        System.out.println("Zum Starten bitte <start> eingeben.");
        System.out.println("Folgende SpielerInnen sind bereits beigetreten:");
        if (server == null) {
            // no previous game was played -> new server has to be started
            server = startServer();
            client = factory.createClient(getUsername());
            try {
                client.registerClient("localhost", Quizzz.SERVER_PORT);
            } catch (UsernameAlreadyExistsException | UnknownHostException ignored) {
            }
        } else {
            server.advanceGamestate();
            server.getUsernames().forEach(System.out::println);
        }
        String s;
        do {
            s = sc.waitForNextLine(this);
            if (s.equals("exit")) {
                throw new ExitException();
            }
        } while (!s.equals("start"));

        server.advanceGamestate();
    }

    private MultiplayerServer startServer() {
        MultiplayerServer server;
        server = factory.createServer();
        server.advanceGamestate();
        return server;
    }

    private ServerInfos askForServerInfos() throws ExitException {
        ServerInfos serverInfos;
        do {
            System.out.println("Bitte gebe den Hostnamen und Port an in der Form <hostname:port>.");
            String input = sc.waitForNextLine(this);
            if (input.equals("exit")) {
                throw new ExitException();
            }
            serverInfos = parseServerInfos(input);
            if (serverInfos != null) {
                return serverInfos;
            }

            System.out.println("Fehlerhafte Eingabe, bitte erneut eingeben.");
        } while (true);
    }

    private ServerInfos parseServerInfos(String input) {
        String[] split = input.split(":");
        if (split.length != 2)
            return null;
        int port;
        try {
            port = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            return null;
        }
        return new ServerInfos(split[0], port);
    }

    private boolean askForHost() throws ExitException {
        System.out.println();
        System.out.println("1 - Hosten");
        System.out.println("2 - Beitreten");

        Boolean p;
        do {
            String input = sc.waitForNextLine(this);
            p = switch(input) {
                case "1" -> true;
                case "2" -> false;
                case "exit" -> throw new ExitException();
                default -> null;
            };
        } while (p == null);
        return p;
    }
}
