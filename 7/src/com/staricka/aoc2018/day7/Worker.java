package com.staricka.aoc2018.day7;

import java.util.Optional;

public class Worker {
    private static int nextId = 0;

    int id;
    Step currentStep;
    int doneAt;

    public Worker() {
        id = nextId++;
    }

    public void assignJob(Step step, int time) {
        System.out.println("Worker " + id + " assigned " + step.id + " at " + time);
        currentStep = step;
        doneAt = time + step.timeToComplete();
    }

    public boolean isWaiting() {
        return currentStep == null;
    }

    public Optional<Step> isFinished(int time) {
        if(doneAt == time) {
            Optional<Step> result = Optional.of(currentStep);
            currentStep = null;
            return result;
        }
        return Optional.empty();
    }
}
