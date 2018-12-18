package com.staricka.aoc2018.day9;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Day9 {
    private static final int[] numPlayers = {7, 10, 13, 17, 21, 30, 428, 428};
    private static final int[] lastMarble = {25, 1618, 7999, 1104, 6111, 5807, 72061, 7206100};

    private static final int CASE_TO_RUN = 7;

    public static void main(String[] args) {
                Game game = new Game(numPlayers[CASE_TO_RUN], lastMarble[CASE_TO_RUN]);
        //        Game game = new Game(13, 69);
                game.playGameEfficiently();
//        game.playGameEfficiently();
        //        System.out.println();
                game.printScores();
        //test();
    }

    public static void test() {
        Map<Integer, Integer> wins = new HashMap<>();
        long winningScore = 0;
        for (int i = 100; i <= 100; i++) {
            Game game = new Game(13, 23 * i);
            game.playGame(false, false, false);
            Player winner = game.getPlayers().stream().max(Comparator.comparingLong(Player::getScore)).get();
            wins.putIfAbsent(winner.getId(), 0);
            wins.compute(winner.getId(), (id, numWins) -> numWins + 1);
            winningScore = winner.getScore();
        }
        System.out.println(wins);
        System.out.println(winningScore);
    }

}
