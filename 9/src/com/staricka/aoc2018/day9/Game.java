package com.staricka.aoc2018.day9;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private List<Player> players;
    private Circle circle;
    private EfficientCircle efficientCircle;

    private int currentPlayerIndex;
    private int lastMarble;
    private int nextMarble;

    public Game(int numPlayers, int lastMarble) {
        players = IntStream.range(0, numPlayers).mapToObj(i -> new Player(i)).collect(Collectors.toList());
        circle = new Circle(lastMarble);
        circle.addMarble(new Marble(0), new RelativePosition(Direction.CLOCKWISE, 0));
        efficientCircle = new EfficientCircle(new Marble(0));
        currentPlayerIndex = 0;
        this.lastMarble = lastMarble;
        nextMarble = 1;
    }

    private void playTurn(Marble marble, boolean printRound, boolean printState, boolean printWins) {
        Player currentPlayer = players.get(currentPlayerIndex);

        if (marble.getId() % 23 == 0) {
            // multiple of 23
            if (printWins) {
                System.out.println(circle);
            }

            currentPlayer.winMarble(marble);
            Marble extraMarble = circle.getMarble(new RelativePosition(Direction.COUNTER_CLOCKWISE, 7));
            currentPlayer.winMarble(extraMarble);
            //System.out.println(extraMarble.getId());
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

    private void playTurnEfficiently(Marble marble) {
        Player currentPlayer = players.get(currentPlayerIndex);

        if (marble.getId() % 23 == 0) {
            // multiple of 23
            currentPlayer.winMarble(marble);
            Marble extraMarble = efficientCircle.getRelative(-7);
            currentPlayer.winMarble(extraMarble);
            efficientCircle.removeRelativeAndMakeNextCurrent(-7);
        } else {
            // normal
            efficientCircle.insertRelativeAndMakeCurrent(marble, 2);
        }

        //System.out.println(efficientCircle);
        advancePlayer();
    }

    public void playGame(boolean printRound, boolean printState, boolean printWins) {
        Marble marble = new Marble(nextMarble++);
        while (marble.getId() <= lastMarble) {
            playTurn(marble, printRound, printState, printWins);
            //System.out.println(circle);
            marble = new Marble(nextMarble++);
        }
    }

    public void playGameEfficiently() {
        Marble marble = new Marble(nextMarble++);
        while (marble.getId() <= lastMarble) {
            playTurnEfficiently(marble);
            marble = new Marble(nextMarble++);
        }
    }

//    public void playGameEfficiently() {
//        for (int i = 23; i <= lastMarble; i += 23) {
//            Player player = players.get(i % players.size() - 1);
//            player.winMarble(new Marble(i));
//            player.winMarble(new Marble((i - 4 - (3 * (i - 23) / 23)) / 2));
//        }
//    }

    public void printScores() {
        long maxScore = -1;
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

    public List<Player> getPlayers() {
        return players;
    }

    public Circle getCircle() {
        return circle;
    }

    private void advancePlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex -= players.size();
        }
    }
}
