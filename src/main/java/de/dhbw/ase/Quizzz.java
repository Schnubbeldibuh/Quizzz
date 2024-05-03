package de.dhbw.ase;

import de.dhbw.ase.user.in.ConsoleIn;

public class Quizzz {

    public static String QUESTION_DIR = "questions/";
    public static String STATS_DIR = "stats/";
    public static String FILE_WWM_EASY = "WWMEasy.csv";
    public static String FILE_WWM_EXPERT = "WWMExpert.csv";
    public static String FILE_WWM_HARD = "WWMHard.csv";
    public static String FILE_WWM_MEDIUM = "WWMMedium.csv";
    public static String FILE_WWM_VERY_HARD = "WWMVeryHard.csv";

    public static String FILE_FQ2 = "FQ2.csv";
    public static String FILE_S = "S.csv";
    public static String FILE_STORY = "Story.csv";

    public static String FILE_MP = "MP.csv";

    public static String FILE_SP_GENERAL = "SPGeneral.csv";
    public static String FILE_SP_GEOGRAPHY = "SPGeography.csv";
    public static String FILE_SP_CINEMA_TV = "/SPCinemaTV.csv";
    public static String FILE_SP_TECHNOLOGY = "SPTechnology.csv";
    public static String FILE_SP_SPORTS = "SPSports.csv";
    public static String FILE_SP_FOOD = "SPFood.csv";

    public static String FILE_STATS_WWM = "wwmStats.csv";
    public static String FILE_STATS_FQ = "fqStats.csv";
    public static String FILE_STATS_S = "sStats.csv";
    public static String FILE_STATS_STORY = "storyStats.csv";

    public static String FILE_STATS_MP = "mpStats.csv";
    public static String FILE_STATS_MP_QUICK = "mpQuickStats.csv";

    public static String FILE_STATS_SP_GENERAL = "SPGeneralStats.csv";
    public static String FILE_STATS_SP_GEOGRAPHY = "SPGeographyStats.csv";
    public static String FILE_STATS_SP_CINEMA_TV = "SPCinemaTVStats.csv";
    public static String FILE_STATS_SP_TECHNOLOGY = "SPTechnologyStats.csv";
    public static String FILE_STATS_SP_SPORTS = "SPSportsStats.csv";
    public static String FILE_STATS_SP_FOOD = "SPFoodStats.csv";

    public static int SERVER_PORT = 2400;

    public static void main(String[] args) {
        Mainmenu menu = new Mainmenu(new ConsoleIn(System.in));
        menu.startGame();
    }
}
