package com.staricka.aoc2018.day8;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private static int nextId = 0;

    int id;
    int startingIndex;
    int endingIndex;
    List<Node> children = new ArrayList<>();
    List<Integer> metadata = new ArrayList<>();

    public Node(int startingIndex) {
        id = nextId++;
        this.startingIndex = startingIndex;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void addMetadata(Integer metadatum) {
        metadata.add(metadatum);
    }

    public void setEndingIndex(int endingIndex) {
        this.endingIndex = endingIndex;
    }

    public int getRecursiveSumOfMetadata() {
        return children.stream().mapToInt(Node::getRecursiveSumOfMetadata).sum() + metadata.stream()
                .mapToInt(Integer::intValue).sum();
    }

    public int getValue() {
        if (children.isEmpty()) {
            return metadata.stream().mapToInt(Integer::intValue).sum();
        } else {
            int value = 0;
            for (Integer metadatum : metadata) {
                if(metadatum > 0 && metadatum <= children.size()) {
                    value += children.get(metadatum - 1).getValue();
                }
            }
            return value;
        }
    }
}
