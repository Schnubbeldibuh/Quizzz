package menu.play.games;

import menu.SelectedMenu;
import menu.Submenu;

public class FindQuestions implements Submenu {

    public SelectedMenu.MenuSelection start() {
        System.out.println("Finde die passende Frage");
        return SelectedMenu.MenuSelection.EXIT;
    }
}
