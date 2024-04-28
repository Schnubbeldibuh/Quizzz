package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.repository.QuestionRepositoryFilebased;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.Map;

public class QuestionMenu extends Submenu {
    private final UserIn sc;

    public QuestionMenu(UserIn sc) {
        super(sc);
        this.sc = sc;
    }

    @Override
    protected Map<String, SelectedMenu> generateSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_MP))));
        map.put("2", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_MP))));
        map.put("3", new SelectedMenu(new WWMQuestionMenu(sc)));
        map.put("4", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_GEOGRAPHY))));
        map.put("5", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_CINEMA_TV))));
        map.put("6", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_GENERAL))));
        map.put("7", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_TECHNOLOGY))));
        map.put("8", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_SPORTS))));
        map.put("9", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_SP_FOOD))));
        map.put("10", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_FQ2))));
        map.put("11", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_S))));
        map.put("12", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_STORY))));
        map.put("13", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("14", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Welchen Fragenkatalog möchtest du bearbeiten?");
        System.out.println("1 - Multiplayer");
        System.out.println("2 - Multiplayer - Schnellmodus");
        System.out.println("3 - Wer wird Milionär?");
        System.out.println("4 - Kategorie Geographie");
        System.out.println("5 - Kategorie Film und Fernsehen");
        System.out.println("6 - Kategorie Allgemeinwissen");
        System.out.println("7 - Kategorie Technologie und Internet");
        System.out.println("8 - Kategorie Sport");
        System.out.println("9 - Kategorie Essen und Trinken");
        System.out.println("10 - Fragenfinder");
        System.out.println("11 - Survival");
        System.out.println("12 - Story");
        System.out.println("13 - Zurück");
        System.out.println("14 - Exit");
    }
}
