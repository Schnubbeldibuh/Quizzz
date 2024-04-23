package de.dhbw.ase.play.games.multiplayer.core;

public interface ServerClientFactory {

    MultiplayerServer createServer();

    MultiplayerClient createClient(String username);
}
