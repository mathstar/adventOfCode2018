package com.staricka.aoc2018.day5;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShortPolymer extends Polymer {
    private static final List<Character> characters =
            Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                    't', 'u', 'v', 'w', 'x', 'y', 'z');

    public ShortPolymer(String value, char remove) {
        super(preprocessPolymer(value, remove));
    }

    private static String preprocessPolymer(String value, char remove) {
        char capital = String.valueOf(remove).toUpperCase().charAt(0);

        char[] units = value.toCharArray();
        StringBuilder postRemoval = new StringBuilder();

        for (char current : units) {
            if (current != remove && current != capital) {
                postRemoval.append(current);
            }
        }

        return postRemoval.toString();
    }

    public static Map<Character, Polymer> buildAndReactAllShortPolymers(String value) {
        Map<Character, Polymer> result =
                characters.stream().collect(Collectors.toMap(Function.identity(), c -> new ShortPolymer(value, c)));
        result.values().forEach(Polymer::processReaction);
        return result;
    }
}
