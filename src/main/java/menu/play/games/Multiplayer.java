package menu.play.games;

import menu.SelectedMenu;
import menu.Submenu;

public class Multiplayer implements Submenu {

    public SelectedMenu.MenuSelection start() {
        System.out.println("Multiplayer");
        return SelectedMenu.MenuSelection.EXIT;
    }
}
