package com.staricka.aoc2018.day20;

import java.io.BufferedReader;
import java.io.FileReader;

public class Day20 {
    private static final String[] INPUTS = {"example1.txt", "example2.txt", "example3.txt", "example4.txt", "input.txt"};
    private static final int CASE = 4;

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUTS[CASE]));

        RouteTraverser routeTraverser = new RouteTraverser(bufferedReader.readLine());
        System.out.println(routeTraverser);
        System.out.println("Max distance to origin: " + routeTraverser.getMaxDistanceFromOrigin());
        System.out.println("Number of paths at least 1000 doors long: " + routeTraverser.getNumberOfPathsAtLeast(1000));
    }
}
