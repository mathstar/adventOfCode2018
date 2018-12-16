package com.staricka.aoc2018.day9;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private List<Player> players;
    private Circle circle;

    private int currentPlayerIndex;
    private int lastMarble;

    public Game(int numPlayers, int lastMarble) {
        players = IntStream.range(0, numPlayers).mapToObj(i -> new Player()).collect(Collectors.toList());
        circle = new Circle(lastMarble);
        circle.addMarble(new Marble(), new RelativePosition(Direction.CLOCKWISE, 0));
        currentPlayerIndex = 0;
        this.lastMarble = lastMarble;
    }

    private void playTurn(Marble marble, boolean printRound, boolean printState, boolean printWins) {
        Player currentPlayer = players.get(currentPlayerIndex);

        //DEBUG
//        Marble current = circle.getMarble(new RelativePosition(Direction.CLOCKWISE, 0));
//        Marble sevenCounter = circle.getMarble(new RelativePosition(Direction.COUNTER_CLOCKWISE, 7));
//        System.out.println(String.format("Current %s, placing %s, seven counter %s", current.getId(), marble.getId(),
//                sevenCounter.getId()));
        //DEBUG

        if (marble.getId() % 23 == 0) {
            // multiple of 23
            if (printWins) {
                System.out.println(circle);
            }

            currentPlayer.winMarble(marble);
            Marble extraMarble = circle.getMarble(new RelativePosition(Direction.COUNTER_CLOCKWISE, 7));
            currentPlayer.winMarble(extraMarble);
            circle.removeMarble(extraMarble);
            circle.setCurrentMarble(circle.getMarble(new RelativePosition(Direction.COUNTER_CLOCKWISE, 6)));

            if (printWins) {
                System.out.println(currentPlayer.getId() + " wins " + marble.getId() + "," + extraMarble.getId());
            }
        } else {
            // normal
            circle.addMarble(marble, new RelativePosition(Direction.CLOCKWISE, 2));
            circle.setCurrentMarble(marble);
        }

        if (printState) {
            System.out.println(circle);
        } else if (printRound) {
            System.out.println("Played round with " + marble.getId());
        }
        advancePlayer();
    }

    public void playGame(boolean printRound, boolean printState, boolean printWins) {
        Marble marble = new Marble();
        while (marble.getId() <= lastMarble) {
            playTurn(marble, printRound, printState, printWins);
            marble = new Marble();
        }
    }

    public void playGameEfficiently() {
        for (int i = 23; i <= lastMarble; i += 23) {
            Player player = players.get(i % players.size() - 1);
            player.winMarble(new Marble(i));
            player.winMarble(new Marble((i - 4 - (3 * (i - 23) / 23)) / 2));
        }
    }

    public void printScores() {
        int maxScore = -1;
        Player winner = null;
        for (Player player : players) {
            System.out.println(player.getId() + " scored " + player.getScore());
            if (player.getScore() > maxScore) {
                maxScore = player.getScore();
                winner = player;
            }
        }
        System.out.println("Winner was " + winner.getId() + " with " + winner.getScore());
    }

    private void advancePlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex -= players.size();
        }
    }
}
