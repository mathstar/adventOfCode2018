package com.staricka.aoc2018.day9;

public class Day9 {
    private static final int[] numPlayers = {7, 10, 13, 17, 21, 30, 428, 428};
    private static final int[] lastMarble = {25, 1618, 7999, 1104, 6111, 5807, 72061, 72061*100};

    private static final int CASE_TO_RUN = 7;

    public static void main(String[] args) {
        Game game = new Game(numPlayers[CASE_TO_RUN], lastMarble[CASE_TO_RUN]);
        //Game game = new Game(428, 7206100);
        game.playGame(false, false, false);
//        game.playGameEfficiently();
        System.out.println();
        game.printScores();
    }

}
