package com.staricka.aoc2018.day15;

import java.util.Comparator;

public abstract class Tile implements Comparable<Tile> {
    private static final Comparator<Tile> COMPARATOR = Comparator.comparingInt(Tile::getY).thenComparingInt(Tile::getX);

    private int x;
    private int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Tile o) {
        return COMPARATOR.compare(this, o);
    }

    public abstract char getRepresentation();
}
