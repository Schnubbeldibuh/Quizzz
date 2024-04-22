package de.dhbw.ase.play.games.multiplayer.core;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.multiplayer.CommunicationPrefixes;
import de.dhbw.ase.stats.persistance.PlayerStatsMPObject;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public abstract class MultiplayerClient {
    protected final String filepath;
    private final String username;
    private final ExecutorService listeningExecutor = Executors.newFixedThreadPool(2);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    protected final List<CommunicationPrefixes> validServerMessages = new ArrayList<>();
    protected List<PlayerStatsMPObject> stats = new ArrayList<>();
    protected boolean discardUserinput;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    protected String questionIndex;


    public MultiplayerClient(String username, String filepath) {
        this.username = username;
        this.filepath = filepath;
    }


    protected abstract boolean checkUserInput(String input) throws ExitException;

    protected abstract boolean processServerInput(String input);

    protected abstract boolean processUserInput(String input);


    protected void writeStats() {
        List<PlayerStatsMPObject> file;
        try {
            file = new ArrayList<>(readFile());
        } catch (IOException e) {
            System.out.println("Beim speichern der Stats ist ein Fehler aufgetreten.");
            System.out.println("Die Stats wurden nicht gespeichert.");
            return;
        }

        for (PlayerStatsMPObject p : stats) {

            int index = file.indexOf(p);
            if (index == -1) {
                file.add(p);
            } else {
                PlayerStatsMPObject oldStats = file.get(index);
                oldStats.add(p);
            }
        }

        Collections.sort(file);

        File f = new File(filepath);
        if (!f.exists()) {
            f.mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Beim speichern der Stats ist ein Fehler aufgetreten.");
                System.out.println("Die Stats wurden nicht gespeichert.");
                return;
            }
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f))) {
            for (PlayerStatsMPObject playerStatsMPObject : file) {
                bufferedWriter.write(playerStatsMPObject.getCompleteLine());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Beim speichern der Stats ist ein Fehler aufgetreten.");
            System.out.println("Die Stats wurden möglicherweise nicht gespeichert.");
            return;
        }
    }

    private List<PlayerStatsMPObject> readFile() throws IOException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(filepath))) {
            return bufferedReader.lines()
                    .map(PlayerStatsMPObject::new)
                    .toList();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
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

    protected void start() throws ExitException {
        CompletionService<Boolean> threadPool = new ExecutorCompletionService<>(listeningExecutor);
        threadPool.submit(() -> {
            Future<Boolean> submit;
            do {
                StringBuilder builder = new StringBuilder();
                do {
                    int inputInt = System.in.read();
                    if (inputInt == 10) {
                        break;
                    }
                    builder.append((char) inputInt);
                } while (true);

                String input = builder.toString();
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
        });

        threadPool.submit(() -> {
            Future<Boolean> submit;
            do {
                String input;
                do {
                    input = in.readLine();
                } while (input != null && !checkServerInput(input));
                String finalInput = input;
                //System.out.println(input);
                submit = executor.submit(() -> processServerInput(finalInput));
            } while (submit.get());
            return true;
        });

        try {
            threadPool.take().get();
        } catch (InterruptedException e) {
            // TODO
            listeningExecutor.shutdownNow();
            return;
        } catch (ExecutionException e) {
            if (e.getCause().getClass() == ExitException.class) {
                listeningExecutor.shutdownNow();
                throw new ExitException();
            }
            if (e.getCause().getClass() == SocketException.class) {
                listeningExecutor.shutdownNow();
                System.out.println("Host hat die Verbindung getrennt");
                throw new ExitException();
            }
            // TODO
            e.printStackTrace();
            return;
        }

        listeningExecutor.shutdownNow();
    }

    boolean registerClient(String host, int port) throws UsernameAlreadyExistsException, UnknownHostException {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            sendMessageToServer(CommunicationPrefixes.USERNAME + username);

            String line;
            do {
                line = in.readLine();
                if (line == null) {
                    // TODO Server hat unerwartet die verbindung geschlossen
                    return false;
                }

                if (CommunicationPrefixes.DUPLIKATE_USERNAME.checkPrefix(line)) {
                    socket.close();
                    throw new UsernameAlreadyExistsException();
                }
                if (CommunicationPrefixes.SUCCESSFULLY_JOINED.checkPrefix(line)) {
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
        SERVER,
        USER
    }

    protected void disconnectClient() {
        listeningExecutor.shutdownNow();
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
