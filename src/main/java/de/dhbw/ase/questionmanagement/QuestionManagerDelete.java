package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.user.in.UserIn;

public class QuestionManagerDelete extends QuetionEditor {

    private final QuestionManagerEditingMenu questionManagerEditingMenu;

    public QuestionManagerDelete(QuestionManagerEditingMenu questionManagerEditingMenu) {
        this.questionManagerEditingMenu = questionManagerEditingMenu;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        readFileContent(questionManagerEditingMenu.getFilePath());
        getQuestionIndex(questionManagerEditingMenu.getSelectedLine());
        file.remove(this.questionIndex);

        for (int i = 0; i < this.questionIndex; i++) {
            int currentQuestionIndex = Integer.parseInt(file.get(i).getQuestionIndex());
            file.get(i).setQuestionIndex(String.valueOf(currentQuestionIndex - 1));
        }
        writeBackToFile(questionManagerEditingMenu.getFilePath());
        return SelectedMenu.MenuSelection.BACK;
    }
}
