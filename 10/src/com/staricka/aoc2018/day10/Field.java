package com.staricka.aoc2018.day10;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Field {
    private static final int FUZZ = 4;

    Point[] points;

    public Field(Collection<Point> points) {
        this.points = points.toArray(new Point[points.size()]);
    }

    public void step() {
        for(Point point : points) {
            point.step();
        }
    }

    public boolean isPossibleImage() {
        Set<Point> pointsToCheck = new HashSet<>(Arrays.asList(points));
        while (!pointsToCheck.isEmpty()) {
            Point point = pointsToCheck.iterator().next();
            boolean foundPoint = false;
            for (Point point2 : points) {
                if (point2 != point && point.distance(point2) < FUZZ) {
                    pointsToCheck.remove(point);
                    pointsToCheck.remove(point2);
                    foundPoint = true;
                    break;
                }
            }

            if(!foundPoint) {
                return false;
            }
        }
        return true;
    }

    public String drawField() {
        int minX = Arrays.stream(points).mapToInt(p -> p.x).min().getAsInt() - 1;
        int maxX = Arrays.stream(points).mapToInt(p -> p.x).max().getAsInt() + 1;
        int minY = Arrays.stream(points).mapToInt(p -> p.y).min().getAsInt() - 1;
        int maxY = Arrays.stream(points).mapToInt(p -> p.y).max().getAsInt() + 1;

        Map<Integer, Map<Integer, Point>> pointsByCoords = new HashMap<>();
        for (Point point : points) {
            Map<Integer, Point> row = pointsByCoords.computeIfAbsent(point.x, p -> new HashMap<>());
            row.put(point.y, point);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int y = minY; y <= maxY; y++) {
            for(int x = minX; x <= maxX; x++) {
                Map<Integer, Point> row = pointsByCoords.get(x);
                if (pointsByCoords.containsKey(x) && pointsByCoords.get(x).containsKey(y)) {
                    stringBuilder.append("#");
                } else {
                    stringBuilder.append(".");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
