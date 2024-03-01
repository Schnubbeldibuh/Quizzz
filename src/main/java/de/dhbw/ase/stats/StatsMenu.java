package de.dhbw.ase.stats;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StatsMenu extends Submenu {

    public StatsMenu(Scanner sc) {
        super(sc);
    }

    @Override
    protected Map<Character, SelectedMenu> createSelectionMap() {
        return new HashMap<>();
        //TODO
    }

    @Override
    protected void showOptions() {
        System.out.println("Statistiken");
        //TODO
    }
}
