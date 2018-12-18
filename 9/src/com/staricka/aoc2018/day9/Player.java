package com.staricka.aoc2018.day9;

public class Player {
    private final int id;
    private long score;

    public Player(int id) {
        this.id = id;
        score = 0;
    }

    public void winMarble(Marble marble) {
        score = Math.addExact(score, (long)marble.getId());
    }

    public int getId() {
        return id;
    }

    public long getScore() {
        return score;
    }
}
