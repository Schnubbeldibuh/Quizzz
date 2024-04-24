package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.repository.QuestionRepositoryFilebased;
import de.dhbw.ase.play.games.repository.StatsRepositoryFilebased;
import de.dhbw.ase.user.in.UserIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleplayerCategoryMenu extends Submenu {
    public SingleplayerCategoryMenu(UserIn sc) {
        super(sc);
    }

    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {

        Map<String, SelectedMenu> map = new HashMap<>();

        SPQuiz generalQuiz = new SPQuiz(getSc(), Categories.GENERAL,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_GENERAL),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_GENERAL));

        SPQuiz geographyQuiz = new SPQuiz(getSc(), Categories.GEOGRAPHY,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_GEOGRAPHY),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_GEOGRAPHY));

        SPQuiz cinemaTVQuiz = new SPQuiz(getSc(), Categories.CINEMA_TV,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_CINEMA_TV),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_CINEMA_TV));

        SPQuiz technologyInternetQuiz = new SPQuiz(getSc(), Categories.TECHNOLOGY_INTERNET,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_TECHNOLOGY),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_TECHNOLOGY));

        SPQuiz sportsQuiz = new SPQuiz(getSc(), Categories.SPORTS,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_SPORTS),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_SPORTS));

        SPQuiz foodQuiz = new SPQuiz(getSc(), Categories.FOOD,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_FOOD),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_SP_FOOD));

        List<SPQuiz> spGamesList = new ArrayList<>();
        spGamesList.add(geographyQuiz);
        spGamesList.add(generalQuiz);
        spGamesList.add(cinemaTVQuiz);
        spGamesList.add(technologyInternetQuiz);
        spGamesList.add(sportsQuiz);
        spGamesList.add(foodQuiz);

        map.put("1", new SelectedMenu(new SPRandom(getSc(), spGamesList)));
        map.put("2", new SelectedMenu(generalQuiz));
        map.put("3", new SelectedMenu(geographyQuiz));
        map.put("4", new SelectedMenu(cinemaTVQuiz));
        map.put("5", new SelectedMenu(technologyInternetQuiz));
        map.put("6", new SelectedMenu(sportsQuiz));
        map.put("7", new SelectedMenu(foodQuiz));
        map.put("8", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("9", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welche Kategorie möchtest du spielen?");
        System.out.println("1 - Zufall");
        System.out.println("2 - Allgemeinwissen");
        System.out.println("3 - Geographie");
        System.out.println("4 - Film und Fernsehen");
        System.out.println("5 - Technologie und Internet");
        System.out.println("6 - Sport");
        System.out.println("7 - Essen und Trinken");
        System.out.println("8 - Zurück");
        System.out.println("9 - Exit");
    }
}
