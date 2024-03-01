package de.dhbw.ase.play.games;

import de.dhbw.ase.play.games.reader.WWMQuestion;
import de.dhbw.ase.play.games.reader.WWMReader;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WerWirdMillionaer extends Game {

    private final Scanner sc;
    public WerWirdMillionaer(Scanner sc) {
        super(sc);
        this.sc = sc;
    }

    @Override
    protected void startGame() {
        WWMReader wwmReader = new WWMReader();
        List<WWMQuestion> questionList = wwmReader.getQuestionList();
        for (int i = 0; i < questionList.size(); i++) {
            playQuestion(questionList.get(i));
        //TODO
        }
    }

    private boolean playQuestion(WWMQuestion question) {
        List<WWMQuestion.Answer> answerList = question.getAnswerList();
        Collections.shuffle(answerList);
        System.out.println(question.getQuestion());
        System.out.println("A: " + answerList.get(0).answer());
        System.out.println("B: " + answerList.get(1).answer());
        System.out.println("C: " + answerList.get(2).answer());
        System.out.println("D: " + answerList.get(3).answer());
        int answer = scanUntilValidInput();
        return answerList.get(answer).isRight();
    }

    private int scanUntilValidInput() {
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
