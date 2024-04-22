package de.dhbw.ase.play;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.multiplayer.MultiplayerMenu;
import de.dhbw.ase.play.games.multiplayer.quickquiz.MultiplayerQuick;
import de.dhbw.ase.play.games.multiplayer.quiz.MultiplayerQuiz;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.singelplayer.FindQuestionsQuiz;
import de.dhbw.ase.play.games.singelplayer.SingleplayerQuiz;
import de.dhbw.ase.play.games.singelplayer.SingleplayerMenu;
import de.dhbw.ase.play.games.singelplayer.WerWirdMillionaer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PlayMenu extends Submenu {

    public PlayMenu(Scanner sc) {
        super(sc);
    }
    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new MultiplayerMenu(getSc())));
        map.put("2", new SelectedMenu(new SingleplayerMenu(getSc())));
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
