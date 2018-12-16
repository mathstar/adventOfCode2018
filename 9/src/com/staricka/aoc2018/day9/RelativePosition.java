package com.staricka.aoc2018.day9;

public class RelativePosition {
    private final Direction direction;
    private final int distance;

    public RelativePosition(Direction direction, int distance) {
        this.direction = direction;
        this.distance = distance;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }
}
