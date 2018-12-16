package com.staricka.aoc2018.day12;

import java.util.Objects;

public class State {
    boolean left2;
    boolean left1;
    boolean center;
    boolean riqht1;
    boolean right2;

    public State(boolean left2, boolean left1, boolean center, boolean riqht1, boolean right2) {
        this.left2 = left2;
        this.left1 = left1;
        this.center = center;
        this.riqht1 = riqht1;
        this.right2 = right2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state = (State) o;
        return left2 == state.left2 && left1 == state.left1 && center == state.center && riqht1 == state.riqht1
                && right2 == state.right2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left2, left1, center, riqht1, right2);
    }
}
