package menu.questionmanagement;

import menu.SelectedMenu;
import menu.Submenu;

import java.util.Scanner;

public class QuestionMenu extends Submenu {

    public QuestionMenu(Scanner sc) {
        super(sc);
    }

    @Override
    protected void showOptions() {
        System.out.println("Fragen");
        //TODO
    }

    @Override
    protected SelectedMenu scanSelection(String input) {
        return new SelectedMenu(SelectedMenu.MenuSelection.BACK, null);
        //TODO
    }

    public SelectedMenu.MenuSelection start() {
        System.out.println("Fragen");
        return SelectedMenu.MenuSelection.EXIT;
    }
}
