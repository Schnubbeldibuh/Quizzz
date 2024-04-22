package de.dhbw.ase.user.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class ConsoleScanner implements Runnable {

    private final ConsoleIn consoleIn;
    private final InputStream in;
    private boolean ending = false;

    public ConsoleScanner(ConsoleIn consoleIn, InputStream in) {
        this.consoleIn = consoleIn;
        this.in = in;
    }

    @Override
    public void run() {
        while (!ending) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            try {
                consoleIn.lineReed(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void ending() {
        this.ending = true;
    }
}
