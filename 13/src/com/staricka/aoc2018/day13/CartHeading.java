package com.staricka.aoc2018.day13;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CartHeading {
    UP('^', TrackType.UP_DOWN), DOWN('v', TrackType.UP_DOWN), LEFT('<', TrackType.LEFT_RIGHT), RIGHT('>',
            TrackType.LEFT_RIGHT);

    private static final Map<Character, CartHeading> REPRESENTATION_MAP = Collections.unmodifiableMap(
            Arrays.stream(values()).collect(Collectors.toMap(CartHeading::getRepresentation, Function.identity())));

    char representation;
    TrackType trackType;

    CartHeading(char representation, TrackType trackType) {
        this.representation = representation;
        this.trackType = trackType;
    }

    public char getRepresentation() {
        return representation;
    }

    public TrackType getTrackType() {
        return trackType;
    }

    public static Optional<CartHeading> parse(char character) {
        return Optional.ofNullable(REPRESENTATION_MAP.get(character));
    }
}
