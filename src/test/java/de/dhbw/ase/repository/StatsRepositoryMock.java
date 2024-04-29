package de.dhbw.ase.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StatsRepositoryMock implements StatsRepository {

    public List stats = new ArrayList<>();
    public List savedStats = new ArrayList<>();

    @Override
    public <T> List<T> readStats(Function<String, T> lineMapper) throws CouldNotAccessFileException {
        return stats;
    }

    @Override
    public void writeStats(List<?> lines) throws CouldNotAccessFileException {
        savedStats = lines;
    }
}