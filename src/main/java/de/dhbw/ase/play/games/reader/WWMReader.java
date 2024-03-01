package de.dhbw.ase.play.games.reader;

import de.dhbw.ase.Quizzz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WWMReader {

    private final Random random;

    public WWMReader() {
        random = new Random();
    }

    public List<WWMQuestion> getQuestionList() {
        List<WWMQuestion> questionsForOneRound = new ArrayList<>();
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_EASY), 5));
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_MEDIUM), 4));
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_HARD), 3));
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_VERY_HARD), 2));
        questionsForOneRound.addAll(readFile(new File(Quizzz.FILE_WWM_EXPERT), 1));
        return questionsForOneRound;
    }

    private List<WWMQuestion> readFile(File file, int amount) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            bufferedReader.mark(1000);
            String l1 = bufferedReader.readLine();
            int lines = Integer.parseInt(l1.substring(0, l1.indexOf(';')));
            bufferedReader.reset();

            Set<Integer> linenumbers = getRandomLinenumbers(amount, lines);
            return bufferedReader.lines()
                    .filter(l -> checkLinePrefix(linenumbers, l))
                    .map(this::mapLineToWWMQuestion)
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
        while (randomNumberList.size() < amount) {
            randomNumberList.add(random.nextInt(lines+1));
        }
        return randomNumberList;
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
