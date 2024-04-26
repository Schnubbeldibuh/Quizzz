package de.dhbw.ase.repository;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class StatsRepositoryFilebased implements StatsRepository {

    private static final Map<String, StatsRepositoryFilebased> INSTANCES = new HashMap<>();

    public static StatsRepositoryFilebased getInstance(String filePath) {
        if (INSTANCES.containsKey(filePath)) {
            return INSTANCES.get(filePath);
        }
        StatsRepositoryFilebased repositoryFilebased = new StatsRepositoryFilebased(filePath);
        INSTANCES.put(filePath, repositoryFilebased);
        return repositoryFilebased;
    }


    private final String filePath;

    private StatsRepositoryFilebased(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public <T> List<T> readStats(Function<String, T> lineMapper) throws CouldNotAccessFileException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(filePath))) {
            return bufferedReader
                    .lines()
                    .filter(this::checkIfLineIsEmpty)
                    .map(lineMapper)
                    .toList();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new CouldNotAccessFileException();
        }
    }

    private boolean checkIfLineIsEmpty(String l) {
        return !l.isEmpty();
    }


    @Override
    public void writeStats(List<?> lines) throws CouldNotAccessFileException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Object o : lines) {
                bufferedWriter.write(o.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new CouldNotAccessFileException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatsRepositoryFilebased that = (StatsRepositoryFilebased) o;
        return filePath.equals(that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }
}
