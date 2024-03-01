package de.dhbw.ase.play.games.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WWMReader {

    public List<WWMQuestion> getQuestionList() {
        List<WWMQuestion> questionsForOneRound = readFile(new File("src/main/resources/WWMleicht.csv"), 5);
        questionsForOneRound.addAll(readFile(new File("src/main/resources/WWMmittle.csv"), 4));
        questionsForOneRound.addAll(readFile(new File("src/main/resources/WWMschwer.csv"), 3));
        questionsForOneRound.addAll(readFile(new File("src/main/resources/WWMSehrSchwer.csv"), 2));
        questionsForOneRound.addAll(readFile(new File("src/main/resources/WWMexperte.csv"), 1));
        return questionsForOneRound;
    }

    private List<WWMQuestion> readFile(File file, int amount) {
        List<WWMQuestion> questionList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file))){
            lineNumberReader.skip(Long.MAX_VALUE);
            int numberOfLines = lineNumberReader.getLineNumber();
            ArrayList<Integer> randomNumberList = new ArrayList<>();
            Random random = new Random();
            while (randomNumberList.size() < amount) {
                int randomNumber = random.nextInt(numberOfLines);
                if (!randomNumberList.contains(randomNumber)) {
                    randomNumberList.add(randomNumber);
                }
            }
            Collections.sort(randomNumberList);
            for (int l: randomNumberList) {
                bufferedReader.skip(l-1);
                questionList.add(mapLineToWWMQuestion(bufferedReader.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    private WWMQuestion mapLineToWWMQuestion(String line) {
        String[] splittedLine = line.split(";");
        List<WWMQuestion.Answer> answerList = new ArrayList<>();
        answerList.add(new WWMQuestion.Answer(splittedLine[2], true));
        answerList.add(new WWMQuestion.Answer(splittedLine[3], false));
        answerList.add(new WWMQuestion.Answer(splittedLine[4], false));
        answerList.add(new WWMQuestion.Answer(splittedLine[5], false));
        return new WWMQuestion(answerList, splittedLine[1]);
    }
}
