package de.dhbw.ase.play.games.multiplayer.core;

import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.play.games.repository.CouldNotAccessFileException;
import de.dhbw.ase.play.games.repository.Question;
import de.dhbw.ase.play.games.repository.QuestionRepository;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class MultiplayerServer {

    private final int port;
    private final Map<String, ClientHandler> clients = new HashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    protected final Set<String> userList = new HashSet<>();
    private final QuestionRepository questionRepository;
    private final String gameMode;
    protected List<Question> questionList;
    protected int questionIndex;
    protected List<Question.Answer> currentAnswerList;
    private GameState gameState = GameState.CREATED;
    private ServerSocket serverSocket;


    protected MultiplayerServer(int port, QuestionRepository questionRepository, String gameMode) {
        this.port = port;
        this.questionRepository = questionRepository;
        this.gameMode = gameMode;
    }

    protected abstract void initializeRound();

    protected abstract void processClientMessage(String username, String msg);
    protected abstract void sendStatsToAllClients();

    protected void startPlaying() {
        initializeRound();
        try {
            questionList = questionRepository.getQuestionList(8);
        } catch (CouldNotAccessFileException e) {
            System.out.println("Das Spiel konnte nicht gestartet werden.");
            System.out.println("Möglicherweise sind die Gamedaten kompromittiert");
            shutdown();
            return;
            //TODO prüfen ob das so funktionert
        }
        questionIndex = -1;
        sendMessageToAllClients(CommunicationPrefixes.START_GAME.toString());
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
        executor.submit(this::joiningPhase);
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
        removeUser(username);
        if (gameState != GameState.PLAY) {
            return;
        }

        if (checkIfQuestionFinished()) {
            playQuestion();
        }
    }

    void shutdown() {
        if (gameState == GameState.SHUTDOWN)
            return;
        gameState = GameState.SHUTDOWN;
        try {
            serverSocket.close();
        } catch (IOException ignored) {
        }
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
                executor.submit(new ClientHandler(socket, this, gameMode));
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
        if (isShutdown()) {
            throw new IllegalStateException("Server is already shut down");
        }
    }

    public boolean isShutdown() {
        return gameState == GameState.SHUTDOWN;
    }

    protected void removeUser(String username) {
        synchronized(userList) {
            userList.remove(username);
        }
    }

    protected Boolean checkIfQuestionFinished() {
        synchronized (userList) {
            return userList.isEmpty();
        }
    }

    protected void playQuestion() {
        questionIndex++;
        if(questionList.size() == questionIndex) {
            sendStatsToAllClients();
            sendMessageToAllClients(CommunicationPrefixes.ROUND_FINISHED.toString());
            return;
        }

        Question question = questionList.get(questionIndex);
        currentAnswerList = question.getAnswerList();

        Collections.shuffle(currentAnswerList);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CommunicationPrefixes.NEXT_QUESTION);
        stringBuilder.append(question.getQuestion());
        currentAnswerList.forEach(a -> {
                    stringBuilder.append(";");
                    stringBuilder.append(a.answer());
        });
        stringBuilder.append(";");
        stringBuilder.append(questionIndex);
        
        sendMessageToAllClients(stringBuilder.toString());

        userList.addAll(getUsernames());
    }
}
