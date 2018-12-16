package com.staricka.aoc2018.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Circle {
    private final List<Marble> marbles;
    private Marble currentMarble;

    public Circle(int capacity) {
        marbles = new ArrayList<>(capacity);
    }

    public void addMarble(Marble marble, RelativePosition relativePosition) {
        if(currentMarble == null) {
            marbles.add(marble);
            return;
        }

        int desiredIndex = marbles.indexOf(currentMarble);

        Direction direction = relativePosition.getDirection();
        switch (direction) {
            case CLOCKWISE:
                desiredIndex += relativePosition.getDistance();
                break;
            case COUNTER_CLOCKWISE:
                desiredIndex -= relativePosition.getDistance();
                desiredIndex += 1;
        }

        desiredIndex = rollIndex(desiredIndex);
        marbles.add(desiredIndex, marble);
    }

    public void removeMarble(Marble marble) {
        marbles.remove(marble);
    }

    public void setCurrentMarble(Marble marble) {
        if (!marbles.contains(marble)) {
            throw new IllegalStateException("Marble not in circle");
        }
        currentMarble = marble;
    }

    public Marble getMarble(RelativePosition relativePosition) {
        int desiredIndex = marbles.indexOf(currentMarble);

        Direction direction = relativePosition.getDirection();
        switch (direction) {
            case CLOCKWISE:
                desiredIndex += relativePosition.getDistance();
                break;
            case COUNTER_CLOCKWISE:
                desiredIndex -= relativePosition.getDistance();
        }

        desiredIndex = rollIndex(desiredIndex);
        return marbles.get(desiredIndex);
    }

    private int rollIndex(int index) {
        while (index >= marbles.size()) {
            index -= marbles.size();
        }
        while (index < 0) {
            index += marbles.size();
        }
        return index;
    }

    @Override
    public String toString() {

        return marbles.stream()
                .map(marble -> marble == currentMarble ? "(" + marble.getId() + ")" : String.valueOf(marble.getId()))
                .collect(Collectors.joining(" "));
    }
}
