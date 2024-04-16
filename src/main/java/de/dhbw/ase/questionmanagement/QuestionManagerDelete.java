package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;

import java.util.Scanner;

public class QuestionManagerDelete implements Startable {

    private QuestionManagerEditingMenu questionManagerEditingMenu;

    public QuestionManagerDelete(Scanner sc, QuestionManagerEditingMenu questionManagerEditingMenu) {
        this.questionManagerEditingMenu = questionManagerEditingMenu;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        return null;
    }
}
