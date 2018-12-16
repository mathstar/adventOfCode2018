package com.staricka.aoc2018.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 {
    private static final String INPUT = "input.txt";
    static final int BASE_TIME = 60;
    private static final int WORKER_COUNT = 5;

    private static final Pattern STEP_PATTERN = Pattern.compile("step ([A-Z])");
    private static final Pattern DEPENDS_ON_PATTERN = Pattern.compile("Step ([A-Z])");

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT));

        NavigableMap<String, Step> steps = new TreeMap<>();
        bufferedReader.lines().forEach(line -> {
            Matcher stepMatcher = STEP_PATTERN.matcher(line);
            stepMatcher.find();
            Matcher dependsOnMatcher = DEPENDS_ON_PATTERN.matcher(line);
            dependsOnMatcher.find();

            String stepId = stepMatcher.group(1);
            String dependsOnId = dependsOnMatcher.group(1);

            Step step = steps.computeIfAbsent(stepId, Step::new);
            Step dependsOn = steps.computeIfAbsent(dependsOnId, Step::new);

            step.addDependency(dependsOn);
        });

        NavigableMap<String, Step> timelessSteps = new TreeMap<>(steps);
        List<Step> completedOrder = new ArrayList<>(timelessSteps.size());
        while (!timelessSteps.isEmpty()) {
            Iterator<Step> stepIterator = timelessSteps.values().iterator();
            while (stepIterator.hasNext()) {
                Step current = stepIterator.next();
                if (current.isUnblocked(completedOrder)) {
                    completedOrder.add(current);
                    stepIterator.remove();
                    break;
                }
            }
        }

        List<Step> timedStepsRemaining = new ArrayList<>(steps.values());
        List<Step> completedTimedOrder = new ArrayList<>(steps.size());
        int time = 0;
        List<Worker> workers =
                IntStream.range(0, WORKER_COUNT).mapToObj(i -> new Worker()).collect(Collectors.toList());
        workers.forEach(worker -> {
            Optional<Step> task = getTask(timedStepsRemaining, completedTimedOrder);
            if (task.isPresent()) {
                worker.assignJob(task.get(), 0);
            }
        });
        while (workers.stream().filter(worker -> !worker.isWaiting()).count() > 0 ||!timedStepsRemaining.isEmpty()) {
            time++;
            for (Worker worker : workers) {
                Optional<Step> finishedStep = worker.isFinished(time);
                if (finishedStep.isPresent()) {
                    completedTimedOrder.add(finishedStep.get());
                }

                if (worker.isWaiting()) {
                    Optional<Step> task = getTask(timedStepsRemaining, completedTimedOrder);
                    if (task.isPresent()) {
                        worker.assignJob(task.get(), time);
                    }
                }
            }
        }

        System.out.println("Desired order is: " + completedOrder.stream().map(s -> s.id).collect(Collectors.joining()));
        System.out.println(
                "Timed order is: " + completedTimedOrder.stream().map(s -> s.id).collect(Collectors.joining()));
        System.out.println("Time required is: " + time);
    }

    private static Optional<Step> getTask(Collection<Step> steps, Collection<Step> completed) {
        System.out.println(
                "Finding task from " + steps.stream().map(s -> s.id).collect(Collectors.joining()) + " with done "
                        + completed.stream().map(s -> s.id).collect(Collectors.joining()));
        Optional<Step> step = steps.stream().filter(s -> s.isUnblocked(completed)).findFirst();
        if (step.isPresent()) {
            steps.remove(step.get());
        }
        return step;
    }
}
