package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Submenu;
import de.dhbw.ase.play.games.repository.CouldNotAccessFileException;
import de.dhbw.ase.play.games.repository.QuestionRepository;
import de.dhbw.ase.user.in.UserIn;

import java.util.HashMap;
import java.util.Map;

public class QuestionManagerEditingMenu extends Submenu {

    private final QuestionRepository questionRepository;
    private final UserIn sc;
    private final SelectedLine selectedLine = new SelectedLine();
    public QuestionManagerEditingMenu(UserIn sc, QuestionRepository questionRepository) {
        super(sc);
        this.sc = sc;
        this.questionRepository = questionRepository;
    }

    @Override
    protected Map<String, SelectedMenu> generateSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new QuestionManagerEdit(sc, questionRepository, selectedLine)));
        map.put("2", new SelectedMenu(new QuestionManagerDelete(questionRepository, selectedLine)));
        map.put("3", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Möchtest du diese Frage bearbeiten oder löschen?");
        System.out.println("1 - Bearbeiten");
        System.out.println("2 - Löschen");
        System.out.println("3 - Zurück");
        System.out.println("4 - Exit");
    }
    @Override
    public SelectedMenu.MenuSelection start() {
        String s;
        do {
            System.out.println("Welche dieser Fragen möchtest du bearbeiten? Bitte gib die Fragennummer an.");
            s = sc.waitForNextLine(this);
        } while (!validateInput(s));
        selectedLine.setSelectedLine(s);

        return super.startOnlyOnes();
    }

    private boolean validateInput(String input) {
        int questionNumber;

        try {
            questionNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        try {
            if ((questionNumber >= 0) && (questionNumber < questionRepository.readCompleteFileAsString().size())) {
                return true;
            }
        } catch (CouldNotAccessFileException ignored) {
        }

        return false;
    }

    public static class SelectedLine {
        private int selectedLine;

        public int getSelectedLine() {
            return selectedLine;
        }

        private void setSelectedLine(String selectedLine) {
            this.selectedLine = Integer.parseInt(selectedLine);
        }
    }
}
