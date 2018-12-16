package com.staricka.aoc2018.day6;

import java.util.Collection;

public class Point extends Tile{
    private static int nextId = 0;

    int id;

    public Point(int x, int y) {
        super(x, y);
        id = nextId++;
    }

    public int getTotalDistanceFromPoints(Collection<Point> points) {
        return points.stream().mapToInt(p -> Math.abs(p.x - x) + Math.abs(p.y - y)).sum();
    }
}
