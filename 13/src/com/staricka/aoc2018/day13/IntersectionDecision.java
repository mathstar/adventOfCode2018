package com.staricka.aoc2018.day13;

public enum IntersectionDecision {
    TURN_LEFT, STRAIGHT, TURN_RIGHT;

    public CartHeading processTurn(CartHeading current) {
        switch (this) {
            case STRAIGHT:
                return current;
            case TURN_LEFT:
                switch (current) {
                    case LEFT:
                        return CartHeading.DOWN;
                    case RIGHT:
                        return CartHeading.UP;
                    case UP:
                        return CartHeading.LEFT;
                    case DOWN:
                        return CartHeading.RIGHT;
                }
            case TURN_RIGHT:
                switch (current) {
                    case LEFT:
                        return CartHeading.UP;
                    case RIGHT:
                        return CartHeading.DOWN;
                    case UP:
                        return CartHeading.RIGHT;
                    case DOWN:
                        return CartHeading.LEFT;
                }
        }
        throw new IllegalStateException();
    }

    public IntersectionDecision getNext() {
        int ordinal = ordinal();
        ordinal++;
        if(ordinal >= values().length) {
            ordinal = 0;
        }
        return values()[ordinal];
    }
}
