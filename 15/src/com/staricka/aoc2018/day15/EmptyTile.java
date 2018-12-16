package com.staricka.aoc2018.day15;

public class EmptyTile extends Tile {
    static final char REPRESENTATION = '.';

    public EmptyTile(int x, int y) {
        super(x, y);
    }

    @Override
    public char getRepresentation() {
        return REPRESENTATION;
    }
}
