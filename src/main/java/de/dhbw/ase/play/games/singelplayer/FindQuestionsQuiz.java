package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.reader.FQReader;
import de.dhbw.ase.play.games.reader.Question;

import java.util.List;
import java.util.Scanner;

public class FindQuestionsQuiz extends SingeplayerGame {

    public FindQuestionsQuiz(Scanner sc) {
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

            Question.Answer rightAnswer = questionList.get(i).getAnswerList()
                    .stream()
                    .filter(Question.Answer::isRight)
                    .findFirst()
                    .get();
            System.out.println("Die richtige Frage wäre \"" + rightAnswer + "\" gewesen."
            );
        }
        System.out.println("Du hattest ingesamt " + rightAnswerCount + " richtige Antworten!");
    }

    @Override
    protected String getStatsFilesPath() {
        return null;
    }

    @Override
    protected void writeStats() {

    }
}
