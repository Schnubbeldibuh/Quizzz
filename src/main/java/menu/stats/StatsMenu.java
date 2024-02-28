package menu.stats;

import menu.SelectedMenu;
import menu.Submenu;

public class StatsMenu extends Submenu {

    @Override
    protected void showOptions() {
        System.out.println("Statistiken");
        //TODO
    }

    @Override
    protected SelectedMenu scanSelection(String input) {
        return new SelectedMenu(SelectedMenu.MenuSelection.BACK, null);
        //TODO
    }
}
