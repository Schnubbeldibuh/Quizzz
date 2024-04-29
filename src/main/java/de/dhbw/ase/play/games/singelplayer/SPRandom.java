package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.user.in.UserIn;

import java.util.Collections;
import java.util.List;

public class SPRandom extends SingleplayerGame {

    private final List<SPQuiz> spGamesList;
    private SPQuiz randomGame;

    public SPRandom(UserIn sc,
                    List<SPQuiz> spGamesList) {
        super(sc);
        this.spGamesList = spGamesList;
    }

    @Override
    protected void startGame() throws ExitException, CouldNotAccessFileException {
        Collections.shuffle(spGamesList);
        randomGame = spGamesList.get(0);
        randomGame.startGame();
    }

    @Override
    protected void writeStats() throws CouldNotAccessFileException {
        randomGame.writeStats();
    }
}
