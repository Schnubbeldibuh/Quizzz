package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.QuestionRepository;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionManagerEdit implements Startable {

    private final UserIn sc;
    private final QuestionRepository questionRepository;
    private final QuestionManagerEditingMenu.SelectedLine selectedLine;

    public QuestionManagerEdit(UserIn sc,
                               QuestionRepository questionRepository,
                               QuestionManagerEditingMenu.SelectedLine selectedLine) {
        this.sc = sc;
        this.questionRepository = questionRepository;
        this.selectedLine = selectedLine;
    }

    @Override
    public SelectedMenu.MenuSelection start() {

        try {

            List<Question> lines = new ArrayList<>(questionRepository.readCompleteFile());
            Question q = lines.get(selectedLine.getSelectedLine());
            Iterator<Question.Answer> answer = q.getAnswerList().iterator();

            Question updatedQuestion = Question.Builder.create()
                    .withQuestion(editQuestion(q.getQuestion()))
                    .withRightAnswer(editRightAnswer(answer.next().answer()))
                    .withWrongAnswer(editWrongAnswer(1, answer.next().answer()))
                    .withWrongAnswer(editWrongAnswer(2, answer.next().answer()))
                    .withWrongAnswer(editWrongAnswer(3, answer.next().answer()))
                    .build();

            lines.remove(q);
            lines.add(updatedQuestion);

            questionRepository.writeBackToFile(lines);
        } catch (CouldNotAccessFileException e) {
            System.out.println("Das Programm konnte nicht auf die Daten zugreifen.");
            System.out.println("Die Änderungen wurden möglicherweise nicht gespeichert.");
            return SelectedMenu.MenuSelection.BACK;
        }

        return SelectedMenu.MenuSelection.BACK;
    }

    private String editWrongAnswer(int i, String originalAnswer) {
        System.out.println();
        System.out.println("Die aktuelle Antwort ist: " + originalAnswer);
        System.out.println("Falls du die falsche Antwort " + i + " ändern möchtest: Neue Frage eingeben.");
        System.out.println("Falls du die falsche Antwort " + i + " beibehalten möchtest, einfach Enter drücken");

        String input;
        input = sc.waitForNextLine(this);
        return input.isEmpty() ? originalAnswer : input;

    }

    private String editRightAnswer(String originalAnswer) {
        System.out.println();
        System.out.println("Die aktuelle Antwort ist: " + originalAnswer);
        System.out.println("Falls du die richtige Antwort ändern möchtest: Neue Frage eingeben.");
        System.out.println("Falls du die richtige Antwort beibehalten möchtest, einfach Enter eingeben.");

        String input;
        input = sc.waitForNextLine(this);
        return input.isEmpty() ? originalAnswer : input;
    }

    private String editQuestion(String originalQuestion) {
        System.out.println();
        System.out.println("Die aktuelle Frage ist: " + originalQuestion);
        System.out.println("Falls du die Frage ändern möchtest: Neue Frage eingeben.");
        System.out.println("Falls du die Frage beibehalten möchtest, einfach Enter eingeben.");

        String input = sc.waitForNextLine(this);
        if (input.isEmpty()) {
            return originalQuestion;
        }
        return input;
    }
}
