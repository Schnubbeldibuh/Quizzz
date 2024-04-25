package de.dhbw.ase.repository;

import de.dhbw.ase.questionmanagement.QuestionObject;

import java.io.*;
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

    private final String filePath;

    private final List<String> fileContentAsString = new ArrayList<>();
    private final List<Question> fileContent = new ArrayList<>();
    private final List<QuestionObject> fileContentAsQuestionObject = new ArrayList<>();

    private QuestionRepositoryFilebased(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Question> getQuestionList(int amount) throws CouldNotAccessFileException {
        LinkedList<Question> questions = new LinkedList<>(readCompleteFile());
        List<Question> selectedQuestions = new ArrayList<>();

        List<Question.Answer> fakeAnswers = new ArrayList<>();
        fakeAnswers.add(new Question.Answer("Richtig", true));
        fakeAnswers.add(new Question.Answer("Falsch", false));
        fakeAnswers.add(new Question.Answer("Falsch", false));
        fakeAnswers.add(new Question.Answer("Falsch", false));
        Question fakeQuestion = new Question(fakeAnswers,
                "Es gab nicht genug Fragen zu diesem Speilmodus. " +
                        "Also wähle die Richtige Antwort:");

        Collections.shuffle(questions);

        while (selectedQuestions.size() < amount) {
            if (questions.size() == 0) {
                selectedQuestions.add(fakeQuestion);
                continue;
            }
            selectedQuestions.add(questions.poll());
        }

        return selectedQuestions;
    }

    @Override
    public List<Question> readCompleteFile() throws CouldNotAccessFileException {
        if (!fileContent.isEmpty()) {
            return fileContent;
        }

        return readCompleteFileAsString()
                .stream()
                .map(this::mapLineToQuestion)
                .toList();
    }

    @Override
    public List<QuestionObject> readCompleteFileAsQuestionObject() throws CouldNotAccessFileException {
        if (fileContentAsQuestionObject.isEmpty()) {
            readCompleteFileAsString()
                    .stream()
                    .map(QuestionObject::new)
                    .forEach(fileContentAsQuestionObject::add);
        }

        return Collections.unmodifiableList(fileContentAsQuestionObject);
    }

    @Override
    public List<String> readCompleteFileAsString() throws CouldNotAccessFileException {
        if (!fileContentAsQuestionObject.isEmpty() && !fileContentAsString.isEmpty()) {
            fileContentAsQuestionObject
                    .stream()
                    .map(QuestionObject::toString)
                    .forEach(fileContentAsString::add);

        } else if (fileContentAsString.isEmpty()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                bufferedReader.lines()
                        .filter(l -> !l.isEmpty())
                        .forEach(fileContentAsString::add);

            } catch (IOException e) {
                throw new CouldNotAccessFileException();
            }
        }
        return Collections.unmodifiableList(fileContentAsString);
    }

    @Override
    public void writeBackToFile(List<QuestionObject> lineList) throws CouldNotAccessFileException {
        ArrayList<QuestionObject> questionObjects = new ArrayList<>(lineList);
        Collections.sort(questionObjects);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (QuestionObject questionObject : questionObjects) {
                bufferedWriter.write(questionObject.getCompleteLine());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new CouldNotAccessFileException();
        }

        fileContentAsString.clear();
        fileContent.clear();
        fileContentAsQuestionObject.clear();
        fileContentAsQuestionObject.addAll(questionObjects);
    }

    private Question mapLineToQuestion(String line) {
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
