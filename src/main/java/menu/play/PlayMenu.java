package menu.play;

import menu.SelectedMenu;
import menu.Submenu;
import menu.play.games.FindQuestions;
import menu.play.games.Multiplayer;
import menu.play.games.MultiplayerQuick;
import menu.play.games.WerWirdMillionaer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PlayMenu extends Submenu {

    public PlayMenu(Scanner sc) {
        super(sc);
    }

    @Override
    protected Map<Character, SelectedMenu> createSelectionMap() {
        Map<Character, SelectedMenu> map = new HashMap<>();
        map.put('1', new SelectedMenu(new WerWirdMillionaer()));
        map.put('2', new SelectedMenu(new Multiplayer()));
        map.put('3', new SelectedMenu(new MultiplayerQuick()));
        map.put('4', new SelectedMenu(new FindQuestions()));
        map.put('5', new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put('6', new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Spielen");
        System.out.println("Welchen Spielmodus möchtest du spielen?");
        System.out.println("1 - Wer wird Millionär?");
        System.out.println("2 - Multiplayer");
        System.out.println("3 - Multiplayer - Schnellmodus");
        System.out.println("4 - Fragenfinder");
        System.out.println("5 - Zurück");
        System.out.println("6 - Exit");
    }
}
