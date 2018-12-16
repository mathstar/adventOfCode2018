package com.staricka.aoc2018.day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Day8 {
    private static final String INPUT = "input.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT));

        int[] values = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();

        Node root = parseNode(values, 0);

        System.out.println("Sum of metadata: " + root.getRecursiveSumOfMetadata());
        System.out.println("Value of root: " + root.getValue());
    }

    private static Node parseNode(int[] values, int startingIndex) {
        int numberOfNodes = values[startingIndex];
        int numberOfMetadata = values[startingIndex + 1];

        Node node = new Node(startingIndex);
        int currentIndex = startingIndex + 2;

        for(int i = 0; i < numberOfNodes; i++) {
            Node child = parseNode(values, currentIndex);
            node.addChild(child);
            currentIndex = child.endingIndex + 1;
        }

        for(int j = 0; j < numberOfMetadata; j++) {
            node.addMetadata(values[currentIndex++]);
        }

        node.setEndingIndex(currentIndex - 1);

        return node;
    }
}
