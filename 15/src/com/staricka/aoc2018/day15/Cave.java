package com.staricka.aoc2018.day15;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Cave {
    private Tile[][] tiles;
    private List<Unit> units;

    public Cave(int width, int height, Collection<Tile> tiles) {
        this.tiles = new Tile[width][height];

        units = tiles.stream().filter(tile -> tile instanceof Unit).map(tile -> (Unit) tile)
                .collect(Collectors.toList());

        tiles.forEach(tile -> this.tiles[tile.getX()][tile.getY()] = tile);
    }

    public void executeTurnsUntilFinished(Consumer<Integer> finishedCallback, boolean printSteps) {
        AtomicBoolean finished = new AtomicBoolean(false);
        AtomicInteger turns = new AtomicInteger(0);
        while (!finished.get()) {
            turns.incrementAndGet();
            executeTurn(() -> {
                if (!finished.get()) {
                    finished.set(true);
                    finishedCallback.accept(turns.get());
                }
            });
            if (printSteps) {
                System.out.println("Turn: " + turns);
                System.out.println(StringTranslator.translateCave(this));
            }
        }
    }

    public void executeTurn(Runnable finishedCallback) {
        units.sort(Tile::compareTo);
        units.forEach(unit -> unit.executeTurn(this, finishedCallback));
    }

    public List<Unit> getUnits() {
        return units;
    }

    public List<Unit> getUnits(Collection<UnitType> unitTypes) {
        return units.stream().filter(Unit::isAlive).filter(unit -> unitTypes.contains(unit.getUnitType()))
                .collect(Collectors.toList());
    }

    public List<Tile> getOpenTilesAround(Tile tile) {
        return getTilesAround(tile).stream().filter(t -> t instanceof EmptyTile).collect(Collectors.toList());
    }

    public List<Tile> getTilesAround(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();

        List<Tile> possibleTiles = new ArrayList<>(4);
        if (x - 1 > 0) {
            possibleTiles.add(tiles[x - 1][y]);
        }
        if (x + 1 < tiles.length) {
            possibleTiles.add(tiles[x + 1][y]);
        }
        if (y - 1 > 0) {
            possibleTiles.add(tiles[x][y - 1]);
        }
        if (y + 1 < tiles[x].length) {
            possibleTiles.add(tiles[x][y + 1]);
        }
        return possibleTiles;
    }

    public List<Tile> findClosestTargets(Tile start, Set<Tile> targets) {
        // breadth-first search until target found
        List<Tile> possibleTargets = new ArrayList<>();

        Set<Tile> visited = new HashSet<>();
        visited.add(start);
        int foundLastRound;
        do {
            List<Tile> toAdd = new ArrayList<>(visited.size() * 4);
            foundLastRound = visited.size();
            for (Tile tile : visited) {
                List<Tile> reachable = getTilesAround(tile);
                for (Tile reachableTile : reachable) {
                    if (targets.contains(tile)) {
                        possibleTargets.add(tile);
                    }
                    if (reachableTile instanceof EmptyTile) {
                        toAdd.add(reachableTile);
                    }
                }
            }
            visited.addAll(toAdd);
        } while (possibleTargets.isEmpty() && foundLastRound < visited.size());

        return possibleTargets;
    }

    public Tile findBestStep(Tile start, Tile target) {
        // System.out.println(String.format("findBestStep: %d,%d -> %d,%d", start.getX(), start.getY(), target.getX(), target.getY()));

        // if adjacent, short-circuit logic
        if (Math.abs(start.getX() - target.getX()) + Math.abs(start.getY() - target.getY()) == 1) {
            return target;
        }

        List<Tile> possibleSteps = getOpenTilesAround(start);

        int shortestDistance = Integer.MAX_VALUE;
        List<Tile> shortestTiles = null;
        for (Tile tile : possibleSteps) {
            Optional<Integer> distance = findShortestDistance(tile, target);
            if (distance.isPresent()) {
                if (distance.get() < shortestDistance) {
                    shortestDistance = distance.get();
                    shortestTiles = new ArrayList<>();
                    shortestTiles.add(tile);
                } else if (distance.get() == shortestDistance) {
                    shortestTiles.add(tile);
                }
            }
        }

        if (shortestTiles == null || shortestTiles.isEmpty()) {
            throw new IllegalStateException(
                    String.format("When finding best step from %d,%d to %d,%d", start.getX(), start.getY(),
                            target.getX(), target.getY()));
        }

        return shortestTiles.stream().sorted().findFirst().get();
    }

    private Optional<Integer> findShortestDistance(Tile start, Tile target) {
        // System.out.println(String.format("findShortestDistance: %d,%d -> %d,%d", start.getX(), start.getY(), target.getX(), target.getY()));
        // breadth-first search until target found
        int steps = 0;
        Set<Tile> possibleLocations = new HashSet<>();
        Set<Tile> visited = new HashSet<>();
        visited.add(start);
        int foundLastRound;
        possibleLocations.add(start);
        do {
            foundLastRound = visited.size();
            Set<Tile> newPossibleLocations = new HashSet<>();
            steps++;

            for (Tile tile : possibleLocations) {
                List<Tile> reachable = getTilesAround(tile);
                for (Tile reachableTile : reachable) {
                    if (reachableTile.equals(target)) {
                        return Optional.of(steps);
                    }
                    if (reachableTile instanceof EmptyTile) {
                        visited.add(reachableTile);
                        newPossibleLocations.add(reachableTile);
                    }
                }
            }
            possibleLocations = newPossibleLocations;
        } while (foundLastRound < visited.size());
        return Optional.empty();
    }

    public void step(Unit unit, Tile step) {
        tiles[unit.getX()][unit.getY()] = new EmptyTile(unit.getX(), unit.getY());

        unit.setX(step.getX());
        unit.setY(step.getY());
        tiles[step.getX()][step.getY()] = unit;
    }

    public void kill(Unit unit) {
        tiles[unit.getX()][unit.getY()] = new EmptyTile(unit.getX(), unit.getY());
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
