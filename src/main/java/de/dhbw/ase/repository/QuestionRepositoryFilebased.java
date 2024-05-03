package de.dhbw.ase.repository;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.repository.question.AnswerProblemException;
import de.dhbw.ase.repository.question.Question;

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

    private final String fileName;
    private final List<Question> fileContent = new ArrayList<>();

    private QuestionRepositoryFilebased(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getQuestionList(int amount) throws CouldNotAccessFileException {
        LinkedList<Question> questions = new LinkedList<>(readCompleteFile());
        List<Question> selectedQuestions = new ArrayList<>();

        Collections.shuffle(questions);

        while (selectedQuestions.size() < amount) {
            if (questions.size() == 0) {
                selectedQuestions.add(Question.Builder.createFakeQuestion());
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

        List<Question> questions;
        try (BufferedReader bufferedReader = new BufferedReader(createInputStreamReader())) {
            questions = bufferedReader.lines()
                    .filter(this::checkIfLineIsEmpty)
                    .map(this::mapLineToQuestion)
                    .toList();
        } catch (IOException | AnswerProblemException e) {
            throw new CouldNotAccessFileException();
        }
        setBufferedFileContent(questions);
        return questions;
    }

    private InputStreamReader createInputStreamReader() throws FileNotFoundException {
        File externalFile = new File(Quizzz.QUESTION_DIR + fileName);
        if (externalFile.exists()) {
            return new FileReader(externalFile);
        } else {
            return new InputStreamReader(Objects.requireNonNull(Quizzz.class.getResourceAsStream(fileName)));
        }
    }

    private boolean checkIfLineIsEmpty(String l) {
        return !l.isEmpty();
    }

    @Override
    public void writeBackToFile(List<Question> lineList) throws CouldNotAccessFileException {
        List<Question> questionObjects = new ArrayList<>(lineList);

        createDirIfNeeded();
        File file = new File(Quizzz.QUESTION_DIR + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CouldNotAccessFileException();
        }

        int id = 0;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Question question : questionObjects) {
                bufferedWriter.write(id++ + ";" + question.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new CouldNotAccessFileException();
        }

        setBufferedFileContent(questionObjects);
    }

    private void createDirIfNeeded() {
        File file = new File(Quizzz.QUESTION_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public void writeBackToFile(Set<Question> lineList) throws CouldNotAccessFileException {
        writeBackToFile(new ArrayList<>(lineList));
    }

    private void setBufferedFileContent(List<Question> questionObjects) {
        fileContent.clear();
        fileContent.addAll(questionObjects);
    }

    private Question mapLineToQuestion(String line) throws AnswerProblemException {
        return Question.Builder
                .create()
                .fromCompleteLine(line)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionRepositoryFilebased that = (QuestionRepositoryFilebased) o;
        return Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}
