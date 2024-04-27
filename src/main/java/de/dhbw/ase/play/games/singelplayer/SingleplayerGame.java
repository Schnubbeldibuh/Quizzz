package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.ExitException;
import de.dhbw.ase.play.games.Game;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SingleplayerGame extends Game {

    private final UserIn sc;

    public SingleplayerGame(UserIn sc) {
        super(sc);
        this.sc = sc;
    }

    protected abstract void startGame() throws ExitException, CouldNotAccessFileException;
    protected abstract void writeStats() throws CouldNotAccessFileException;

    @Override
    public SelectedMenu.MenuSelection start() {
        try {
            indicateUser();
        } catch (ExitException e) {
            return SelectedMenu.MenuSelection.EXIT;
        }

        do {
            try {
                startGame();
            } catch (ExitException e) {
                return SelectedMenu.MenuSelection.EXIT;
            } catch (CouldNotAccessFileException e) {
                System.out.println("Das Spiel konnte nicht gestartet werden.");
                System.out.println("Möglicherweise sind die Gamedaten kompromittiert");
                return SelectedMenu.MenuSelection.BACK;
            }
            try {
                writeStats();
            } catch (CouldNotAccessFileException e) {
                System.out.println("Beim speichern der Stats ist ein Fehler aufgetreten.");
                System.out.println("Die Stats wurden nicht gespeichert.");
            }
        } while (askUserForRetry());
        return SelectedMenu.MenuSelection.BACK;
    }

    protected boolean playQuestion(Question question) throws ExitException {
        List<Question.Answer> answerList = showQuestion(question);
        int answer = scanUntilValidInput();
        return answerList.get(answer).isRight();
    }

    protected List<Question.Answer> showQuestion(Question question) {
        List<Question.Answer> answerList = new ArrayList<>(question.getAnswerList());
        Collections.shuffle(answerList);
        System.out.println();
        System.out.println(question.getQuestion());
        System.out.println("A: " + answerList.get(0).answer());
        System.out.println("B: " + answerList.get(1).answer());
        System.out.println("C: " + answerList.get(2).answer());
        System.out.println("D: " + answerList.get(3).answer());
        return answerList;
    }

    protected int scanUntilValidInput() throws ExitException {
        int selection;
        do {
            String input = sc.waitForNextLine(this).toLowerCase();
            if (input.equalsIgnoreCase("exit")) {
                throw new ExitException();
            }
            if (input.length() == 1) {
                selection = switch (input.charAt(0)) {
                    case 'a' -> 0;
                    case 'b' -> 1;
                    case 'c' -> 2;
                    case 'd' -> 3;
                    default -> -1;
                };
                if (selection != -1)
                    return selection;
            }
            System.out.println("Invalide Eingabe. Bitte erneut antworten.");
        } while (true);
    }


}
