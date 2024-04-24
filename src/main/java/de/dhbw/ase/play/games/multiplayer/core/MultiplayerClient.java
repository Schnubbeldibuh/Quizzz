package de.dhbw.ase.play.games.multiplayer.core;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.user.in.UserIn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class MultiplayerClient {
    private final String username;
    private final ExecutorService listeningExecutor = Executors.newFixedThreadPool(2);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    protected final List<CommunicationPrefixes> validServerMessages = new ArrayList<>();
    private final String gameMode;
    private final UserIn sc;
    protected boolean discardUserinput;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    protected String questionIndex;


    public MultiplayerClient(UserIn sc, String username, String gameMode) {
        this.username = username;
        this.sc = sc;
        this.gameMode = gameMode;
    }


    protected abstract boolean checkUserInput(String input) throws ExitException;

    protected abstract boolean processServerInput(String input);

    protected boolean processUserInput(String input) {
        Integer selection = switch (input) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            default -> {
                throw new IllegalStateException("Unexpected value: " + input);
            }
        };
        sendMessageToServer(CommunicationPrefixes.ANSWER.toString() + selection + ";" + questionIndex);
        return true;
    }


    protected boolean checkServerInput(String input) {
        CommunicationPrefixes communicationPrefixes;

        try {
            communicationPrefixes = CommunicationPrefixes.evaluateCase(input);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return validServerMessages.contains(communicationPrefixes);
    }

    protected boolean start() throws ExitException {
        CompletionService<Boolean> threadPool = new ExecutorCompletionService<>(listeningExecutor);
        threadPool.submit(this::litenToClient);

        threadPool.submit(this::listenToServer);

        try {
            threadPool.take().get();
        } catch (InterruptedException e) {
            // TODO
            disconnectClient();
            throw new ExitException();
        } catch (ExecutionException e) {
            if (e.getCause().getClass() == ExitException.class) {
                listeningExecutor.shutdownNow();
                throw new ExitException();
            }
            if (e.getCause().getClass() == SocketException.class
                    || e.getCause().getClass() == HostDisconnectedException.class) {
                listeningExecutor.shutdownNow();
                System.out.println("Host hat die Verbindung getrennt");
                return false;
            }
            // TODO
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean listenToServer()
            throws IOException, InterruptedException, ExecutionException, HostDisconnectedException {
        Future<Boolean> submit;
        do {
            String input;
            do {
                input = in.readLine();
            } while (input != null && !checkServerInput(input));

            if (input == null) {
                throw new HostDisconnectedException();
            }

            String finalInput = input;
            submit = executor.submit(() -> processServerInput(finalInput));
        } while (submit.get());
        return true;
    }

    private boolean litenToClient() throws ExitException, InterruptedException, ExecutionException {
        Future<Boolean> submit;
        do {
            String input = sc.waitForNextLine(this);

            if (input.equals("exit")) {
                throw new ExitException();
            }

            if (!checkUserInput(input)) {
                System.out.println("Ungültige Eingabe");
                continue;
            }

            submit = executor.submit(() -> processUserInput(input));
            submit.get();
        } while (true);
    }

    boolean registerClient(String host, int port)
            throws UsernameAlreadyExistsException, UnknownHostException, ExitException {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String line;

            sendMessageToServer(CommunicationPrefixes.GAMEMODE + gameMode);
            sendMessageToServer(CommunicationPrefixes.USERNAME + username);

            do {
                line = in.readLine();
                if (line == null) {
                    System.out.println("Der Server hat die Verbindung getrennt");
                    return false;
                }

                if (CommunicationPrefixes.DUPLIKATE_USERNAME.checkPrefix(line)) {
                    socket.close();
                    throw new UsernameAlreadyExistsException();

                } else if (CommunicationPrefixes.SUCCESSFULLY_JOINED.checkPrefix(line)) {
                    return true;

                } else if (CommunicationPrefixes.WRONG_GAMEMODE.checkPrefix(line)) {
                    socket.close();
                    System.out.println("Der Server hat einen anderen Spielmodus");
                    throw new ExitException();
                }
            } while (true);

        } catch (IOException e) {
            return false;
        }
    }

    protected void sendMessageToServer(String msg) {
        out.println(msg);
    }

    protected void disconnectClient() {
        listeningExecutor.shutdownNow();
        executor.shutdownNow();
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    protected void showQuestion(String input) {
        String question = input.substring(
                CommunicationPrefixes.NEXT_QUESTION.getLength());

        String[] questionAnswersArray = question.split(";");
        System.out.println();
        System.out.println("Frage: " + questionAnswersArray[0]);
        System.out.println("A: " + questionAnswersArray[1]);
        System.out.println("B: " + questionAnswersArray[2]);
        System.out.println("C: " + questionAnswersArray[3]);
        System.out.println("D: " + questionAnswersArray[4]);

        questionIndex = questionAnswersArray[5];
    }
}
