package de.dhbw.ase;

import java.util.Scanner;

public class Quizzz {

    public static String FILE_WWM_EASY = "src/main/resources/WWMEasy.csv";
    public static String FILE_WWM_EXPERT = "src/main/resources/WWMExpert.csv";
    public static String FILE_WWM_HARD = "src/main/resources/WWMHard.csv";
    public static String FILE_WWM_MEDIUM = "src/main/resources/WWMMedium.csv";
    public static String FILE_WWM_VERY_HARD = "src/main/resources/WWMVeryHard.csv";

    public static String FILE_FQ2 = "src/main/resources/FQ2.csv";

    public static String FILE_MP = "src/main/resources/MP.csv";

    public static String FILE_SP_GENERAL = "src/main/resources/SPGeneral.csv";
    public static String FILE_SP_GEOGRAPHY = "src/main/resources/SPGeography.csv";
    public static String FILE_SP_CINEMA_TV = "src/main/resources/SPCinemaTV.csv";
    public static String FILE_SP_TECHNOLOGY = "src/main/resources/SPTechnology.csv";
    public static String FILE_SP_SPORTS = "src/main/resources/SPSports.csv";
    public static String FILE_SP_FOOD = "src/main/resources/SPFood.csv";

    public static String FILE_STATS_WWM = "src/main/resources/stats/wwmStats.csv";
    public static String FILE_STATS_FQ = "src/main/resources/stats/fqStats.csv";
    public static String FILE_STATS_MP = "src/main/resources/stats/mpStats.csv";
    public static String FILE_STATS_MP_QUICK = "src/main/resources/stats/mpQuickStats.csv";
    public static String FILE_STATS_SP_GENERAL = "src/main/resources/stats/SPGeneralStats.csv";
    public static String FILE_STATS_SP_GEOGRAPHY = "src/main/resources/stats/SPGeographyStats.csv";
    public static String FILE_STATS_SP_CINEMA_TV = "src/main/resources/stats/SPCinemaTVStats.csv";
    public static String FILE_STATS_SP_TECHNOLOGY = "src/main/resources/stats/SPTechnologyStats.csv";
    public static String FILE_STATS_SP_SPORTS = "src/main/resources/stats/SPSportsStats.csv";
    public static String FILE_STATS_SP_FOOD = "src/main/resources/stats/SPFoodStats.csv";



    public static int SERVER_PORT = 2400;

    public static void main(String[] args) {
        Mainmenu menu = new Mainmenu(new Scanner(System.in));
        menu.startGame();
    }
}
