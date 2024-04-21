package de.dhbw.ase.stats.show;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public abstract class ShowStats implements Startable {

    private String filename;
    public ShowStats(String filename) {
        this.filename = filename;
    }

    protected abstract void displayStats(String string);
    protected abstract void displayFittingHeader();

    @Override
    public SelectedMenu.MenuSelection start() {

        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("                        STATS MENU                       %n");
        displayFittingHeader();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))){
            bufferedReader.lines()
                    .forEach(this::displayStats);
        } catch (IOException e) {
            // TODO
            throw new RuntimeException();
        }

        return SelectedMenu.MenuSelection.BACK;
    }
}
