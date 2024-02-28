package menu.stats;

import menu.SelectedMenu;
import menu.Submenu;

public class StatsMenu implements Submenu {
    public SelectedMenu.MenuSelection start() {
        System.out.println("Statistiken");
        return SelectedMenu.MenuSelection.EXIT;
    }
}
