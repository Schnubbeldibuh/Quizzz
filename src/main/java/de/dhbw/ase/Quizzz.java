package de.dhbw.ase;

import java.util.Scanner;

public class Quizzz {

    public static String FILE_WWM_EASY = "src/main/resources/WWMEasy.csv";
    public static String FILE_WWM_EXPERT = "src/main/resources/WWMExpert.csv";
    public static String FILE_WWM_HARD = "src/main/resources/WWMHard.csv";
    public static String FILE_WWM_MEDIUM = "src/main/resources/WWMMedium.csv";
    public static String FILE_WWM_VERY_HARD = "src/main/resources/WWMVeryHard.csv";
    public static String FILE_FQ = "src/main/resources/FQ.csv";

    public static int SERVER_PORT = 2400;

    public static void main(String[] args) {
        Mainmenu menu = new Mainmenu(new Scanner(System.in));
        menu.startGame();
    }
}
