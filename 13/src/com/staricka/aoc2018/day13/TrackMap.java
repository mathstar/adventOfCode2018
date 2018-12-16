package com.staricka.aoc2018.day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TrackMap {
    TrackSegment[][] tracks;
    List<Minecart> minecarts;

    public TrackMap(Collection<TrackSegment> tracks, Collection<Minecart> minecarts) {
        int maxX = tracks.stream().mapToInt(t -> t.x).max().getAsInt();
        int maxY = tracks.stream().mapToInt(t -> t.y).max().getAsInt();

        this.tracks = new TrackSegment[maxX + 1][maxY + 1];
        tracks.forEach(trackSegment -> this.tracks[trackSegment.x][trackSegment.y] = trackSegment);

        this.minecarts = new ArrayList<>(minecarts);
    }

    public String processTicksUntilCollistion() {
        Optional<String> collision = Optional.empty();
        int tick = 0;
        while (!collision.isPresent()) {
            System.out.println("Processing tick " + ++tick);
            collision = processTick();
        }
        return collision.get();
    }

    public String processTicksWithRemovals() {
        int tick = 0;
        while (minecarts.size() > 1) {
            System.out.println("Processing tick " + ++tick);
            processTickWithRemoval();
        }

        //        System.out.println("Processing tick " + ++tick);
        //        processTick();
        return minecarts.iterator().next().getCoords();
    }

    private Optional<String> processTick() {
        minecarts.sort(Comparator.comparingInt(m -> ((Minecart) m).y).thenComparing(m -> ((Minecart) m).x));
        for (Minecart minecart : minecarts) {
            minecart.processTick(tracks[minecart.x][minecart.y]);
            Optional<String> collision = detectCollision();
            if (collision.isPresent()) {
                return collision;
            }
        }
        return Optional.empty();
    }

    private void processTickWithRemoval() {
        minecarts.sort(Comparator.comparingInt(m -> ((Minecart) m).y).thenComparing(m -> ((Minecart) m).x));

        Set<Minecart> collided = new HashSet<>();
        for (Minecart minecart : minecarts) {
            if(!collided.contains(minecart)) {
                minecart.processTick(tracks[minecart.x][minecart.y]);
                Optional<String> collision = detectCollision(collided);
                if (collision.isPresent()) {
                    int x = getXFromCoord(collision.get());
                    int y = getYFromCoord(collision.get());

                    collided.addAll(minecarts.stream().filter(m -> m.x == x && m.y == y).collect(Collectors.toSet()));
                }
            }
        }
        minecarts.removeAll(collided);
    }

    private Optional<String> detectCollision() {
        return minecarts.stream().collect(Collectors.groupingBy(Minecart::getCoords)).entrySet().stream()
                .filter(e -> e.getValue().size() > 1).map(Entry::getKey).findAny();
    }

    private Optional<String> detectCollision(Set<Minecart> alreadyCollided) {
        return minecarts.stream().filter(m -> !alreadyCollided.contains(m))
                .collect(Collectors.groupingBy(Minecart::getCoords)).entrySet().stream()
                .filter(e -> e.getValue().size() > 1).map(Entry::getKey).findAny();
    }

    private int getXFromCoord(String coord) {
        return Integer.parseInt(coord.split(",")[0]);
    }

    private int getYFromCoord(String coord) {
        return Integer.parseInt(coord.split(",")[1]);
    }
}
