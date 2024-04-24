package de.dhbw.ase.play.games.repository;

import java.util.List;
import java.util.function.Function;

public interface StatsRepository {

    <T> List<T> readStats(Function<String, T> lineMapper) throws CouldNotAccessFileException;

    void writeStats(List<?> lines) throws CouldNotAccessFileException;
}
