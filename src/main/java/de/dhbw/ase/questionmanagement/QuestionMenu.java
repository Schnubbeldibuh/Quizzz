package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.play.games.multiplayer.quickquiz.MultiplayerQuick;
import de.dhbw.ase.play.games.multiplayer.quiz.MultiplayerQuiz;
import de.dhbw.ase.play.games.singelplayer.FindQuestionsQuiz;
import de.dhbw.ase.play.games.singelplayer.WerWirdMillionaer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QuestionMenu extends Submenu {

    public QuestionMenu(Scanner sc) {
        super(sc);
    }

    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new WWMQuestionMenu(getSc())));
        map.put("2", new SelectedMenu(new QuestionManager(getSc(), Quizzz.FILE_MP)));
        map.put("3", new SelectedMenu(new QuestionManager(getSc(), Quizzz.FILE_FQ2)));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("5", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Fragenkatalog möchtest du bearbeiten?");
        System.out.println("1 - Wer wird Milionär?");
        System.out.println("2 - Multiplayer");
        System.out.println("3 - Fragenfinder");
        System.out.println("4 - Zurück");
        System.out.println("5 - Exit");
    }
}
