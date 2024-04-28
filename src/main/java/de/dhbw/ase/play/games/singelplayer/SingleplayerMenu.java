package de.dhbw.ase.play.games.singelplayer;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.repository.QuestionRepositoryFilebased;
import de.dhbw.ase.repository.StatsRepositoryFilebased;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.Map;

public class SingleplayerMenu extends Submenu {

    private final UserIn sc;

    public SingleplayerMenu(UserIn sc) {
        super(sc);
        this.sc = sc;
    }

    @Override
    protected Map<String, SelectedMenu> generateSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(
                new WerWirdMillionaer(sc,
                        StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_WWM),
                        QuestionRepositoryFilebased.getInstance(Quizzz.FILE_WWM_EASY),
                        QuestionRepositoryFilebased.getInstance(Quizzz.FILE_WWM_MEDIUM),
                        QuestionRepositoryFilebased.getInstance(Quizzz.FILE_WWM_HARD),
                        QuestionRepositoryFilebased.getInstance(Quizzz.FILE_WWM_VERY_HARD),
                        QuestionRepositoryFilebased.getInstance(Quizzz.FILE_WWM_EXPERT))));

        map.put("2", new SelectedMenu(new FindQuestionsQuiz(sc,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_FQ2),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_FQ))));

        map.put("3", new SelectedMenu(new SingleplayerCategoryMenu(sc)));
        map.put("4", new SelectedMenu(new SurvivalQuiz(sc,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_S),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_S))));
        map.put("5", new SelectedMenu(new StoryQuiz(sc,
                QuestionRepositoryFilebased.getInstance(Quizzz.FILE_STORY),
                StatsRepositoryFilebased.getInstance(Quizzz.FILE_STATS_STORY))));
        map.put("6", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("7", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Singleplayer-Modus möchtest du spielen?");
        System.out.println("1 - Wer wird Millionär?");
        System.out.println("2 - Fragenfinder");
        System.out.println("3 - Kategorien");
        System.out.println("4 - Survival");
        System.out.println("5 - Story");
        System.out.println("6 - Zurück");
        System.out.println("7 - Exit");
    }
}
