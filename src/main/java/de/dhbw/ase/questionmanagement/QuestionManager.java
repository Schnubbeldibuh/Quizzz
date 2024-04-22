package de.dhbw.ase.questionmanagement;

import de.dhbw.ase.Quizzz;
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

public class QuestionManager extends Submenu {
    private String filePath;

    public QuestionManager(Scanner sc, String file) {
        super(sc);
        this.filePath = file;
    }

    @Override
    protected Map<String, SelectedMenu> createSelectionMap() {
        Map<String, SelectedMenu> map = new HashMap<>();
        map.put("1", new SelectedMenu(new QuestionManagerAdd(getSc(),this)));
        map.put("2", new SelectedMenu(new QuestionManagerEditingMenu(getSc(), this)));
        map.put("3", new SelectedMenu(SelectedMenu.MenuSelection.BACK));
        map.put("4", new SelectedMenu(SelectedMenu.MenuSelection.EXIT));

        return map;
    }

    @Override
    protected void showOptions() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.lines()
                    .map(this::convertString)
                    .forEach(System.out::println);
        } catch (FileNotFoundException e) {
            //TODO
        } catch (IOException e) {
            //TODO
        }
        System.out.println();
        System.out.println("Möchtest du eine Frage hinzufügen oder eine bestehende Frage bearbeiten?");
        System.out.println("1 - Hinzufügen");
        System.out.println("2 - Bearbeiten/ Löschen");
        System.out.println("3 - Zurück");
        System.out.println("4 - Exit");
    }

    private String convertString(String input) {
        return input.replaceAll(";", " - ");
    }

    public String getFilePath() {
        return filePath;
    }
}
