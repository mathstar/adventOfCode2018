package com.staricka.aoc2018.day20;

import java.util.Optional;

public class Room {
    private Optional<Room> north;
    private Optional<Room> south;
    private Optional<Room> east;
    private Optional<Room> west;

    private final int distanceToOrigin;
    private final int x;
    private final int y;

    public Room(final int distanceToOrigin, final int x, final int y) {
        this.distanceToOrigin = distanceToOrigin;
        this.x = x;
        this.y = y;

        north = Optional.empty();
        south = Optional.empty();
        east = Optional.empty();
        west = Optional.empty();
    }

    public Optional<Room> getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = Optional.of(north);
    }

    public Optional<Room> getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = Optional.of(south);
    }

    public Optional<Room> getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = Optional.of(east);
    }

    public Optional<Room> getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = Optional.of(west);
    }

    public int getDistanceToOrigin() {
        return distanceToOrigin;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
