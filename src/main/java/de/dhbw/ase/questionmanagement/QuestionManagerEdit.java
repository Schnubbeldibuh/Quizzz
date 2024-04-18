package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;

import java.util.Scanner;

public class QuestionManagerEdit extends QuetionEditor {

    private Scanner sc;
    private QuestionManagerEditingMenu questionManagerEditingMenu;

    public QuestionManagerEdit(Scanner sc, QuestionManagerEditingMenu questionManagerEditingMenu) {
        this.sc = sc;
        this.questionManagerEditingMenu = questionManagerEditingMenu;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        readFileContent(questionManagerEditingMenu.getFilePath());
        getQuestionIndex(questionManagerEditingMenu.getSelectedLine());
        editQuestion();
        editRightAnswer();
        for (int i = 2; i < 5; i++) {
            editWrongAnswer(i);
        }
        writeBackToFile(questionManagerEditingMenu.getFilePath());
        return SelectedMenu.MenuSelection.BACK;
    }

    private void editWrongAnswer(int i) {
        System.out.println();
        System.out.println("Falls du die falsche Antwort " + i + " ändern möchtest: Neue Frage eingeben.");
        System.out.println("Falls du die falsche Antwort " + i + " beibehalten möchtest: \";\" eingeben.");
        String input = sc.next();
        if (input.equals(";")) {
            return;
        }
        if (i == 1) {
            file.get(questionIndex).setWrongAnswer1(input);
        } else if (i == 2) {
            file.get(questionIndex).setWrongAnswer2(input);
        } else {
            file.get(questionIndex).setWrongAnswer3(input);
        }
    }

    private void editRightAnswer() {
        System.out.println();
        System.out.println("Falls du die richtige Antwort ändern möchtest: Neue Frage eingeben.");
        System.out.println("Falls du die richtige Antwort beibehalten möchtest: \";\" eingeben.");
        String input = sc.next();
        if (input.equals(";")) {
            return;
        }
        file.get(questionIndex).setRightAnswer(input);
    }

    private void editQuestion() {
        System.out.println();
        System.out.println("Falls du die Frage ändern möchtest: Neue Frage eingeben.");
        System.out.println("Falls du die Frage beibehalten möchtest: \";\" eingeben.");

        String input = sc.next();
        if (input.equals(";")) {
            return;
        }
        file.get(questionIndex).setQuestion(input);
    }

}
