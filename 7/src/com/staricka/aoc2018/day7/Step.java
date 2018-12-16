package com.staricka.aoc2018.day7;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Step implements Comparable<Step> {
    String id;
    Set<Step> dependentOn = new HashSet<>();

    public Step(String id) {
        this.id = id;
    }

    public void addDependency(Step step) {
        dependentOn.add(step);
    }

    public boolean isUnblocked(Collection<Step> completed) {
        Set<Step> stillNeeded = new HashSet<>(dependentOn);
        stillNeeded.removeAll(completed);
        return stillNeeded.isEmpty();
    }

    public int timeToComplete() {
        return Day7.BASE_TIME + (id.charAt(0) - 'A') + 1;
    }

    @Override
    public int compareTo(Step o) {
        return id.compareTo(o.id);
    }
}
