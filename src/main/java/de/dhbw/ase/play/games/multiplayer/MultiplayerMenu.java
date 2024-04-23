package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.multiplayer.core.MultiplayerGame;
import de.dhbw.ase.play.games.multiplayer.quickquiz.MultiplayerQuickServerClientFactory;
import de.dhbw.ase.play.games.multiplayer.quiz.MultiplayerQuizServerClientFactory;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.Map;

public class MultiplayerMenu extends Submenu {
    public MultiplayerMenu(UserIn sc) {
        super(sc);
    }

    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {
        UserIn sc = getSc();

        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new MultiplayerGame(sc, new MultiplayerQuizServerClientFactory(sc))));
        map.put("2", new SelectedMenu(new MultiplayerGame(sc, new MultiplayerQuickServerClientFactory(sc))));
        map.put("3", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Multiplayer-Modus möchtest du spielen?");
        System.out.println("1 - Multiplayer - normal");
        System.out.println("2 - Multiplayer - Schnellmodus");
        System.out.println("3 - Zurück");
        System.out.println("4 - Exit");
    }
}
