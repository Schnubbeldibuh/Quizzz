package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.play.games.repository.CouldNotAccessFileException;
import de.dhbw.ase.play.games.repository.QuestionRepository;
import de.dhbw.ase.user.in.UserIn;

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
            questionRepository.writeBackToFile(
                    questionRepository
                            .readCompleteFileAsQuestionObject()
                            .stream()
                            .map(q ->
                                    q.getQuestionIndex() != selectedLine.getSelectedLine() ? q :
                                            QuestionObject.Builder.create()
                                                    .withQuestionIndex(q.getQuestionIndex())
                                                    .withQuestion(editQuestion(q.getQuestion()))
                                                    .withRightAnswer(editRightAnswer(q.getRightAnswer()))
                                                    .withWrongAnswer1(editWrongAnswer(1, q.getWrongAnswer1()))
                                                    .withWrongAnswer2(editWrongAnswer(2, q.getWrongAnswer2()))
                                                    .withWrongAnswer3(editWrongAnswer(3, q.getWrongAnswer3()))
                                                    .build())
                            .toList()
            );
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
