package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.QuestionRepository;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashSet;
import java.util.Set;

public class QuestionManagerAdd implements Startable {

    private final UserIn sc;
    private final QuestionRepository questionRepository;
    public QuestionManagerAdd(UserIn sc, QuestionRepository questionRepository) {
        this.sc = sc;
        this.questionRepository = questionRepository;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        Question.Builder builder = Question.Builder.create();

        System.out.println("Bitte gib die neue Frage ein: ");
        String input = sc.waitForNextLine(this);
        builder.withQuestion(input);

        System.out.println();
        System.out.println("Bitte gib die richtige Antwort ein:");
        input = sc.waitForNextLine(this);
        builder.withRightAnswer(input);

        System.out.println();
        System.out.println("Bitte gib eine falsche Antwort ein:");
        input = sc.waitForNextLine(this);
        builder.withWrongAnswer(input);

        System.out.println();
        System.out.println("Bitte gib eine andere falsche Antwort ein:");
        input = sc.waitForNextLine(this);
        builder.withWrongAnswer(input);

        System.out.println();
        System.out.println("Bitte gib noch eine andere falsche Antwort ein:");
        input = sc.waitForNextLine(this);
        builder.withWrongAnswer(input);

        System.out.println();

        Set<Question> fileContent;
        try {
            fileContent = new HashSet<>(questionRepository.readCompleteFile());
        } catch (CouldNotAccessFileException e) {
            System.out.println("Das Programm konnte nicht auf die Daten zugreifen.");
            System.out.println("Die Änderungen wurden nicht gespeichert.");
            return SelectedMenu.MenuSelection.BACK;
        }

        boolean successfulAdded = fileContent.add(builder.build());
        if (successfulAdded) {
            System.out.println("Diese Frage existiert bereits");
            return SelectedMenu.MenuSelection.BACK;
        }

        try {
            questionRepository.writeBackToFile(fileContent);
        } catch (CouldNotAccessFileException e) {
            System.out.println("Das Programm konnte nicht auf die Daten zugreifen.");
            System.out.println("Die Änderungen wurden möglicherweise nicht gespeichert.");
            return SelectedMenu.MenuSelection.BACK;
        }

        return SelectedMenu.MenuSelection.BACK;
    }
}
