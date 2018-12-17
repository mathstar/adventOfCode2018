package com.staricka.aoc2018.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {
    private static final Pattern BEFORE_PATTERN =
            Pattern.compile("Before: \\[([0-9]+), ([0-9]+), ([0-9]+), ([0-9]+)\\]");
    private static final Pattern AFTER_PATTERN =
            Pattern.compile("After:  \\[([0-9]+), ([0-9]+), ([0-9]+), ([0-9]+)\\]");
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)");

    private static final String INPUT_1 = "input1.txt";
    private static final String INPUT_2 = "input2.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(INPUT_1));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(INPUT_2));
        //part1(bufferedReader1);
        part2(bufferedReader1, bufferedReader2);
    }

    public static void part1(BufferedReader bufferedReader) throws Exception {
        int matchesThreeCount = 0;

        String line = bufferedReader.readLine();
        while (line != null) {
            Matcher beforeMatcher = BEFORE_PATTERN.matcher(line);
            beforeMatcher.find();

            line = bufferedReader.readLine();
            Matcher instructionMatcher = INSTRUCTION_PATTERN.matcher(line);
            instructionMatcher.find();

            line = bufferedReader.readLine();
            Matcher afterMatcher = AFTER_PATTERN.matcher(line);
            afterMatcher.find();

            CpuState beforeState = new CpuState(
                    new int[] {Integer.valueOf(beforeMatcher.group(1)), Integer.valueOf(beforeMatcher.group(2)),
                            Integer.valueOf(beforeMatcher.group(3)), Integer.valueOf(beforeMatcher.group(4))});
            CpuState afterState = new CpuState(
                    new int[] {Integer.valueOf(afterMatcher.group(1)), Integer.valueOf(afterMatcher.group(2)),
                            Integer.valueOf(afterMatcher.group(3)), Integer.valueOf(afterMatcher.group(4))});

            int a = Integer.valueOf(instructionMatcher.group(2));
            int b = Integer.valueOf(instructionMatcher.group(3));
            int c = Integer.valueOf(instructionMatcher.group(4));

            int matches = 0;
            for (OpCode opCode : OpCode.values()) {
                matches += opCode.testCode(beforeState, afterState, a, b, c) ? 1 : 0;
            }

            if (matches >= 3) {
                matchesThreeCount++;
            }

            bufferedReader.readLine();
            line = bufferedReader.readLine();
        }
        System.out.println("Matched three: " + matchesThreeCount);
    }

    public static void part2(BufferedReader bufferedReader1, BufferedReader bufferedReader2) throws Exception {
        Map<Integer, Set<OpCode>> possibleOpCodes = identifyPossibleOpCodes(bufferedReader1);
        reduceOpCodePossibilities(possibleOpCodes);

        Map<Integer, OpCode> opCodeMap = possibleOpCodes.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().iterator().next()));
        executeProgram(opCodeMap, bufferedReader2);
    }

    private static void reduceOpCodePossibilities(Map<Integer, Set<OpCode>> possibleOpCodes) {
        while (possibleOpCodes.values().stream().mapToInt(Set::size).max().getAsInt() > 1) {
            List<Entry<Integer, Set<OpCode>>> determinedOpCodes =
                    possibleOpCodes.entrySet().stream().filter(e -> e.getValue().size() == 1)
                            .collect(Collectors.toList());
            for (Entry<Integer, Set<OpCode>> determinedEntry : determinedOpCodes) {
                for (Entry<Integer, Set<OpCode>> possibleEntry : possibleOpCodes.entrySet()) {
                    if (possibleEntry.getKey() == determinedEntry.getKey()) {
                        continue;
                    }
                    possibleEntry.getValue().remove(determinedEntry.getValue().iterator().next());
                }
            }
        }
    }

    private static Map<Integer, Set<OpCode>> identifyPossibleOpCodes(BufferedReader bufferedReader) throws Exception {
        Map<Integer, Set<OpCode>> opCodeMap = new HashMap<>();

        String line = bufferedReader.readLine();
        while (line != null) {
            Matcher beforeMatcher = BEFORE_PATTERN.matcher(line);
            beforeMatcher.find();

            line = bufferedReader.readLine();
            Matcher instructionMatcher = INSTRUCTION_PATTERN.matcher(line);
            instructionMatcher.find();

            line = bufferedReader.readLine();
            Matcher afterMatcher = AFTER_PATTERN.matcher(line);
            afterMatcher.find();

            CpuState beforeState = new CpuState(
                    new int[] {Integer.valueOf(beforeMatcher.group(1)), Integer.valueOf(beforeMatcher.group(2)),
                            Integer.valueOf(beforeMatcher.group(3)), Integer.valueOf(beforeMatcher.group(4))});
            CpuState afterState = new CpuState(
                    new int[] {Integer.valueOf(afterMatcher.group(1)), Integer.valueOf(afterMatcher.group(2)),
                            Integer.valueOf(afterMatcher.group(3)), Integer.valueOf(afterMatcher.group(4))});

            int code = Integer.valueOf(instructionMatcher.group(1));
            int a = Integer.valueOf(instructionMatcher.group(2));
            int b = Integer.valueOf(instructionMatcher.group(3));
            int c = Integer.valueOf(instructionMatcher.group(4));

            Set<OpCode> matches = new HashSet<>();
            for (OpCode opCode : OpCode.values()) {
                if (opCode.testCode(beforeState, afterState, a, b, c)) {
                    matches.add(opCode);
                }
            }

            opCodeMap.putIfAbsent(code, matches);
            opCodeMap.get(code).retainAll(matches);

            bufferedReader.readLine();
            line = bufferedReader.readLine();
        }
        return opCodeMap;
    }

    private static void executeProgram(Map<Integer, OpCode> opCodeMap, BufferedReader bufferedReader) throws Exception {
        CpuState state = new CpuState(4);

        String line = bufferedReader.readLine();
        while (line != null) {
            Matcher instructionMatcher = INSTRUCTION_PATTERN.matcher(line);
            instructionMatcher.find();
            int code = Integer.valueOf(instructionMatcher.group(1));
            int a = Integer.valueOf(instructionMatcher.group(2));
            int b = Integer.valueOf(instructionMatcher.group(3));
            int c = Integer.valueOf(instructionMatcher.group(4));

            state = opCodeMap.get(code).applyCode(state, a, b, c);

            line = bufferedReader.readLine();
        }

        System.out.println("Final state: " + state);
    }
}
