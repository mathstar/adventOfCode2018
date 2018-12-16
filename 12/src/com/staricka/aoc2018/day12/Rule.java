package com.staricka.aoc2018.day12;

public class Rule {
    State state;
    boolean yields;

    public Rule(boolean left2, boolean left1, boolean center, boolean riqht1, boolean right2, boolean yields) {
        state = new State(left2, left1, center, riqht1, right2);
        this.yields = yields;
    }
}
