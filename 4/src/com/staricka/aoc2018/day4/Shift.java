package com.staricka.aoc2018.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shift {
    String date;
    Boolean[] awake = new Boolean[60];

    public Shift(String date) {
        this.date = date;
        Arrays.fill(awake, true);
    }

    public void sleep(int startTime, int stopTime) {
        for(int i = startTime; i < stopTime && i < awake.length; i++) {
            awake[i] = false;
        }
    }

    public int timeAsleep() {
        return (int) Arrays.stream(awake).filter(awake -> !awake).count();
    }

    public List<Integer> minutesSlept() {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < awake.length; i++) {
            if(!awake[i]){
                result.add(i);
            }
        }
        return result;
    }
}
