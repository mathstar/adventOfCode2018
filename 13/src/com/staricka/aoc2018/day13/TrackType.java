package com.staricka.aoc2018.day13;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TrackType {
    UP_DOWN('|'), LEFT_RIGHT('-'), INTERSECTION('+'), DIAGONAL_UP('/'), DIAGONAL_DOWN('\\');

    private static final Map<Character, TrackType> REPRESENTATION_MAP = Collections.unmodifiableMap(
            Arrays.stream(values()).collect(Collectors.toMap(TrackType::getRepresentation, Function.identity())));

    char representation;

    TrackType(char representation) {
        this.representation = representation;
    }

    public char getRepresentation() {
        return representation;
    }

    public static Optional<TrackType> parse(char character) {
        return Optional.ofNullable(REPRESENTATION_MAP.get(character));
    }
}
