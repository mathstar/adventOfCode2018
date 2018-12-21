package com.staricka.aoc2018.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)");
    private static final Pattern IP_PATTERN = Pattern.compile("#ip ([0-9])");

    private static final String INPUT = "input.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT));

        String ipLine = bufferedReader.readLine();
        Matcher ipMatcher = IP_PATTERN.matcher(ipLine);
        ipMatcher.find();
        int ipRegister = Integer.parseInt(ipMatcher.group(1));

        List<Instruction> instructions = new ArrayList<>();
        bufferedReader.lines().forEach(line -> {
            Matcher instructionMatcher = INSTRUCTION_PATTERN.matcher(line);
            instructionMatcher.find();
            instructions.add(new Instruction(OpCode.fromAssembly(instructionMatcher.group(1)),
                    Integer.parseInt(instructionMatcher.group(2)), Integer.parseInt(instructionMatcher.group(3)),
                    Integer.parseInt(instructionMatcher.group(4))));
        });

        final int bound = 900000;
        AtomicInteger count = new AtomicInteger(0);
        CpuState initial = new CpuState(new int[] {0, 0, 0, 0, 0, 0});
        CpuState result = executeProgram(instructions, ipRegister, initial, count, bound);
        System.out.println(result);

    }

    public static CpuState executeProgram(List<Instruction> instructions, int ipRegister, CpuState cpuState,
            AtomicInteger count, int bound) {
        Set<Integer> haltingInputs = new HashSet<>();
        int ip = 0;
        while (ip >= 0 && ip < instructions.size()) { //&& count.get() < bound) {
            count.incrementAndGet();
            cpuState = cpuState.setRegister(ipRegister, ip);
            cpuState = instructions.get(ip).execute(cpuState);
            ip = cpuState.getRegister(ipRegister) + 1;

            if (cpuState.getRegister(ipRegister) == 27) {
                if(haltingInputs.contains(cpuState.getRegister(2))) {
                    System.out.println(haltingInputs);
                    break;
                } else {
                    haltingInputs.add(cpuState.getRegister(2));
                }
                System.out.println(cpuState);
            }
        }
        System.out.println("Executed " + count.get() + "/" + bound + " instructions");
        return cpuState;
    }
}
