package de.dhbw.ase.play.games;

import de.dhbw.ase.play.games.reader.FQReader;
import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.play.games.singelplayer.SingeplayerGame;

import java.util.List;
import java.util.Scanner;

public class FindQuestions extends SingeplayerGame {

    public FindQuestions(Scanner sc) {
        super(sc);
    }

    @Override
    protected void startGame() {
        FQReader fqReader = new FQReader();
        List<Question> questionList = fqReader.getQuestionList();
        int rightAnswerCount = 0;

        System.out.println();
        System.out.println("------------- Neue Runde WWM -------------");

        for (int i = 0; i < questionList.size(); i++) {
            boolean answerEvaluation = playQuestion(questionList.get(i));
            if (answerEvaluation) {
                System.out.println("Richtige Antwort!");
                rightAnswerCount++;
                continue;
            }
            System.out.println("Diese Antwort war leider Falsch.");
            // bei falscher Antwort noch die Richtige anzeigen
        }
        System.out.println("Du hattest ingesamt " + rightAnswerCount + " richtige Antworten!");
    }
}
