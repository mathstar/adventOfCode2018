package com.staricka.aoc2018.day6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NonPoint extends Tile {
    Map<Point, Integer> distanceToPoints;
    NavigableMap<Integer, List<Point>> pointsByDistance;

    public NonPoint(int x, int y, Collection<Point> points) {
        super(x, y);

        distanceToPoints = new HashMap<>();
        pointsByDistance = new TreeMap<>();
        for (Point point : points) {
            int distance = Math.abs(point.x - x) + Math.abs(point.y - y);
            distanceToPoints.put(point, distance);
            pointsByDistance.computeIfAbsent(distance, d -> new ArrayList<>());
            pointsByDistance.get(distance).add(point);
        }
    }

    public Optional<Point> getNearestPoint() {
        List<Point> nearestPoints = pointsByDistance.firstEntry().getValue();
        if(nearestPoints.size() == 1) {
            return Optional.of(nearestPoints.get(0));
        }
        return Optional.empty();
    }

    public int getTotalDistanceFromPoints() {
        return distanceToPoints.values().stream().mapToInt(Integer::intValue).sum();
    }
}
