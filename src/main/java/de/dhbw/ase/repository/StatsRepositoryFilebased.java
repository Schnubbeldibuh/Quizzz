package de.dhbw.ase.repository;

import de.dhbw.ase.Quizzz;

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

    private StatsRepositoryFilebased(String fileName) {
        this.filePath = Quizzz.STATS_DIR + fileName;
    }

    @Override
    public <T> List<T> readStats(Function<String, T> lineMapper) throws CouldNotAccessFileException {
        File f = new File(filePath);
        if (!f.exists()) {
            return new ArrayList<>();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(f))) {
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
        createDirIfNeeded();
        File f = new File(filePath);
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new CouldNotAccessFileException();
        }

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

    private void createDirIfNeeded() {
        File file = new File(Quizzz.STATS_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
