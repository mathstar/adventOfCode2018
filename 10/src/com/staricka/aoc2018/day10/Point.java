package com.staricka.aoc2018.day10;

public class Point {
    int x;
    int y;

    int velocityX;
    int velocityY;

    public Point(int x, int y, int velocityX, int velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void step() {
        x += velocityX;
        y += velocityY;
    }

    public int distance(Point point) {
        return Math.abs(point.x - x) + Math.abs(point.y - y);
    }
}
