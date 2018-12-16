package com.staricka.aoc2018.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {
    private static final String INPUT = "input.txt";
    private static final Pattern PATTERN =
            Pattern.compile("< ?(-?[0-9]+),  ?(-?[0-9]+)>[^<]*< ?(-?[0-9]+),  ?(-?[0-9]+)");

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT));

        List<Point> points = new ArrayList<>();
        bufferedReader.lines().forEach(line -> {
            Matcher matcher = PATTERN.matcher(line);
            matcher.find();

            points.add(new Point(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)),
                    Integer.valueOf(matcher.group(3)), Integer.valueOf(matcher.group(4))));
        });

        Field field = new Field(points);
        int steps = 0;
        while (!field.isPossibleImage()) {
            System.out.println("Step: " + (steps++));
            field.step();
        }
        System.out.println(field.drawField());
    }
}
