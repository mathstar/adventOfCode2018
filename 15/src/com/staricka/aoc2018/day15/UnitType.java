package com.staricka.aoc2018.day15;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum UnitType {
    ELF('E'), GOBLIN('G');

    private static final Map<Character, UnitType> REPRESENTATION_MAP = Collections.unmodifiableMap(
            Arrays.stream(values()).collect(Collectors.toMap(UnitType::getRepresentation, Function.identity())));

    private char representation;

    UnitType(char representation) {
        this.representation = representation;
    }

    public char getRepresentation() {
        return representation;
    }

    public List<UnitType> getEnemies() {
        switch (this) {
            case ELF: return Collections.singletonList(GOBLIN);
            case GOBLIN: return Collections.singletonList(ELF);
        }
        throw new IllegalStateException();
    }
}
