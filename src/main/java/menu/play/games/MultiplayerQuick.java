package menu.play.games;
import menu.SelectedMenu;
import menu.Submenu;
import menu.play.PlayMenu;

public class MultiplayerQuick implements Submenu {

    public SelectedMenu.MenuSelection start() {
        System.out.println("Multiplayer - Schnellmodus");
        return SelectedMenu.MenuSelection.EXIT;
    }
}
