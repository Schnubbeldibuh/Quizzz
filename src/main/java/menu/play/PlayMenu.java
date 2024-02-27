package menu.play;
import menu.Exit;
import menu.Submenu;
import menu.play.games.FindQuestions;
import menu.play.games.Multiplayer;
import menu.play.games.MultiplayerQuick;
import menu.play.games.WerWirdMillionär;

import java.util.Optional;
import java.util.Scanner;

public class PlayMenu implements Submenu {
    private final Scanner sc = new Scanner(System.in);
    public void start() {
        System.out.println("Spielen");
        PlayMenu playMenu = new PlayMenu();
        playMenu.showGameModeSelection();
        //Todo
    }

    private void showGameModeSelection() {
        System.out.println("Welchen Spielmodus möchtest du spielen?");
        System.out.println("1 - Wer wird Millionär?");
        System.out.println("2 - Multiplayer");
        System.out.println("3 - Multiplayer - Schnellmodus");
        System.out.println("3 - Fragenfinder");
        System.out.println("4 - Exit");

        Optional<Submenu> gameSelection;

        do {
            gameSelection = scanSelection();
        } while (gameSelection.isEmpty());

        gameSelection.get().start();
    }

    private Optional<Submenu> scanSelection() {
        String playMenuSelection = sc.next();

        switch (playMenuSelection.charAt(0)){
            case '1':
                return Optional.of(new WerWirdMillionär());
            case '2':
                return Optional.of(new Multiplayer());
            case '3':
                return Optional.of(new MultiplayerQuick());
            case '4':
                return Optional.of(new FindQuestions());
            case '5':
                return Optional.of(new Exit());
            default:
                System.out.println("Invalide Eingabe. Bitte erneut wählen.");
                return Optional.empty();
        }
    }
}
