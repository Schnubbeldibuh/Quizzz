package menu.questionmanagement;

import menu.SelectedMenu;
import menu.Submenu;

public class QuestionMenu extends Submenu {

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
