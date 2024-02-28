package menu;

import menu.play.PlayMenu;
import menu.questionmanagement.QuestionMenu;
import menu.stats.StatsMenu;

public class Menu extends Submenu {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.startGame();
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
                return new SelectedMenu(new PlayMenu());
            case '2':
                return new SelectedMenu(new QuestionMenu());
            case '3':
                return new SelectedMenu(new StatsMenu());
            case '4':
                return new SelectedMenu(SelectedMenu.MenuSelection.EXIT);
            default:
                System.out.println("Invalide Eingabe. Bitte erneut wählen.");
                return new SelectedMenu(SelectedMenu.MenuSelection.INVALID);
        }
    }
}
