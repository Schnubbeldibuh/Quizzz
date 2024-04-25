package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.Quizzz;
import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.repository.QuestionRepositoryFilebased;
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
        map.put("1", new SelectedMenu(new WWMQuestionMenu(sc)));
        map.put("2", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_MP))));
        map.put("3", new SelectedMenu(
                new QuestionManager(sc, QuestionRepositoryFilebased.getInstance(Quizzz.FILE_FQ2))));
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
