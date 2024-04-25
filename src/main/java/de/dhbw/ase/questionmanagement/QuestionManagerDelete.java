package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.play.games.repository.CouldNotAccessFileException;
import de.dhbw.ase.play.games.repository.QuestionRepository;

public class QuestionManagerDelete implements Startable {

    private final QuestionRepository questionRepository;
    private final QuestionManagerEditingMenu.SelectedLine selectedLine;


    public QuestionManagerDelete(QuestionRepository questionRepository,
                                 QuestionManagerEditingMenu.SelectedLine selectedLine) {

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
                            .filter(q -> q.getQuestionIndex() != (selectedLine.getSelectedLine()))
                            .map(q ->
                                    q.getQuestionIndex() < selectedLine.getSelectedLine() ? q :
                                            QuestionObject.Builder.create()
                                                    .fromQuestionObject(q)
                                                    .withQuestionIndex(q.getQuestionIndex() - 1)
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
}
