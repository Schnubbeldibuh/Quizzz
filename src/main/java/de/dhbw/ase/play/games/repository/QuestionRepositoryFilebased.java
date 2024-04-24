package de.dhbw.ase.play.games.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class QuestionRepositoryFilebased implements QuestionRepository {

    private static final Map<String, QuestionRepository> INSTANCES = new HashMap<>();

    public static QuestionRepository getInstance(String filePath) {
        if (INSTANCES.containsKey(filePath)) {
            return INSTANCES.get(filePath);
        }
        QuestionRepositoryFilebased repositoryFilebased = new QuestionRepositoryFilebased(filePath);
        INSTANCES.put(filePath, repositoryFilebased);
        return repositoryFilebased;
    }

    private final Random random = new Random();
    private final String filePath;


    private QuestionRepositoryFilebased(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Question> getQuestionList(int amount) throws CouldNotAccessFileException {
        return new ArrayList<>(readQuestionFile(filePath, amount));
    }

    private List<Question> readQuestionFile(String file, int amount) throws CouldNotAccessFileException {
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
            throw new CouldNotAccessFileException();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionRepositoryFilebased that = (QuestionRepositoryFilebased) o;
        return Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }
}
