package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.QuestionRepository;
import de.dhbw.ase.repository.question.Question;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionManager extends Submenu {
    private final QuestionRepository questionRepository;
    private final UserIn sc;

    public QuestionManager(UserIn sc, QuestionRepository questionRepository) {
        super(sc);
        this.questionRepository = questionRepository;
        this.sc = sc;
    }

    @Override
    protected Map<String, SelectedMenu> generateSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new QuestionManagerAdd(sc, questionRepository)));
        map.put("2", new SelectedMenu(new QuestionManagerEditingMenu(sc, questionRepository)));
        map.put("3", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    public SelectedMenu.MenuSelection start() {
        SelectedMenu.MenuSelection menuSelection;
        do {
            try {
                List<Question> questions = questionRepository.readCompleteFile();

                for (int i = 0; i < questions.size(); i++) {
                    String string = convertToString(i, questions.get(i));
                    System.out.println(string);
                }

            } catch (CouldNotAccessFileException e) {
                System.out.println("Das System kann nicht auf die Fragen zugreifen");
                return SelectedMenu.MenuSelection.BACK;
            }
            menuSelection = startOnlyOnes();
        } while (menuSelection != SelectedMenu.MenuSelection.EXIT);

        return menuSelection;
    }

    @Override
    protected void showOptions() {

        System.out.println();
        System.out.println("Möchtest du eine Frage hinzufügen oder eine bestehende Frage bearbeiten?");
        System.out.println("1 - Hinzufügen");
        System.out.println("2 - Bearbeiten/ Löschen");
        System.out.println("3 - Zurück");
        System.out.println("4 - Exit");
    }

    private String convertToString(int i, Question question) {
        return i + " - " + question.toString();
    }
}
