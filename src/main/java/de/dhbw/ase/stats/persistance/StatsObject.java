package de.dhbw.ase.stats.persistance;

import java.util.Objects;

public abstract class StatsObject {

    private final String username;

    protected StatsObject(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsObject that = (StatsObject) o;

        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}
