package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.Submenu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QuestionManagerEditingMenu extends Submenu {
    private QuestionManager questionManager;
    private Scanner sc;
    private int numberOfLines;
    private String selectedLine;
    public QuestionManagerEditingMenu(Scanner sc, QuestionManager questionManager) {
        super(sc);
        this.sc = sc;
        this.questionManager = questionManager;
    }

    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new QuestionManagerEdit(getSc(),this)));
        map.put("2", new SelectedMenu(new QuestionManagerDelete(getSc(), this)));
        map.put("3", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        System.out.println("Möchtest du eine Frage bearbeiten oder löschen?");
        System.out.println("1 - Bearbeiten");
        System.out.println("2 - Löschen");
        System.out.println("3 - Zurück");
        System.out.println("4 - Exit");
    }
    @Override
    public SelectedMenu.MenuSelection start() {
        getNumberofLines();

        do {
            System.out.println("Welche dieser Fragen möchtest du bearbeiten? Bitte gib die Fragennummer an.");
            selectedLine = sc.next();
        } while (!validateInput(selectedLine));

        return super.start();
    }

    private boolean validateInput(String input) {
        int questionNumber;

        try {
            questionNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        if ((questionNumber >= 0) && (questionNumber <= numberOfLines)) {
            return true;
        }
        return false;
    }

    private void getNumberofLines() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(questionManager.getFilePath()))) {
            String firstLine = bufferedReader.readLine();
            numberOfLines =  Integer.parseInt(firstLine.substring(0, firstLine.indexOf(';')));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            //TODO
        }
    }

    String getFilePath() {
        return questionManager.getFilePath();
    }

    String getSelectedLine() {
        return selectedLine;
    }
}
