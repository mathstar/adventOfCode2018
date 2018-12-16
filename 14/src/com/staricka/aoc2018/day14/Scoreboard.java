package com.staricka.aoc2018.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Scoreboard {
    List<Character> scores;
    int[] currentScorePointers;
    int numberOfElves;

    public Scoreboard(String scores, int numberOfElves, int initialScoreArraySize) {
        this.scores = new ArrayList<>(initialScoreArraySize);
        populateList(this.scores, scores.toCharArray());

        currentScorePointers = new int[numberOfElves];
        this.numberOfElves = numberOfElves;

        initializeScorePointers();
    }

    public static void populateList(List<Character> list, char[] chars) {
        for (char c : chars) {
            list.add(c);
        }
    }

    public String getScoreBoard() {
        return scores.stream().map(String::valueOf).collect(Collectors.joining());
    }

    private void initializeScorePointers() {
        if (scores.size() < numberOfElves) {
            throw new IllegalStateException();
        }

        for (int elf = 0; elf < numberOfElves; elf++) {
            currentScorePointers[elf] = elf;
        }

        for (int round = 1; round < scores.size() / 2; round++) {
            updateAllScorePointers();
        }
    }

    public String getScoreRange(int startIndex, int number) {
        while (scores.size() < startIndex + number) {
            executeRound();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(scores.get(startIndex + i));
        }
        return stringBuilder.toString();
    }

    public int seekString(String target) {
        Pattern pattern = Pattern.compile(target);

        while (true) {
            String scoreString = getScoreString();
            Matcher matcher = pattern.matcher(scoreString);

            if (matcher.find()) {
                return matcher.toMatchResult().start();
            }

            // no match, double size of score board
            executeRounds(scores.size());
        }
    }

    private String getScoreString() {
        StringBuilder stringBuilder = new StringBuilder();
        scores.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    public void executeRounds(int rounds) {
        IntStream.range(0, rounds).forEach(i -> executeRound());
    }

    public void executeRound() {
        int totalScore = IntStream.range(0, numberOfElves).map(this::getScore).sum();
        char[] newScores = Integer.toString(totalScore).toCharArray();

        populateList(scores, newScores);
        updateAllScorePointers();
    }

    private void updateAllScorePointers() {
        for (int elf = 0; elf < numberOfElves; elf++) {
            updateScorePointer(elf);
        }
    }

    private void updateScorePointer(int elf) {
        int numberToAdvance = getScore(elf) + 1;
        int newPointer = currentScorePointers[elf] + numberToAdvance;

        while (newPointer >= scores.size()) {
            newPointer -= scores.size();
        }

        currentScorePointers[elf] = newPointer;
    }

    private int getScore(int elf) {
        return Integer.parseInt(String.valueOf(scores.get(currentScorePointers[elf])));
    }
}
