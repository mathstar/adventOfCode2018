package com.staricka.aoc2018.day6;

import java.util.List;

public class ConflictNonPoint extends Tile {
    List<Point> closestPoint;

    public ConflictNonPoint(int x, int y, List<Point> closestPoint) {
        super(x, y);
        this.closestPoint = closestPoint;
    }
}
