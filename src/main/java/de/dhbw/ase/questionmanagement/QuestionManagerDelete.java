package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;

import java.util.Scanner;

public class QuestionManagerDelete extends QuetionEditor {

    private Scanner sc;
    private QuestionManagerEditingMenu questionManagerEditingMenu;

    public QuestionManagerDelete(Scanner sc, QuestionManagerEditingMenu questionManagerEditingMenu) {
        this.sc = sc;
        this.questionManagerEditingMenu = questionManagerEditingMenu;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        QuestionObject questionObject = null;
        readFileContent(questionManagerEditingMenu.getFilePath());
        getQuestionIndex(questionManagerEditingMenu.getSelectedLine());
        int tempIndex = questionIndex-1;
        for (int i = 0; i < file.size(); i++) {
            questionObject = file.get(i);
            System.out.println(questionObject);
            if (Integer.parseInt(questionObject.getQuestionIndex()) == tempIndex) {
                break;
            }
        }
        int questionIndex = file.indexOf(questionObject);
        file.remove(questionIndex);

        for (int i = 0; i < questionIndex; i++) {
            int currentQuestionIndex = Integer.parseInt(file.get(i).getQuestionIndex());
            file.get(i).setQuestionIndex(String.valueOf(currentQuestionIndex - 1));
        }
        questionObject.setQuestionIndex(file.size() + "");
        writeBackToFile(questionManagerEditingMenu.getFilePath());
        return SelectedMenu.MenuSelection.BACK;
    }
}
