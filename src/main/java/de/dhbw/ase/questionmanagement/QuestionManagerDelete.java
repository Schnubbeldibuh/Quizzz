package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;

import java.util.Scanner;

public class QuestionManagerDelete extends QuetionEditor {

    private final QuestionManagerEditingMenu questionManagerEditingMenu;

    public QuestionManagerDelete(Scanner sc, QuestionManagerEditingMenu questionManagerEditingMenu) {
        this.questionManagerEditingMenu = questionManagerEditingMenu;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        //QuestionObject questionObject = null;
        readFileContent(questionManagerEditingMenu.getFilePath());
        getQuestionIndex(questionManagerEditingMenu.getSelectedLine());
        /*for (int i = 0; i < file.size(); i++) {
            questionObject = file.get(i);
            System.out.println(questionObject);
            if (Integer.parseInt(questionObject.getQuestionIndex()) == (questionIndex-1)) {
                break;
            }
        }
        int questionIndex = file.indexOf(questionObject);*/
        file.remove(this.questionIndex);

        for (int i = 0; i < this.questionIndex; i++) {
            int currentQuestionIndex = Integer.parseInt(file.get(i).getQuestionIndex());
            file.get(i).setQuestionIndex(String.valueOf(currentQuestionIndex - 1));
        }
        //questionObject.setQuestionIndex(file.size() + "");
        writeBackToFile(questionManagerEditingMenu.getFilePath());
        return SelectedMenu.MenuSelection.BACK;
    }
}
