package de.dhbw.ase.play.games.multiplayer.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {

    private final Socket socket;
    private final MultiplayerServer server;
    private String username;
    private PrintWriter out;

    public ClientHandler(Socket socket, MultiplayerServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            do {
                line = in.readLine();
                if (line == null) {
                    // client has closed connection
                    socket.close();
                    return;
                }
                if (line.startsWith("Username: ")) username = line.substring("Username: ".length()); // TODO String rausziehen
            } while (username == null);

            boolean successfulAdded = server.addClient(this, username);
            if (!successfulAdded) {
                out.println("duplicate Username"); // TODO String rausziehen
                socket.close();
                return;
            }
            out.println("successful joined"); // TODO String rausziehen
            // TODO Host benachrichtigen, dass ein Client erfolgreich connected hat

            do {
                line = in.readLine();
                server.processClientMessage(getUsername(), line);
            } while (line != null);
            socket.close();
            server.removeClient(username);
        } catch (IOException e) {
            server.removeClient(username);
            // TODO Host benachrichtigen das der Client removed wurde
        }
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
        if (obj.getClass() != getClass()) return false;

        ClientHandler o = (ClientHandler) obj;
        return o.getUsername().equals(getUsername());
    }
}
