package menu.stats;

import menu.SelectedMenu;
import menu.Submenu;

import java.util.Scanner;

public class StatsMenu extends Submenu {

    public StatsMenu(Scanner sc) {
        super(sc);
    }

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
