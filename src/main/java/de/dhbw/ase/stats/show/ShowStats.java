package de.dhbw.ase.stats.show;

import de.dhbw.ase.SelectedMenu;
import de.dhbw.ase.Startable;
import de.dhbw.ase.repository.CouldNotAccessFileException;
import de.dhbw.ase.repository.StatsRepository;
import de.dhbw.ase.stats.persistance.StatsObject;

public abstract class ShowStats<T extends StatsObject> implements Startable {

    private final StatsRepository statsRepository;
    public ShowStats(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    protected abstract T mapLine(String line);
    protected abstract void displayStats(T state);
    protected abstract void displayFittingHeader();

    @Override
    public SelectedMenu.MenuSelection start() {

        System.out.printf("---------------------------------------------------------%n");
        System.out.printf("                          STATS                          %n");
        displayFittingHeader();

        try {
            statsRepository
                    .readStats(this::mapLine)
                    .forEach(this::displayStats);
        } catch (CouldNotAccessFileException e) {
            System.out.println("Beim lesen der Stats ist ein Fehler aufgetreten");
            return SelectedMenu.MenuSelection.BACK;
        }

        return SelectedMenu.MenuSelection.BACK;
    }
}
