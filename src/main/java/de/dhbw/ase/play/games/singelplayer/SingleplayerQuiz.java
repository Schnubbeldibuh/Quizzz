package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.multiplayer.quickquiz.MultiplayerQuick;
import de.dhbw.ase.play.games.multiplayer.quiz.MultiplayerQuiz;
import de.dhbw.ase.play.games.singelplayer.category.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SingleplayerQuiz extends Submenu {

    public SingleplayerQuiz(Scanner sc) {super(sc);}

    @Override
    protected Map<Character, SelectedMenu> createSelectionMap() {
        Map<Character, SelectedMenu> map = new HashMap<>();
        map.put('1', new SelectedMenu(new SPRandom(getSc(), Quizzz.FILE_STATS_SP_RANDOM)));
        map.put('2', new SelectedMenu(new SPGeneral(getSc(), Quizzz.FILE_STATS_SP_GENERAL)));
        map.put('3', new SelectedMenu(new SPGeography(getSc(), Quizzz.FILE_STATS_SP_GEOGRAPHY)));
        map.put('4', new SelectedMenu(new SPCinemaTV(getSc(), Quizzz.FILE_STATS_SP_CINEMA_TV)));
        map.put('5', new SelectedMenu(new SPTechnology(getSc(), Quizzz.FILE_STATS_SP_TECHNOLOGY)));
        map.put('6', new SelectedMenu(new SPSports(getSc(), Quizzz.FILE_STATS_SP_SPORTS)));
        map.put('7', new SelectedMenu(new SPFood(getSc(), Quizzz.FILE_STATS_SP_FOOD)));
        map.put('8', new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put('9', new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welche Kategorie möchtest du spielen?");
        System.out.println("1 - Zufall");
        System.out.println("2 - Allgemeinwissen");
        System.out.println("3 - Geographie");
        System.out.println("4 - Film und Fernsehen");
        System.out.println("5 - Technologie und Internet");
        System.out.println("6 - Sport");
        System.out.println("7 - Essen und Trinken");
        System.out.println("8 - Zurück");
        System.out.println("9 - Exit");
    }
}
