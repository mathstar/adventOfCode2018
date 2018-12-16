package com.staricka.aoc2018.day4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Guard {
    String id;
    List<Shift> shifts = new ArrayList<>();

    public Guard(String id) {
        this.id = id;
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    public MinuteResult sleepiestMinute() {
        Map<Integer, Integer> timesSleptInMinute = new HashMap<>();

        for(Shift shift : shifts) {
            for(Integer minute : shift.minutesSlept()) {
                timesSleptInMinute.putIfAbsent(minute, 0);
                timesSleptInMinute.compute(minute, (m, times) -> times + 1);
            }
        }

        int sleepiestMinute = -1;
        int maxTimes = 0;
        for (Entry<Integer, Integer> entry : timesSleptInMinute.entrySet()) {
            if(entry.getValue() > maxTimes) {
                maxTimes = entry.getValue();
                sleepiestMinute = entry.getKey();
            }
        }
        return new MinuteResult(this, sleepiestMinute, maxTimes);
    }

    class MinuteResult {
        Guard guard;
        int minute;
        int frequency;

        public MinuteResult(Guard guard, int minute, int frequency) {
            this.guard = guard;
            this.minute = minute;
            this.frequency = frequency;
        }

        public Guard getGuard() {
            return guard;
        }

        public int getMinute() {
            return minute;
        }

        public int getFrequency() {
            return frequency;
        }
    }
}
