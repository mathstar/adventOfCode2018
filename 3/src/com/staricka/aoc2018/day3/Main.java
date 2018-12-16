package com.staricka.aoc2018.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern idPattern = Pattern.compile("#([0-9]+)");
    private static final Pattern xPattern = Pattern.compile("@ ([0-9]+)");
    private static final Pattern yPattern = Pattern.compile(",([0-9]+)");
    private static final Pattern widthPattern = Pattern.compile(": ([0-9]+)");
    private static final Pattern heightPattern = Pattern.compile("x([0-9]+)");

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        Field field = new Field(1000, 1000);

        AtomicInteger count = new AtomicInteger(0);
        bufferedReader.lines().forEach(line -> {
            Matcher idMatcher = idPattern.matcher(line);
            Matcher xMatcher = xPattern.matcher(line);
            Matcher yMatcher = yPattern.matcher(line);
            Matcher widthMatcher = widthPattern.matcher(line);
            Matcher heightMatcher = heightPattern.matcher(line);

            idMatcher.find();
            int id = Integer.valueOf(idMatcher.group(1));

            xMatcher.find();
            int x = Integer.valueOf(xMatcher.group(1));

            yMatcher.find();
            int y = Integer.valueOf(yMatcher.group(1));

            widthMatcher.find();
            int width = Integer.valueOf(widthMatcher.group(1));

            heightMatcher.find();
            int height = Integer.valueOf(heightMatcher.group(1));

            field.claim(id, x, y, width, height);
            System.out.println("Processed: " + count.incrementAndGet());
        });

        System.out.println(field.tilesWithMultipleClaims().size());
        System.out.println(field.uniqueClaim());
    }
}
