package menu.play;
import menu.SelectedMenu;
import menu.Submenu;
import menu.play.games.FindQuestions;
import menu.play.games.Multiplayer;
import menu.play.games.MultiplayerQuick;
import menu.play.games.WerWirdMillionär;

import java.util.Scanner;

public class PlayMenu implements Submenu {
    private final Scanner sc = new Scanner(System.in);
    public SelectedMenu.MenuSelection start() {
        System.out.println("Spielen");
        return showGameModeSelection();
    }

    private SelectedMenu.MenuSelection showGameModeSelection() {
        System.out.println("Welchen Spielmodus möchtest du spielen?");
        System.out.println("1 - Wer wird Millionär?");
        System.out.println("2 - Multiplayer");
        System.out.println("3 - Multiplayer - Schnellmodus");
        System.out.println("4 - Fragenfinder");
        System.out.println("5 - Zurück");
        System.out.println("6 - Exit");

        SelectedMenu gameSelection;

        do {
            gameSelection = scanSelection();
        } while (gameSelection.menuSelection() == SelectedMenu.MenuSelection.INVALID);

        if (gameSelection.menuSelection() == SelectedMenu.MenuSelection.SUBMENU) {
            gameSelection.submenu().start();
        }
        return gameSelection.menuSelection();
    }

    private SelectedMenu scanSelection() {
        String playMenuSelection = sc.next();

        switch (playMenuSelection.charAt(0)){
            case '1':
                return new SelectedMenu(SelectedMenu.MenuSelection.SUBMENU, new WerWirdMillionär());
            case '2':
                return new SelectedMenu(SelectedMenu.MenuSelection.SUBMENU, new Multiplayer());
            case '3':
                return new SelectedMenu(SelectedMenu.MenuSelection.SUBMENU, new MultiplayerQuick());
            case '4':
                return new SelectedMenu(SelectedMenu.MenuSelection.SUBMENU, new FindQuestions());
            case '5':
                return new SelectedMenu(SelectedMenu.MenuSelection.BACK, null);
            case '6':
                return new SelectedMenu(SelectedMenu.MenuSelection.EXIT, null);
            default:
                System.out.println("Invalide Eingabe. Bitte erneut wählen.");
                return new SelectedMenu(SelectedMenu.MenuSelection.INVALID, null);
        }
    }
}
