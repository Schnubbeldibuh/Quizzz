package de.dhbw.ase.play.games.multiplayer.core;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.Game;
import de.dhbw.ase.user.in.UserIn;

import java.net.UnknownHostException;

public abstract class MultiplayerGame extends Game {

    private final UserIn sc;

    private record ServerInfos(String host, int port) {}

    public MultiplayerGame(UserIn sc) {
        super(sc);
        this.sc = sc;
    }

    protected abstract MultiplayerServer createServer();
    protected abstract MultiplayerClient createClient(String username, MultiplayerServer server);

    @Override
    public SelectedMenu.MenuSelection start() {

        boolean host;
        try {
            host = askForHost();
        } catch (ExitException e) {
            return SelectedMenu.MenuSelection.BACK;
        }
        MultiplayerServer server = null;
        MultiplayerClient client = null;
        do {
            if (host) {
                indicateUser();
                System.out.println("Zum Starten bitte <start> eingeben.");
                System.out.println("Folgende SpielerInnen sind bereits beigetreten:");
                if (server == null) {
                    // no previous game was played -> new server has to be started
                    server = createServer();
                    server.advanceGamestate();
                    client = createClient(getUsername(), server);
                    try {
                        client.registerClient("localhost", Quizzz.SERVER_PORT); // TODO rückgabewert verarbeiten
                    } catch (UsernameAlreadyExistsException | UnknownHostException e) {
                        /* TODO was tun wenn hier die Registrierung nicht klappt?
                        *   muss ein technischer Fehler sein*/
                    }
                } else {
                    server.advanceGamestate();
                    server.getUsernames().forEach(System.out::println);
                }
                String s;
                do {
                    s = sc.waitForNextLine(this);
                } while (!s.equals("start"));
                //TODO chekc for exit
                server.advanceGamestate();
            } else if (client == null){
                ServerInfos serverInfos;
                do {
                    try {
                        serverInfos = askForServerInfos();
                    } catch (ExitException e) {
                        return SelectedMenu.MenuSelection.BACK;
                    }
                    do {
                        indicateUser();
                        client = createClient(getUsername(), null);
                        try {
                            client.registerClient(serverInfos.host(), serverInfos.port()); // TODO Rückgabewert verarbeiten
                        } catch (UnknownHostException e) {
                            System.out.println("Der Host existiert nicht. Bitte erneut versuchen");
                            serverInfos = null;
                        } catch (UsernameAlreadyExistsException e) {
                            System.out.println("Username ist bereits vergeben. Bitte einen anderen wählen.");
                            client = null;
                        }

                    } while (client == null);
                } while (serverInfos == null);
                // TODO was tun wenn hier die Registrierung nicht klappt?
            }
            try {
                client.start();
            } catch (ExitException e) {
                if (server != null) {
                    server.shutdown();
                }
                client.disconnectClient();
                return SelectedMenu.MenuSelection.BACK;
            }
            if (host)
                server.advanceGamestate();

        } while (askUserForRetry());
        client.disconnectClient();

        if (host)
            server.shutdown();
        return SelectedMenu.MenuSelection.BACK;
    }

    private ServerInfos askForServerInfos() throws ExitException {
        ServerInfos serverInfos;
        do {
            System.out.println("Bitte gebe den Hostnamen und Port an in der Form <hostname:port>.");
            String input = sc.waitForNextLine(this);
            if (input.equals("exit"))
                throw new ExitException();
            serverInfos = parseServerInfos(input);
            if (serverInfos != null)
                return serverInfos;

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
