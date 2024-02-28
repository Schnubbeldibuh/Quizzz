package menu;

import menu.play.PlayMenu;
import menu.questionmanagement.QuestionMenu;
import menu.stats.StatsMenu;

import java.util.Scanner;

public class Menu extends Submenu {
    public static void main(String[] args) {
        Menu menu = new Menu(new Scanner(System.in));
        menu.startGame();
    }

    public Menu(Scanner sc) {
        super(sc);
    }

    public void startGame() {
        System.out.println("Herzlichen Willkommen bei Quizzz!");
        start();
    }

    @Override
    protected void showOptions() {
        System.out.println("Hauptmenü");
        System.out.println("Wähle: ");
        System.out.println("1 - Spielen");
        System.out.println("2 - Fragenverwaltung");
        System.out.println("3 - Statistiken");
        System.out.println("4 - Exit");
    }

    @Override
    protected SelectedMenu scanSelection(String input) {
        switch (input.charAt(0)){
            case '1':
                return new SelectedMenu(new PlayMenu(getSc()));
            case '2':
                return new SelectedMenu(new QuestionMenu(getSc()));
            case '3':
                return new SelectedMenu(new StatsMenu(getSc()));
            case '4':
                return new SelectedMenu(SelectedMenu.MenuSelection.EXIT);
            default:
                return new SelectedMenu(SelectedMenu.MenuSelection.INVALID);
        }
    }
}
