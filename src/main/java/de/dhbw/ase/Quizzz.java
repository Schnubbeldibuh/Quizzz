package de.dhbw.ase;

import java.util.Scanner;

public class Quizzz {



    public static void main(String[] args) {
        Mainmenu menu = new Mainmenu(new Scanner(System.in));
        menu.startGame();
    }
}
