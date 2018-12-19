package day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)");
    private static final Pattern IP_PATTERN = Pattern.compile("#ip ([0-9])");

//    private static final String INPUT = "example.txt";
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

        //CpuState result = executeProgram(instructions, ipRegister);
        //System.out.println(result.getRegister(0));
        //System.out.println(result);
        //System.out.println(result.getRegister(5));
        guess();
    }

    public static void guess() {
        int target = 10551276;
        //int target = 876;
        long sum = 0;
        for (int i = 1; i <= target; i ++) {
            if(target % i == 0) {
                System.out.println(i);
                sum += i;
            }
        }
        System.out.println(sum);
    }

    public static CpuState executeProgram(List<Instruction> instructions, int ipRegister) {
        long ip = 0;
        CpuState cpuState = new CpuState(6);
        boolean foo = true;
        long change = 0;
        //cpuState = cpuState.setRegister(0, 1);
        while(ip >= 0 && ip < instructions.size()) {
            if (foo && ip == 2) {
                System.out.println(cpuState);
                foo = false;
            }
            if (!foo && cpuState.getRegister(0) != change) {
                System.out.println(cpuState);
                change = cpuState.getRegister(0);
            }
            cpuState = cpuState.setRegister(ipRegister, ip);
            cpuState = instructions.get((int)ip).execute(cpuState);
            ip = cpuState.getRegister(ipRegister) + 1;
        }
        return cpuState;
    }
}
