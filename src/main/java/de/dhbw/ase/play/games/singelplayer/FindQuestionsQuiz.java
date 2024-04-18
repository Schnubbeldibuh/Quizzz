package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.reader.FQReader;
import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.stats.persistance.PlayerStatsFQObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FindQuestionsQuiz extends SingeplayerGame {

    private final String filePath;

    private PlayerStatsFQObject playerStatsFQObject;

    public FindQuestionsQuiz(Scanner sc, String filePath) {
        super(sc);
        this.filePath = filePath;
    }

    @Override
    protected void startGame() {
        FQReader fqReader = new FQReader();
        List<Question> questionList = fqReader.getQuestionList();
        int rightAnswerCount = 0;
        int wrongAnswerCount = 0;

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
            System.out.println("Die richtige Frage wäre \"" + rightAnswer.answer() + "\" gewesen."
            );

            wrongAnswerCount++;
        }
        System.out.println("Du hattest ingesamt " + rightAnswerCount + " richtige Antworten!");

        playerStatsFQObject = new PlayerStatsFQObject(getUsername(), rightAnswerCount, wrongAnswerCount);
    }

    @Override
    protected String getStatsFilesPath() {
        return filePath;
    }

    @Override
    protected void writeStats() {

        List<PlayerStatsFQObject> file = new ArrayList<>(readFile());

        int index = file.indexOf(playerStatsFQObject);
        if (index == -1) {
            file.add(playerStatsFQObject);
        } else {
            PlayerStatsFQObject oldStats = file.get(index);
            oldStats.add(playerStatsFQObject);
        }

        Collections.sort(file);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getStatsFilesPath()))) {
            for (PlayerStatsFQObject playerStatsWWMObject1 : file) {
                bufferedWriter.write(playerStatsWWMObject1.getCompleteLine());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    private List<PlayerStatsFQObject> readFile() {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(getStatsFilesPath()))) {
            return bufferedReader.lines()
                    .map(PlayerStatsFQObject::new)
                    .toList();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Ein unerwarteter Fehler ist aufgetreten.");
            //TODO zurückspringen mit separater Exception
        }
        return new ArrayList<>();
    }
}
