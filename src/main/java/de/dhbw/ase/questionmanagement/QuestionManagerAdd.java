package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.play.games.repository.CouldNotAccessFileException;
import de.dhbw.ase.play.games.repository.QuestionRepository;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.List;

public class QuestionManagerAdd implements Startable {

    private final UserIn sc;
    private final QuestionRepository questionRepository;
    public QuestionManagerAdd(UserIn sc, QuestionRepository questionRepository) {
        this.sc = sc;
        this.questionRepository = questionRepository;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        QuestionObject.Builder builder = QuestionObject.Builder.create();

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
        builder.withWrongAnswer1(input);

        System.out.println();
        System.out.println("Bitte gib eine andere falsche Antwort ein:");
        input = sc.waitForNextLine(this);
        builder.withWrongAnswer2(input);

        System.out.println();
        System.out.println("Bitte gib noch eine andere falsche Antwort ein:");
        input = sc.waitForNextLine(this);
        builder.withWrongAnswer3(input);

        System.out.println();

        List<QuestionObject> fileContent;
        try {
            fileContent = new ArrayList<>(questionRepository.readCompleteFileAsQuestionObject());
        } catch (CouldNotAccessFileException e) {
            System.out.println("Das Programm konnte nicht auf die Daten zugreifen.");
            System.out.println("Die Änderungen wurden nicht gespeichert.");
            return SelectedMenu.MenuSelection.BACK;
        }

        builder.withQuestionIndex(fileContent.size());
        fileContent.add(builder.build());

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
