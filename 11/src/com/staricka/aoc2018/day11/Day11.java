package com.staricka.aoc2018.day11;

public class Day11 {
    public static void main(String[] args) {
        tests();

        System.out.println(new Grid(5719).findBest3x3());
        System.out.println(new Grid(5719).findBestSquare());
    }

    private static void tests() {
        System.out.println("Tests:");
        System.out.println(new Grid(8).getPowerLevelAt(3,5) == 4);
        System.out.println(new Grid(57).getPowerLevelAt(122,79) == -5);
        System.out.println(new Grid(39).getPowerLevelAt(217,196) == 0);
        System.out.println(new Grid(71).getPowerLevelAt(101,153) == 4);
        System.out.println(new Grid(18).findBest3x3().equals("33,45"));
        System.out.println();
    }
}
