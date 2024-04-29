package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.repository.QuestionRepositoryMock;
import de.dhbw.ase.repository.question.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class QuestionManagerDeleteTest {

    @Test
    void start() {
        QuestionRepositoryMock repository = new QuestionRepositoryMock();
        Set<Question.Answer> answers = Set.of(
                new Question.Answer("1", true),
                new Question.Answer("2", false),
                new Question.Answer("3", false),
                new Question.Answer("4", false)
        );
        repository.stats = List.of(
                Question.Builder.createFakeQuestion(),
                Question.Builder.createFakeQuestion()
        );

        SelectedLinesMock selectedLine = new SelectedLinesMock();
        QuestionManagerDelete managerDelete = new QuestionManagerDelete(repository, selectedLine);

        selectedLine.setSelectedLine(0);

        managerDelete.start();

        int size = repository.savedStats.size();
        Assertions.assertEquals(1, size);
    }

    static class SelectedLinesMock extends QuestionManagerEditingMenu.SelectedLine {
        private int selectedLine;

        public int getSelectedLine() {
            return selectedLine;
        }

        public void setSelectedLine(int selectedLine) {
            this.selectedLine = selectedLine;
        }
    }
}