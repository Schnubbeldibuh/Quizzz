package de.dhbw.ase.play.games.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Reader {
    private final Random random;
    private String filePath;
    private int amount;

    public Reader(String filePath, int amount) {
        random = new Random();
        this.filePath = filePath;
        this.amount = amount;
    }

    public Reader() {
        random  = new Random();
    }

    public List<Question> getQuestionList() {
        List<Question> questionsForOneRound = new ArrayList<>(readFile(filePath, amount));
        return questionsForOneRound;
    }

    protected List<Question> readFile(String file, int amount) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            bufferedReader.mark(1000);
            String l1 = bufferedReader.readLine();
            int lines = Integer.parseInt(l1.substring(0, l1.indexOf(';')));
            bufferedReader.reset();

            Set<Integer> linenumbers = getRandomLinenumbers(amount, lines);
            return bufferedReader.lines()
                    .filter(l -> checkLinePrefix(linenumbers, l))
                    .map(this::mapLineToQuestion)
                    .toList();
        } catch (IOException e) {
            // TODO
            throw new RuntimeException();
        }
    }

    private boolean checkLinePrefix(Set<Integer> searchedNumbers, String line) {
        for (Integer n : searchedNumbers)
            if (line.startsWith(n.toString())) {
                searchedNumbers.remove(n);
                return true;
            }
        return false;
    }

    private Set<Integer> getRandomLinenumbers(int amount, int lines) {
        Set<Integer> randomNumberList = new HashSet<>();
        while (randomNumberList.size() <= amount) {
            randomNumberList.add(random.nextInt(lines+1));
        }
        return randomNumberList;
    }

    protected Question mapLineToQuestion(String line) {
        String[] splittedLine = line.split(";");
        List<Question.Answer> answerList = new ArrayList<>();
        answerList.add(new Question.Answer(splittedLine[2], true));
        answerList.add(new Question.Answer(splittedLine[3], false));
        answerList.add(new Question.Answer(splittedLine[4], false));
        answerList.add(new Question.Answer(splittedLine[5], false));
        return new Question(answerList, splittedLine[1]);
    }
}
