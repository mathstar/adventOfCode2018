package com.staricka.aoc2018.day21;

import java.util.function.BiFunction;

public class RegisterOperation implements Operation {
    private final BiFunction<Integer, Integer, Integer> intOp;

    public RegisterOperation(BiFunction<Integer, Integer, Integer> intOp) {
        this.intOp = intOp;
    }

    @Override
    public CpuState apply(CpuState cpuState, int a, int b, int c) {
        return cpuState.setRegister(c, intOp.apply(cpuState.getRegister(a), cpuState.getRegister(b)));
    }
}
