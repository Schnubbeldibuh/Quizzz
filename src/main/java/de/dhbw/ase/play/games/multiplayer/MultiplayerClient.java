package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.play.games.ExitException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.*;

public abstract class MultiplayerClient {
    private final Scanner sc;
    private final String username;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final CompletionService<ListeningResult> threadPool =
            new ExecutorCompletionService<>(executor);
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    private record ListeningResult(String input, InputSource source) {}

    public MultiplayerClient(Scanner sc, String username) {
        this.sc = sc;
        this.username = username;
    }

    protected abstract String checkAndFormatUserInput(String input);
    protected abstract String processServerInput(String input);

    protected void start() throws ExitException {
        do {
            Future<ListeningResult> clientThread = threadPool.submit(() -> {
                String input;
                do {
                    input = checkAndFormatUserInput(sc.next());
                } while (input == null);
                return new ListeningResult(input, InputSource.USER);
            });
            Future<ListeningResult> serverThread = threadPool.submit(() -> {
                String s = in.readLine();
                if (s == null)
                    throw new ServerClosedConnectionException();
                return new ListeningResult(s, InputSource.SERVER);
            });

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
                if (e.getCause().getClass() == ServerClosedConnectionException.class) {
                    executor.shutdownNow();
                    //TODO
                }
                // TODO
                return;
            }

            if (res.source() == InputSource.USER) {
                serverThread.cancel(true);
                out.println(res.input());
            } else {
                clientThread.cancel(true);
                processServerInput(res.input()); // TODO der Methode ein rückgabewert geben um festzustellen ob auf den server oder auf den user oder beide gewartet werden soll
            }

        } while (true);
    }

    boolean registerClient(String host, int port) throws UsernameAlreadyExistsException, UnknownHostException {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Username: " + username); // TODO String rausziehen

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

    private enum InputSource {
        SERVER, USER
    }
}
