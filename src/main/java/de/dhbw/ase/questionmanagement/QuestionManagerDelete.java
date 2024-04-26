package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

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
            List<QuestionObject> lines = questionRepository.readCompleteFileAsQuestionObject();
            List<QuestionObject> newLines = new ArrayList<>();

            boolean changeIndex = false;
            for (QuestionObject q : lines) {
                if (q.getQuestionIndex() == selectedLine.getSelectedLine()) {
                    changeIndex = true;
                    continue;
                }

                if (changeIndex) {
                    QuestionObject newQ = QuestionObject.Builder.create()
                            .fromQuestionObject(q)
                            .withQuestionIndex(q.getQuestionIndex() - 1)
                            .build();
                    newLines.add(newQ);
                    continue;
                }

                newLines.add(q);
            }
            questionRepository.writeBackToFile(newLines);

        } catch (CouldNotAccessFileException e) {
            System.out.println("Das Programm konnte nicht auf die Daten zugreifen.");
            System.out.println("Die Änderungen wurden möglicherweise nicht gespeichert.");
            return SelectedMenu.MenuSelection.BACK;
        }

        return SelectedMenu.MenuSelection.BACK;
    }
}
