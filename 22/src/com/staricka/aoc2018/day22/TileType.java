package com.staricka.aoc2018.day22;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TileType {
    ROCKY('.', 0, new Tool[] {Tool.CLIMBING_GEAR, Tool.TORCH}), WET('=', 1,
            new Tool[] {Tool.CLIMBING_GEAR, Tool.NEITHER}), NARROW('|', 2,
            new Tool[] {Tool.TORCH, Tool.NEITHER});

    private final static TileType[] GEOLOGIC_INDEX_ARRAY = {ROCKY, WET, NARROW};
    private char representation;
    private int risk;
    private Set<Tool> validTools;

    TileType(char representation, int risk, Tool[] validTools) {
        this.representation = representation;
        this.risk = risk;
        this.validTools = new HashSet<>(Arrays.asList(validTools));
    }

    public static TileType getTypeFromErosionIndex(int geologicIndex) {
        return GEOLOGIC_INDEX_ARRAY[geologicIndex % GEOLOGIC_INDEX_ARRAY.length];
    }

    public char getRepresentation() {
        return representation;
    }

    public int getRisk() {
        return risk;
    }

    public Set<Tool> getValidTools() {
        return validTools;
    }
}
