package com.staricka.aoc2018.day12;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlantRow {
    Map<Integer, Set<Integer>> plantsAtGeneration;
    Map<State, Rule> rules;

    public PlantRow(Collection<Integer> currentPlants, Collection<Rule> rules) {
        plantsAtGeneration = new HashMap<>();
        plantsAtGeneration.put(0, new HashSet<>(currentPlants));
        this.rules = rules.stream().collect(Collectors.toMap(r -> r.state, Function.identity()));
    }

    public Set<Integer> getPlantsAtGeneration(int generation) {
        evaluateGeneration(generation);
        return plantsAtGeneration.get(generation);
    }

    public Set<Long> getPlantsAtLargeGeneration(long generation) {
        Set<Long> plants = plantsAtGeneration.get(0).stream().map(Integer::longValue).collect(Collectors.toSet());
        for (long step = 1; step <= generation; step++) {
            plants = evaluateGeneration(plants, step, generation);
        }
        return plants;
    }

    private Set<Long> evaluateGeneration(Set<Long> currentPlants, long generation, long target) {
        long leftMostPlant = currentPlants.stream().reduce(Long.MAX_VALUE, Long::min);
        long rightMostPlant = currentPlants.stream().reduce(Long.MIN_VALUE, Long::max);

        Set<Long> nextGenPlants = new HashSet<>();
        for (long i = leftMostPlant - 2; i <= rightMostPlant + 2; i++) {
            boolean hasPlant = evaluatePlant(i, currentPlants);
            if (hasPlant) {
                nextGenPlants.add(i);
            }
        }

        checkForLoop(nextGenPlants, generation, target);
        return nextGenPlants;
    }

    Map<Set<Long>, ShiftedGeneration> arrangementToGenerationMap = new HashMap<>();

    private void checkForLoop(Set<Long> currentPlants, long generation, long target) {
        long leftMostPlant = currentPlants.stream().reduce(Long.MAX_VALUE, Long::min);
        Set<Long> shiftedPlants = currentPlants.stream().map(p -> p - leftMostPlant).collect(Collectors.toSet());

        ShiftedGeneration previousGeneration = arrangementToGenerationMap.get(shiftedPlants);
        if (previousGeneration != null) {
            System.out.println("Loop detected: " + previousGeneration.generation + " to " + generation);
            long shiftBetweenGenerations = leftMostPlant - previousGeneration.shift;
            System.out.println(currentPlants.stream().map(p -> p + shiftBetweenGenerations * (target - generation))
                    .reduce(0L, Long::sum));
            System.exit(0);
        } else {
            arrangementToGenerationMap.put(shiftedPlants, new ShiftedGeneration(generation, leftMostPlant));
        }
    }

    class ShiftedGeneration {
        long generation;
        long shift;

        public ShiftedGeneration(long generation, long shift) {
            this.generation = generation;
            this.shift = shift;
        }
    }

    private void evaluateGeneration(int generation) {
        if (plantsAtGeneration.containsKey(generation)) {
            return;
        }

        if (!plantsAtGeneration.containsKey(generation - 1)) {
            evaluateGeneration(generation - 1);
        }

        int leftMostPlant =
                plantsAtGeneration.get(generation - 1).stream().mapToInt(Integer::intValue).min().getAsInt();
        int rightMostPlant =
                plantsAtGeneration.get(generation - 1).stream().mapToInt(Integer::intValue).max().getAsInt();

        Set<Integer> nextGenPlants = new HashSet<>();
        for (int i = leftMostPlant - 2; i <= rightMostPlant + 2; i++) {
            boolean hasPlant = evaluatePlant(generation - 1, i);
            if (hasPlant) {
                nextGenPlants.add(i);
            }
        }
        plantsAtGeneration.put(generation, nextGenPlants);

        System.out.println(generation + ": " + plantsAtGeneration.get(generation));
    }

    private boolean evaluatePlant(int generation, int position) {
        boolean left2 = plantsAtGeneration.get(generation).contains(position - 2);
        boolean left1 = plantsAtGeneration.get(generation).contains(position - 1);
        boolean center = plantsAtGeneration.get(generation).contains(position);
        boolean right1 = plantsAtGeneration.get(generation).contains(position + 1);
        boolean right2 = plantsAtGeneration.get(generation).contains(position + 2);
        State state = new State(left2, left1, center, right1, right2);

        return rules.get(state).yields;
    }

    private boolean evaluatePlant(long position, Set<Long> currentPlants) {
        boolean left2 = currentPlants.contains(position - 2);
        boolean left1 = currentPlants.contains(position - 1);
        boolean center = currentPlants.contains(position);
        boolean right1 = currentPlants.contains(position + 1);
        boolean right2 = currentPlants.contains(position + 2);
        State state = new State(left2, left1, center, right1, right2);

        return rules.get(state).yields;
    }
}
