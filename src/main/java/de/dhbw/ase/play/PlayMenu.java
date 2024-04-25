package de.dhbw.ase.play;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.multiplayer.MultiplayerMenu;
import de.dhbw.ase.play.games.singelplayer.SingleplayerMenu;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.Map;

public class PlayMenu extends Submenu {

    private final UserIn sc;

    public PlayMenu(UserIn sc) {
        super(sc);
        this.sc = sc;
    }
    @Override
    protected Map<String, SelectedMenu> generateSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new MultiplayerMenu(sc)));
        map.put("2", new SelectedMenu(new SingleplayerMenu(sc)));
        map.put("3", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Spielmodus möchtest du spielen?");
        System.out.println("1 - Multiplayer");
        System.out.println("2 - Singleplayer");
        System.out.println("3 - Zurück");
        System.out.println("4 - Exit");
    }
}
