package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.Quizzz;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class MultiplayerServer implements Runnable {


    @Override
    public void run() {
        try (ServerSocket socket = new ServerSocket(Quizzz.SERVER_PORT);) {

            //TODO
        } catch (IOException ignored) {
        }
    }
}
