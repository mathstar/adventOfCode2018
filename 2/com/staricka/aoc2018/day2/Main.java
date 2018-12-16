package com.staricka.aoc2018.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main {
    public static void main(final String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));

        int count = 0;
        int check2 = 0;
        int check3 = 0;
        String line = bufferedReader.readLine();
        while (line != null) {
            count++;
            System.out.println(count);

            check2 += isTwoSame(line) ? 1 : 0;
            check3 += isThreeSame(line) ? 1 : 0;

            line = bufferedReader.readLine();
        }

        System.out.println(check2 * check3);
    }

    private static boolean isTwoSame(final String id) {
        char[] chars = id.toCharArray();
        Map<Character, Integer> counts = new HashMap<>();
        for(char c : chars) {
            counts.computeIfAbsent(c, (x) -> 0);
            counts.put(c, counts.get(c) + 1);
        }

        for(Entry<Character, Integer> entry : counts.entrySet()) {
            if(entry.getValue() == 2) {
                return true;
            }
        }

        return false;
    }

    private static boolean isThreeSame(final String id) {
        char[] chars = id.toCharArray();
        Map<Character, Integer> counts = new HashMap<>();
        for(char c : chars) {
            counts.computeIfAbsent(c, (x) -> 0);
            counts.put(c, counts.get(c) + 1);
        }

        for(Entry<Character, Integer> entry : counts.entrySet()) {
            if(entry.getValue() == 3) {
                return true;
            }
        }

        return false;
    }
}
