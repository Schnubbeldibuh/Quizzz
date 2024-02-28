package menu;

import menu.play.PlayMenu;
import menu.questionmanagement.QuestionMenu;
import menu.stats.StatsMenu;

import java.util.Optional;
import java.util.Scanner;

public class Menu {
    public record selectedMenu () {

    }
    private final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showSelection();
    }

    public void showSelection (){
        SelectedMenu.MenuSelection menuSelection;
        do {
            System.out.println("Herzlichen Willkommen bei Quizzz!");
            System.out.println("Wähle: ");
            System.out.println("1 - Spielen");
            System.out.println("2 - Fragenverwaltung");
            System.out.println("3 - Statistiken");
            System.out.println("4 - Exit");

            SelectedMenu selection;

            do {
                selection = scanSelection();
            } while (selection.menuSelection() == SelectedMenu.MenuSelection.INVALID);

            if (selection.menuSelection() == SelectedMenu.MenuSelection.SUBMENU) {
                menuSelection = selection.submenu().start();
            } else {
                menuSelection = SelectedMenu.MenuSelection.EXIT;
            }
        } while (menuSelection != SelectedMenu.MenuSelection.EXIT);
    }

    private SelectedMenu scanSelection() {
        String menuSelection = sc.next();

        switch (menuSelection.charAt(0)){
            case '1':
                return new SelectedMenu(SelectedMenu.MenuSelection.SUBMENU, new PlayMenu());
            case '2':
                return new SelectedMenu(SelectedMenu.MenuSelection.SUBMENU, new QuestionMenu());
            case '3':
                return new SelectedMenu(SelectedMenu.MenuSelection.SUBMENU, new StatsMenu());
            case '4':
                return new SelectedMenu(SelectedMenu.MenuSelection.EXIT, null);
            default:
                System.out.println("Invalide Eingabe. Bitte erneut wählen.");
                return new SelectedMenu(SelectedMenu.MenuSelection.INVALID, null);
        }
    }
}
