package de.dhbw.ase.play.games.multiplayer;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.multiplayer.quickquiz.MultiplayerQuick;
import de.dhbw.ase.play.games.multiplayer.quiz.MultiplayerQuiz;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MultiplayerMenu extends Submenu {
    public MultiplayerMenu(Scanner sc) {
        super(sc);
    }

    @Override
    protected Map<Character, SelectedMenu> createSelectionMap() {
        Map<Character, SelectedMenu> map = new HashMap<>();
        map.put('1', new SelectedMenu(new MultiplayerQuiz(getSc())));
        map.put('2', new SelectedMenu(new MultiplayerQuick(getSc())));
        map.put('3', new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put('4', new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

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
