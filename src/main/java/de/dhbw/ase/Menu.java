package de.dhbw.ase;

import de.dhbw.ase.play.PlayMenu;
import de.dhbw.ase.questionmanagement.QuestionMenu;
import de.dhbw.ase.stats.StatsMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu extends Submenu {

    public static void main(String[] args) {
        Menu menu = new Menu(new Scanner(System.in));
        menu.startGame();
    }

    public Menu(Scanner sc) {
        super(sc);
    }

    public void startGame() {
        System.out.println("Herzlichen Willkommen bei Quizzz!");
        start();
    }

    @Override
    protected Map<Character, SelectedMenu> createSelectionMap() {
        Map<Character, SelectedMenu> map = new HashMap<>();
        map.put('1', new SelectedMenu(new PlayMenu(getSc())));
        map.put('2', new SelectedMenu(new QuestionMenu(getSc())));
        map.put('3', new SelectedMenu(new StatsMenu(getSc())));
        map.put('4', new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Hauptmenü");
        System.out.println("Wähle: ");
        System.out.println("1 - Spielen");
        System.out.println("2 - Fragenverwaltung");
        System.out.println("3 - Statistiken");
        System.out.println("4 - Exit");
    }
}
