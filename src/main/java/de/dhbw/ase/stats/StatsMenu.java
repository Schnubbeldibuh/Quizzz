package de.dhbw.ase.stats;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.stats.show.ShowStatsFQ;
import de.dhbw.ase.stats.show.ShowStatsMP;
import de.dhbw.ase.stats.show.ShowStatsMPQuick;
import de.dhbw.ase.stats.show.ShowStatsWWM;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StatsMenu extends Submenu {

    public StatsMenu(Scanner sc) {
        super(sc);
    }

    @Override
    protected Map<Character, SelectedMenu> createSelectionMap() {
        Map<Character, SelectedMenu> map = new HashMap<>();
        map.put('1', new SelectedMenu(new ShowStatsWWM(Quizzz.FILE_STATS_WWM)));
        map.put('2', new SelectedMenu(new ShowStatsMP(Quizzz.FILE_STATS_MP)));
        map.put('3', new SelectedMenu(new ShowStatsMPQuick(Quizzz.FILE_STATS_MP_QUICK)));
        map.put('4', new SelectedMenu(new ShowStatsFQ(Quizzz.FILE_STATS_FQ)));
        map.put('5', new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put('6', new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welche Statistik möchtest du einsehen?");
        System.out.println("1 - Wer wird Milionär?");
        System.out.println("2 - Multiplayer");
        System.out.println("3 - Multiplayer - Schnellmodus");
        System.out.println("4 - Fragenfinder");
        System.out.println("5 - Zurück");
        System.out.println("6 - Exit");
    }
}
