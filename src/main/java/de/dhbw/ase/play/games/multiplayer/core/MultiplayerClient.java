package de.dhbw.ase.play.games.multiplayer.core;

import de.dhbw.ase.play.games.ExitException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public abstract class MultiplayerClient {
    private final Scanner sc;
    private final String username;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private String questionIndex;

    private record ListeningResult(String input, Source source) {}

    public MultiplayerClient(Scanner sc, String username) {
        this.sc = sc;
        this.username = username;
    }

    protected abstract boolean checkUserInput(String input) throws ExitException;
    protected abstract boolean checkServerInput(String input) throws ExitException;
    protected abstract List<Source> processServerInput(String input);

    protected void start() throws ExitException {
        List<Source> listeningTo = new ArrayList<>();
        listeningTo.add(Source.SERVER);

        do {
            CompletionService<ListeningResult> threadPool = new ExecutorCompletionService<>(executor);
            Future<ListeningResult> clientThread = null, serverThread = null;
            if (listeningTo.contains(Source.USER)) {
                clientThread = threadPool.submit(() -> {
                    String input;
                    do {
                        input = sc.next();
                        if (input.equals("exit"))
                            throw new ExitException();
                    } while (!checkUserInput(input));
                    return new ListeningResult(input, Source.USER);
                });
            }
            if (listeningTo.contains(Source.SERVER)) {
                serverThread = threadPool.submit(() -> {
                    String input;
                    do {
                        input = in.readLine();
                    } while (input != null && !checkServerInput(input));
                    return new ListeningResult(input, Source.SERVER);
                });
            }

            ListeningResult res;
            try {
                Future<ListeningResult> take = threadPool.take();
                res = take.get();
            } catch (InterruptedException e) {
                // TODO
                executor.shutdownNow();
                return;
            } catch (ExecutionException e) {
                if (e.getCause().getClass() == ExitException.class) {
                    executor.shutdownNow();
                    throw new ExitException();
                }
                if (e.getCause().getClass() == SocketException.class) {
                    executor.shutdownNow();
                    System.out.println("Host hat die Verbindung getrennt");
                    throw new ExitException();
                }
                // TODO
                return;
            }

            if (res.source() == Source.USER) {
                if (serverThread != null)
                    serverThread.cancel(true);
                listeningTo = processUserInput(res.input());
            } else {
                if (clientThread != null)
                    clientThread.cancel(true);
                if (res.input == null) {
                    System.out.println("Host hat die Verbindung getrennt.");
                    throw new ExitException();
                }
                listeningTo = processServerInput(res.input());
            }

        } while (!listeningTo.isEmpty());
    }

    boolean registerClient(String host, int port) throws UsernameAlreadyExistsException, UnknownHostException {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            sendMessageToServer("Username: " + username); // TODO String rausziehen

            String line;
            do {
                line = in.readLine();
                if (line == null) {
                    // TODO Server hat unerwartet die verbindung geschlossen
                    return false;
                }

                if (line.equals("duplicate Username")) { // TODO String rausziehen
                    socket.close();
                    throw new UsernameAlreadyExistsException();
                }
                if (line.equals("successful joined")) { // TODO String rausziehen
                    return true;
                }
            } while (true);

        } catch (UnknownHostException e) {
            throw e;
        } catch (IOException e) {
            return false;
        }
    }

    protected void sendMessageToServer(String msg) {
        out.println(msg);
    }

    protected enum Source {
        SERVER, USER
    }

    protected void disconnectClient() {
        executor.shutdownNow();
        try {
            socket.close();
        } catch (IOException ignored) {

        }
    }

    protected void showQuestion(String input) {
        String question = input.substring("Next question:".length());
        String[] questionAnswersArray = question.split(";");
        System.out.println("Frage: " + questionAnswersArray[0]);
        System.out.println("A: " + questionAnswersArray[1]);
        System.out.println("B: " + questionAnswersArray[2]);
        System.out.println("C: " + questionAnswersArray[3]);
        System.out.println("D: " + questionAnswersArray[4]);
        questionIndex = questionAnswersArray[5];
    }

    protected List<MultiplayerClient.Source> processUserInput(String input) {
        Integer selection = switch (input) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + input);
        };
        sendMessageToServer("Answer:" + selection + ";" + questionIndex);
        List<MultiplayerClient.Source> sourceList = new ArrayList<>();
        sourceList.add(Source.SERVER);
        return sourceList;
    }
}
