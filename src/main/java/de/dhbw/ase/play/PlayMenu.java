package de.dhbw.ase.play;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.FindQuestions;
import de.dhbw.ase.play.games.WerWirdMillionaer;
import de.dhbw.ase.play.games.Multiplayer;
import de.dhbw.ase.play.games.MultiplayerQuick;

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
        map.put('1', new SelectedMenu(new WerWirdMillionaer(getSc())));
        map.put('2', new SelectedMenu(new Multiplayer(getSc())));
        map.put('3', new SelectedMenu(new MultiplayerQuick(getSc())));
        map.put('4', new SelectedMenu(new FindQuestions(getSc())));
        map.put('5', new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put('6', new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Spielmodus möchtest du spielen?");
        System.out.println("1 - Wer wird Millionär?");
        System.out.println("2 - Multiplayer");
        System.out.println("3 - Multiplayer - Schnellmodus");
        System.out.println("4 - Fragenfinder");
        System.out.println("5 - Zurück");
        System.out.println("6 - Exit");
    }
}
