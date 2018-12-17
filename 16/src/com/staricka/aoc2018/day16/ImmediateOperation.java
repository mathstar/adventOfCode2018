package com.staricka.aoc2018.day16;

import java.util.function.BiFunction;

public class ImmediateOperation implements Operation {
    private final BiFunction<Integer, Integer, Integer> intOp;

    public ImmediateOperation(BiFunction<Integer, Integer, Integer> intOp) {
        this.intOp = intOp;
    }

    @Override
    public CpuState apply(CpuState cpuState, int a, int b, int c) {
        return cpuState.setRegister(c, intOp.apply(cpuState.getRegister(a), b));
    }
}
