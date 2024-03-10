package de.dhbw.ase.play.games.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class MultiplayerServer {

    private final int port;
    private final Map<String, ClientHandler> clients = new HashMap<>();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private GameState gameState = GameState.CREATED;
    private Future<Void> joiningFuture;


    protected MultiplayerServer(int port) {
        this.port = port;
    }

    protected abstract void processClientMessage(String username, String msg);

    protected abstract void startPlaying();

    protected void sendMessageToAllClients(String msg) {
        clients.values()
                .forEach(c -> c.sendMessage(msg));
    }

    void startJoiningPhase() {
        checkIfShutDown();
        joiningFuture = threadPool.submit(this::joiningPhase);
    }

    void advanceGamestate() {
        checkIfShutDown();
        switch (gameState) {
            case CREATED -> {
                gameState = GameState.JOIN;
                joiningPhase();
            }
            case JOIN -> {
                gameState = GameState.PLAY;
                joiningFuture.cancel(true);
                startPlaying();
            }
            case PLAY ->
                // TODO save statistics
                    gameState = GameState.FINISHED;
            case FINISHED -> {
                gameState = GameState.PLAY;
                joiningPhase();
            }
        }
    }

    boolean addClient(ClientHandler client, String username) {
        checkIfShutDown();
        if (username == null)
            return false;

        synchronized (clients) {
            if (clients.containsKey(username))
                return false;
            clients.put(username, client);
        }
        return true;
    }

    void removeClient(String username) {
        checkIfShutDown();
        if (username == null)
            return;

        synchronized (clients) {
            clients.remove(username);
        }
    }

    void shutdown() {
        if (gameState == GameState.SHUTDOWN)
            return;
        gameState = GameState.SHUTDOWN;
        threadPool.shutdownNow();
        clients.values()
                .forEach(ClientHandler::closeConnection);
    }

    Set<String> getUsernames() {
        return clients.keySet();
    }

    private Void joiningPhase() {
        ServerSocket serverSocket = null;
        try (ServerSocket ss = new ServerSocket(port)) {
            serverSocket = ss;
            while (gameState == GameState.JOIN) {
                Socket socket = serverSocket.accept();
                threadPool.submit(new ClientHandler(socket, this));
            }
        } catch (SocketException e) {
            if (serverSocket != null && serverSocket.isClosed())
                return null;

            System.out.println("Der Server ist abgestürzt :(");
            System.out.println("Bitte erneut versuchen.");
            return null;

        } catch (IOException e) {
            System.out.println("Der Server ist abgestürzt :(");
            System.out.println("Bitte erneut versuchen.");
            return null;
        }
        return null;
    }

    private void checkIfShutDown() {
        if (gameState == GameState.SHUTDOWN)
            throw new IllegalStateException("Server is already shut down");
    }
}
