package com.staricka.aoc2018.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Circle {
    private List<Marble> marbles;
    private Marble currentMarble;

    public Circle(int capacity) {
        marbles = new ArrayList<>(capacity);
    }

    public void addMarble(Marble marble, RelativePosition relativePosition) {
        if(currentMarble == null) {
            marbles.add(marble);
            return;
        }

        marbles.add(getRelativeIndex(relativePosition), marble);
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
        return marbles.get(getRelativeIndex(relativePosition));
    }

    private int getRelativeIndex(RelativePosition relativePosition) {
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
        return desiredIndex;
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

    private void garbageCollect() {
        if(marbles.size() >= 4000) {
            int newStart = rollIndex(marbles.indexOf(currentMarble) - 1000);

            List<Marble> newMarbles = new ArrayList<>(5000);
            int count = 0;
            for(int i = newStart; count <= 2000; i++) {
                count++;
                newMarbles.add(marbles.get(rollIndex(i)));
            }

            marbles = newMarbles;
        }
    }

    @Override
    public String toString() {
        int zeroIndex = marbles.indexOf(new Marble(0));

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < marbles.size(); i++) {
            Marble marble = marbles.get(rollIndex(zeroIndex + i));
            if(marble.equals(currentMarble)) {
                stringBuilder.append(String.format("(%d)", marble.getId()));
            } else {
                stringBuilder.append(marble.getId());
            }
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();

//        return marbles.stream()
//                .map(marble -> marble == currentMarble ? "(" + marble.getId() + ")" : String.valueOf(marble.getId()))
//                .collect(Collectors.joining(" "));
    }
}
