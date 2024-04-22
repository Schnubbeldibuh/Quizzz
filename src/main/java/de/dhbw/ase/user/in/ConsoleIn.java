package de.dhbw.ase.user.in;

import de.dhbw.ase.user.out.UserOut;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class ConsoleIn implements UserIn {

    private final Thread thread;
    private final ConsoleScanner consoleScanner;
    private Object output = this;
    private final Queue<String> lastLines = new LinkedList<>();


    public ConsoleIn(InputStream in) {
        consoleScanner = new ConsoleScanner(this, in);
        thread = new Thread(consoleScanner);
        thread.start();
    }

    @Override
    public synchronized void setOutput(Object o) {
        notify();
        output = o;
    }


    @Override
    public synchronized String getLastLine(Object o) {
        if (o != output) {
            throw new OutputChangedException();
        }
        return lastLines.poll();
    }

    @Override
    public synchronized String waitForNextLine(Object o) {
        setOutput(o);
        if (!lastLines.isEmpty()) {
            return lastLines.poll();
        }
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getLastLine(o);
    }

    @Override
    public void stop(UserOut userOut) {
        consoleScanner.ending();
        userOut.printLn("Drücke Enter um das Spiel zu beenden");
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized void lineReed(String input) {
        lastLines.offer(input);
        notify();
    }
}
