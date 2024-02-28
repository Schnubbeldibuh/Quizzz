package menu.play.games;

import menu.SelectedMenu;
import menu.Startable;

public abstract class Game implements Startable {

    @Override
    public SelectedMenu.MenuSelection start() {
        //TODO
        return SelectedMenu.MenuSelection.BACK;
    }
}
