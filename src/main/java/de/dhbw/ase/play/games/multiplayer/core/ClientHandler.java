package de.dhbw.ase.play.games.multiplayer.core;

import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {

    private final Socket socket;
    private final MultiplayerServer server;
    private final String gameMode;
    private String username;
    private PrintWriter out;

    public ClientHandler(Socket socket, MultiplayerServer server, String gameMode) {
        this.socket = socket;
        this.server = server;
        this.gameMode = gameMode;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            boolean gameModChecked = false;
            do {
                line = in.readLine();
                if (line == null) {
                    // client has closed connection
                    socket.close();
                    return;
                }
                if (CommunicationPrefixes.USERNAME.checkPrefix(line)) {
                    username = line.substring(CommunicationPrefixes.USERNAME.getLength());

                } else if (CommunicationPrefixes.GAMEMODE.checkPrefix(line)) {
                    String gamemode = line.substring(CommunicationPrefixes.GAMEMODE.getLength());
                    if (!gamemode.equals(gameMode)) {
                        out.println(CommunicationPrefixes.WRONG_GAMEMODE);
                        return;
                    }
                    gameModChecked = true;
                }
            } while (username == null && gameModChecked);

            boolean successfulAdded = server.addClient(this, username);
            if (!successfulAdded) {
                out.println(CommunicationPrefixes.DUPLIKATE_USERNAME);
                socket.close();
                return;
            }
            out.println(CommunicationPrefixes.SUCCESSFULLY_JOINED);
            System.out.println(username + " ist beigetreten");

            do {
                line = in.readLine();
                if (line == null) {
                    break;
                }

                server.processClientMessage(getUsername(), line);
            } while (true);
            socket.close();
            server.removeClient(username);
        } catch (IOException e) {
            server.removeClient(username);
        }
        System.out.println(username + " wurde entfernt.");
    }

    void sendMessage(String msg) {
        out.println(msg);
    }

    void closeConnection() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) {
            return false;
        }

        ClientHandler o = (ClientHandler) obj;
        return o.getUsername().equals(getUsername());
    }
}
