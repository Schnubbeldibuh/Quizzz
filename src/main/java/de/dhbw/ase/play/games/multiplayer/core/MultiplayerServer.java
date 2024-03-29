package de.dhbw.ase.play.games.multiplayer.core;

import de.dhbw.ase.play.games.reader.MReader;
import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.play.games.reader.Reader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class MultiplayerServer {

    private final int port;
    private final Map<String, ClientHandler> clients = new HashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    protected Set<String > userList;
    protected List<Question> questionList;
    protected int questionIndex;
    protected List<Question.Answer> currentAnswerList;
    private GameState gameState = GameState.CREATED;
    private Future<Void> joiningFuture;
    private ServerSocket serverSocket;


    protected MultiplayerServer(int port) {
        this.port = port;
    }

    protected abstract void processClientMessage(String username, String msg);

    protected void startPlaying() {
        Reader mReader = new MReader();
        questionList = mReader.getQuestionList();
        questionIndex = -1;
        sendMessageToAllClients("start game");
        Collections.shuffle(questionList);
        playQuestion();
    }

    protected void sendMessageToAllClients(String msg) {
        clients.values()
                .forEach(c -> c.sendMessage(msg));
    }

    protected void sendMessageToClient(String msg, String username) {
        clients.get(username).sendMessage(msg);
    }

    void startJoiningPhase() {
        checkIfShutDown();
        joiningFuture = executor.submit(this::joiningPhase);
    }

    void advanceGamestate() {
        checkIfShutDown();
        switch (gameState) {
            case CREATED, FINISHED -> {
                gameState = GameState.JOIN;
                startJoiningPhase();
            }
            case JOIN -> {
                gameState = GameState.PLAY;
                try {
                    serverSocket.close();
                } catch (IOException ignored) {
                }
                startPlaying();
            }
            case PLAY ->
                // TODO save statistics
                    gameState = GameState.FINISHED;
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

    protected void removeClient(String username) {
        checkIfShutDown();
        if (username == null)
            return;

        synchronized (clients) {
            clients.remove(username);
        }
        checkIfRoundClosed(username);
    }

    void shutdown() {
        if (gameState == GameState.SHUTDOWN)
            return;
        gameState = GameState.SHUTDOWN;
        executor.shutdownNow();
        clients.values()
                .forEach(ClientHandler::closeConnection);
    }

    protected Set<String> getUsernames() {
        return new HashSet<>(clients.keySet());
    }

    private Void joiningPhase() {
        serverSocket = null;
        try (ServerSocket ss = new ServerSocket()) {
            serverSocket = ss;
            ss.setReuseAddress(true);
            ss.bind(new InetSocketAddress(port));
            while (gameState == GameState.JOIN) {
                Socket socket = serverSocket.accept();
                executor.submit(new ClientHandler(socket, this));
            }
        } catch (SocketException e) {
            if (serverSocket != null && serverSocket.isClosed())
                return null;
            e.printStackTrace();
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

    protected void checkIfRoundClosed(String username) {
        synchronized(userList) {
            userList.remove(username);
            if (userList.isEmpty()) {
                playQuestion();
            }
        }
    }

    protected void playQuestion() {
        questionIndex++;
        if(questionList.size() == questionIndex) {
            sendMessageToAllClients("Round is finished!");
            //TODO Punktestand an alle Clients schicken
            return;
        }
        Question question = questionList.get(questionIndex);
        currentAnswerList = question.getAnswerList();
        Collections.shuffle(currentAnswerList);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Next question:");
        stringBuilder.append(question.getQuestion());
        currentAnswerList.forEach(a -> {
                    stringBuilder.append(";");
                    stringBuilder.append(a.answer());
        });
        stringBuilder.append(";");
        stringBuilder.append(questionIndex);
        sendMessageToAllClients(stringBuilder.toString());
        userList = getUsernames();
    }
}
