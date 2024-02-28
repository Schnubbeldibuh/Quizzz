package menu.questionmanagement;

import menu.SelectedMenu;
import menu.Submenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QuestionMenu extends Submenu {

    public QuestionMenu(Scanner sc) {
        super(sc);
    }

    @Override
    protected Map<Character, SelectedMenu> createSelectionMap() {
        return new HashMap<>();
        //TODO
    }

    @Override
    protected void showOptions() {
        System.out.println("Fragen");
        //TODO
    }
}
