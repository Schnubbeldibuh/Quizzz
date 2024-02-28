package menu.questionmanagement;

import menu.SelectedMenu;
import menu.Submenu;

public class QuestionMenu implements Submenu {
    public SelectedMenu.MenuSelection start() {
        System.out.println("Fragen");
        return SelectedMenu.MenuSelection.EXIT;
    }
}
