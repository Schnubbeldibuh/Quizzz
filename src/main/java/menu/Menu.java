package menu;

import menu.play.PlayMenu;
import menu.questionmanagement.QuestionMenu;
import menu.stats.StatsMenu;

import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showSelection();

    }

    public void showSelection (){
        System.out.println("Herzlichen Willkommen bei Quizzz!");
        System.out.println("Wählen Sie: ");
        System.out.println("1 - Spielen");
        System.out.println("2 - Fragenverwaltung");
        System.out.println("3 - Statistiken");
        System.out.println("4 - Exit");

        Optional<Submenu> selection;

        do {
            selection = scanSelection();
        } while (selection.isEmpty());

        selection.get().start();
    }

    private Optional<Submenu> scanSelection() {
        String menuSelection = sc.next();

        switch (menuSelection.charAt(0)){
            case '1':
                return Optional.of(new PlayMenu());
            case '2':
                return Optional.of(new QuestionMenu());
            case '3':
                return Optional.of(new StatsMenu());
            case '4':
                return Optional.of(new Exit());
            default:
                System.out.println("Invalide Eingabe. Bitte erneut wählen.");
                return Optional.empty();
        }
    }
}
