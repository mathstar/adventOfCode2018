package com.staricka.aoc2018.day9;

public class Player {
    private static int nextId = 0;

    private final int id;
    private int score;

    public Player() {
        id = nextId++;
        score = 0;
    }

    public void winMarble(Marble marble) {
        score += marble.getId();
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }
}
