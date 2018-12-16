package com.staricka.aoc2018.day15;

public class Wall extends Tile {
    static final char REPRESENTATION = '#';

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public char getRepresentation() {
        return REPRESENTATION;
    }
}
