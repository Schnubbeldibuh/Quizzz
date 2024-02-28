package menu.play.games;

import menu.SelectedMenu;
import menu.Submenu;

public class WerWirdMillionär implements Submenu {

    public SelectedMenu.MenuSelection start() {
        System.out.println("Wer wird Millionär");
        return SelectedMenu.MenuSelection.EXIT;
    }
}
