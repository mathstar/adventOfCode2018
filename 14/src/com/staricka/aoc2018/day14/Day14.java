package com.staricka.aoc2018.day14;

public class Day14 {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard("37", 2, 919901);
        //scoreboard.executeRounds(15);
        //System.out.println(scoreboard.getScoreRange(919901, 10));
        System.out.println(scoreboard.seekString("919901"));
    }
}
