package de.dhbw.ase.stats;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.repository.StatsRepositoryFilebased;
import de.dhbw.ase.stats.show.*;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.Map;

public class StatsMenu extends Submenu {

    public StatsMenu(UserIn sc) {
        super(sc);
    }

    @Override
    protected Map<String, SelectedMenu> generateSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();

        map.put("1", new SelectedMenu(
                new ShowStatsWWM(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_WWM))
        ));
        map.put("2", new SelectedMenu(
                new ShowStatsMP(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_MP))
        ));
        map.put("3", new SelectedMenu(
                new ShowStatsMPQuick(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_MP_QUICK))
        ));
        map.put("4", new SelectedMenu(
                new ShowStatsFQ(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_FQ))
        ));
        map.put("5", new SelectedMenu(
                new ShowStatsSP(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_GENERAL))
        ));
        map.put("6", new SelectedMenu(
                new ShowStatsSP(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_GEOGRAPHY))
        ));
        map.put("7", new SelectedMenu(
                new ShowStatsSP(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_CINEMA_TV))
        ));
        map.put("8", new SelectedMenu(
                new ShowStatsSP(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_TECHNOLOGY))
        ));
        map.put("9", new SelectedMenu(
                new ShowStatsSP(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_SPORTS))
        ));
        map.put("10", new SelectedMenu(
                new ShowStatsSP(
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_FOOD))
        ));
        map.put("11", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("12", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welche Statistik möchtest du einsehen?");
        System.out.println("1 - Wer wird Milionär?");
        System.out.println("2 - Multiplayer");
        System.out.println("3 - Multiplayer - Schnellmodus");
        System.out.println("4 - Fragenfinder");
        System.out.println("5 - Kategorie Allgemeinwissen");
        System.out.println("6 - Kategorie Geographie");
        System.out.println("7 - Kategorie Film und Fernsehen");
        System.out.println("8 - Kategorie Technologie und Internet");
        System.out.println("9 - Kategorie Sport");
        System.out.println("10 - Kategorie Essen und Trinken");
        System.out.println("11 - Zurück");
        System.out.println("12 - Exit");
    }
}
