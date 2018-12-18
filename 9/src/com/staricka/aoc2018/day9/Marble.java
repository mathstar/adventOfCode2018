package com.staricka.aoc2018.day9;

import java.util.Objects;

public class Marble {
    private final int id;


    public Marble(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Marble marble = (Marble) o;
        return id == marble.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
