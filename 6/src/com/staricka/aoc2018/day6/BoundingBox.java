package com.staricka.aoc2018.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BoundingBox {
    int leftX;
    int rightX;
    int topY;
    int bottomY;

    NavigableMap<Integer, NavigableMap<Integer, Tile>> tiles;

    public BoundingBox(Collection<Point> points) {
        // determine bounds
        leftX = Integer.MAX_VALUE;
        rightX = Integer.MIN_VALUE;
        topY = Integer.MAX_VALUE;
        bottomY = Integer.MIN_VALUE;

        for (Point point : points) {
            if (point.x < leftX) {
                leftX = point.x;
            }
            if (point.x > rightX) {
                rightX = point.x;
            }
            if (point.y < topY) {
                topY = point.y;
            }
            if (point.y > bottomY) {
                bottomY = point.y;
            }
        }

        // create tile maps
        tiles = new TreeMap<>();
        for (int i = leftX; i <= rightX; i++) {
            tiles.put(i, new TreeMap<>());
        }

        // populate points
        for (Point point : points) {
            tiles.get(point.x).put(point.y, point);
        }

        // populate non points
        for (int i = leftX; i <= rightX; i++) {
            for (int j = topY; j <= bottomY; j++) {
                final int x = i;
                final int y = j;
                tiles.get(i).computeIfAbsent(j, unneeded -> new NonPoint(x, y, points));
            }
        }
    }

    public boolean isBoundingPoint(Point point) {
        return point.x == leftX || point.x == rightX || point.y == topY || point.y == bottomY;
    }

    public PointResult getLargestFiniteArea() {
        Map<Point, Integer> pointAreas = new HashMap<>();
        tiles.values().stream().flatMap(m -> m.values().stream()).forEach(tile -> {
            if (tile instanceof NonPoint) {
                NonPoint nonPoint = (NonPoint) tile;
                Optional<Point> nearestPoint = nonPoint.getNearestPoint();
                if (nearestPoint.isPresent() && !isBoundingPoint(nearestPoint.get())) {
                    pointAreas.computeIfPresent(nearestPoint.get(), (p, i) -> i + 1);
                    pointAreas.putIfAbsent(nearestPoint.get(), 1);
                }
            } else if (tile instanceof Point) {
                Point point = (Point) tile;
                pointAreas.computeIfPresent(point, (p, i) -> i + 1);
                pointAreas.putIfAbsent(point, 1);
            }
        });

        TreeMap<Integer, Stream<Point>> pointsByArea = pointAreas.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getValue(), e -> Stream.of(e.getKey()), Stream::concat, TreeMap::new));

        Entry<Integer, Stream<Point>> result = pointsByArea.lastEntry();
        return new PointResult(result.getValue().findAny().get(), result.getKey());
    }

    public long getAreaWithinRadius(int radius, Collection<Point> points) {
        return tiles.values().stream().flatMap(m -> m.values().stream())
                .filter(tile -> (tile instanceof Point && ((Point) tile).getTotalDistanceFromPoints(points) < radius)
                        || (tile instanceof NonPoint && ((NonPoint) tile).getTotalDistanceFromPoints() < radius))
                .count();
    }

    class PointResult {
        Point point;
        Integer area;

        public PointResult(Point point, Integer area) {
            this.point = point;
            this.area = area;
        }
    }

}
