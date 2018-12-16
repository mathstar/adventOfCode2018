package com.staricka.aoc2018.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {
    private static final String INPUT = "input.txt";

    private static final Pattern INITIAL_PATTERN = Pattern.compile("initial state: ([#\\.]+)");
    private static final Pattern RULE_PATTERN = Pattern.compile("([#\\.])([#\\.])([#\\.])([#\\.])([#\\.]) => ([#\\.])");

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT));

        char[] plants = {};
        List<Rule> rules = new ArrayList<>();

        String line = bufferedReader.readLine();
        while (line != null) {
            Matcher initialMatcher = INITIAL_PATTERN.matcher(line);
            Matcher ruleMatcher = RULE_PATTERN.matcher(line);

            if (initialMatcher.find()) {
                plants = initialMatcher.group(1).toCharArray();
            } else if(ruleMatcher.find()) {
                Rule rule = new Rule(ruleMatcher.group(1).equals("#"), ruleMatcher.group(2).equals("#"),
                        ruleMatcher.group(3).equals("#"), ruleMatcher.group(4).equals("#"),
                        ruleMatcher.group(5).equals("#"), ruleMatcher.group(6).equals("#"));
                rules.add(rule);
            }

            line = bufferedReader.readLine();
        }

        List<Integer> plantLocations = new ArrayList<>();
        for (int i = 0; i < plants.length; i++) {
            if(plants[i] == '#') {
                plantLocations.add(i);
            }
        }

        PlantRow plantRow = new PlantRow(plantLocations, rules);
//        Set<Integer> twentiethGeneration = plantRow.getPlantsAtGeneration(20);
//        System.out.println(twentiethGeneration);
//        System.out.println(twentiethGeneration.stream().mapToInt(Integer::intValue).sum());

        Set<Long> crazyGeneration = plantRow.getPlantsAtLargeGeneration(50000000000L);
        System.out.println(crazyGeneration.stream().reduce(0L, Long::sum));
    }
}
