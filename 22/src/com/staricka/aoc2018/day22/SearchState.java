package com.staricka.aoc2018.day22;

import java.util.Map;

public class SearchState {
    private final Map<Tool, Integer> toolPathLength;

    public SearchState(Map<Tool, Integer> toolPathLength) {
        this.toolPathLength = toolPathLength;
    }

    public Map<Tool, Integer> getToolPathLength() {
        return toolPathLength;
    }
}
