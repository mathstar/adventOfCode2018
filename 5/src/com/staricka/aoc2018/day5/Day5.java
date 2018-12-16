package com.staricka.aoc2018.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.OptionalInt;

public class Day5 {
    public static void main(String[] args) throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        String value = bufferedReader.lines().findAny().get();
        Polymer polymer = new Polymer(value);
        polymer.processReaction();

        Map<Character, Polymer> shortPolymers = ShortPolymer.buildAndReactAllShortPolymers(value);
        OptionalInt shortestPolymer = shortPolymers.values().stream().mapToInt(p -> p.value.length()).min();

        System.out.println("Units remaining: " + polymer.value.length());
        System.out.println("Shortest polymer: " + shortestPolymer.getAsInt());
    }
}
