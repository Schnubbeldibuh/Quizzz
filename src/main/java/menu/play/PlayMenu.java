package menu.play;

import menu.SelectedMenu;
import menu.Submenu;
import menu.play.games.FindQuestions;
import menu.play.games.Multiplayer;
import menu.play.games.MultiplayerQuick;
import menu.play.games.WerWirdMillionaer;

import java.util.Scanner;

public class PlayMenu extends Submenu {

    public PlayMenu(Scanner sc) {
        super(sc);
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

    @Override
    protected SelectedMenu scanSelection(String input) {
        switch (input.charAt(0)){
            case '1':
                return new SelectedMenu(new WerWirdMillionaer());
            case '2':
                return new SelectedMenu(new Multiplayer());
            case '3':
                return new SelectedMenu(new MultiplayerQuick());
            case '4':
                return new SelectedMenu(new FindQuestions());
            case '5':
                return new SelectedMenu(SelectedMenu.MenuSelection.BACK);
            case '6':
                return new SelectedMenu(SelectedMenu.MenuSelection.EXIT);
            default:
                return new SelectedMenu(SelectedMenu.MenuSelection.INVALID);
        }
    }
}
