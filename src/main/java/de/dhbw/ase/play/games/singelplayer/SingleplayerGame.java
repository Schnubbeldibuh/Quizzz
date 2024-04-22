package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.Game;
import de.dhbw.ase.play.games.reader.Question;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class SingleplayerGame extends Game {

    private final Scanner sc;

    public SingleplayerGame(Scanner sc) {
        super(sc);
        this.sc = sc;
    }

    protected abstract void startGame();
    protected  abstract String getStatsFilesPath();
    protected abstract void writeStats();

    @Override
    public SelectedMenu.MenuSelection start() {
        indicateUser();
        do {
            startGame();
            writeStats();
        } while (askUserForRetry());
        return SelectedMenu.MenuSelection.BACK;
    }

    protected boolean playQuestion(Question question) {
        List<Question.Answer> answerList = question.getAnswerList();
        Collections.shuffle(answerList);
        System.out.println(question.getQuestion());
        System.out.println("A: " + answerList.get(0).answer());
        System.out.println("B: " + answerList.get(1).answer());
        System.out.println("C: " + answerList.get(2).answer());
        System.out.println("D: " + answerList.get(3).answer());
        int answer = scanUntilValidInput();
        return answerList.get(answer).isRight();
    }

    protected int scanUntilValidInput() {
        int selection;
        do {
            String input = sc.next().toLowerCase();
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
