package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.play.games.reader.Question;
import de.dhbw.ase.play.games.reader.Reader;
import de.dhbw.ase.stats.persistance.PlayerStatsSPObject;
import de.dhbw.ase.user.in.UserIn;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SPQuiz extends SingleplayerGame {

    private final String filePath;
    private final String statsFilePath;
    private final Categories category;
    private PlayerStatsSPObject playerStatsSPObject;

    public SPQuiz(UserIn sc, String filePath, Categories category, String statsFilePath) {
        super(sc);
        this.filePath = filePath;
        this.category = category;
        this.statsFilePath = statsFilePath;
    }

    @Override
    protected void startGame() {
        Reader spReader = new Reader(filePath, 8);
        List<Question> questionList = spReader.getQuestionList();
        int rightAnswerCount = 0;
        int wrongAnswerCount = 0;
        long averageAnswerTime;
        long totalAnswerTime = 0;

        System.out.println();
        System.out.println("------------- Neue Runde WWM -------------");

        for (int i = 0; i < questionList.size(); i++) {
            long beforeQuestion = System.nanoTime();
            boolean answerEvaluation = playQuestion(questionList.get(i));
            long afterQuestion = System.nanoTime();

            if (answerEvaluation) {
                System.out.println("Richtige Antwort!");
                rightAnswerCount++;
                continue;
            }
            System.out.println("Diese Antwort war leider Falsch.");
            totalAnswerTime += Math.abs(afterQuestion - beforeQuestion);

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
        averageAnswerTime = TimeUnit.NANOSECONDS.toMillis(totalAnswerTime / (questionList.size()));
        System.out.println("Du hast eine durchschnittliche Antwortzeit von " + averageAnswerTime + " Millisekunden.");

        playerStatsSPObject = new PlayerStatsSPObject(getUsername(), rightAnswerCount, wrongAnswerCount, category.getString());
    }

    @Override
    protected String getStatsFilesPath() {
        return filePath;
    }

    @Override
    protected void writeStats() {
        List<PlayerStatsSPObject> file = new ArrayList<>(readFile());

        int index = file.indexOf(playerStatsSPObject);
        if (index == -1) {
            file.add(playerStatsSPObject);
        } else {
            PlayerStatsSPObject oldStats = file.get(index);
            oldStats.add(playerStatsSPObject);
        }

        Collections.sort(file);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(statsFilePath))) {
            for (PlayerStatsSPObject playerStatsSPObject1 : file) {
                bufferedWriter.write(playerStatsSPObject1.getCompleteLine());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO
        }
    }

    private List<PlayerStatsSPObject> readFile() {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(statsFilePath))) {
            return bufferedReader.lines()
                    .map(PlayerStatsSPObject::new)
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
