package com.staricka.aoc2018.day16;

public enum OpCode {
    ADDR("addr", new RegisterOperation(Integer::sum)),
    ADDI("addi", new ImmediateOperation(Integer::sum)),
    MULR("mulr", new RegisterOperation(Math::multiplyExact)),
    MULI("muli", new ImmediateOperation(Math::multiplyExact)),
    BANR("banr", new RegisterOperation((a,b) -> a & b)),
    BANI("bani", new ImmediateOperation((a,b) -> a & b)),
    BORR("borr", new RegisterOperation((a,b) -> a | b)),
    BORI("bori", new ImmediateOperation((a,b) -> a | b)),
    SETR("setr", (cpuState, a, b, c) -> cpuState.setRegister(c, cpuState.getRegister(a))),
    SETI("seti", (cpuState, a, b, c) -> cpuState.setRegister(c, a)),
    GTIR("gtir", (cpuState, a, b, c) -> cpuState.setRegister(c, a > cpuState.getRegister(b) ? 1 : 0)),
    GTRI("gtri", (cpuState, a, b, c) -> cpuState.setRegister(c, cpuState.getRegister(a) > b ? 1 : 0)),
    GTRR("gtrr", (cpuState, a, b, c) -> cpuState.setRegister(c, cpuState.getRegister(a) > cpuState.getRegister(b) ? 1 : 0)),
    EQIR("eqir", (cpuState, a, b, c) -> cpuState.setRegister(c, a == cpuState.getRegister(b) ? 1 : 0)),
    EQRI("eqri", (cpuState, a, b, c) -> cpuState.setRegister(c, cpuState.getRegister(a) == b ? 1 : 0)),
    EQRR("eqrr", (cpuState, a, b, c) -> cpuState.setRegister(c, cpuState.getRegister(a) == cpuState.getRegister(b) ? 1 : 0));

    private String assembly;
    private Operation operation;

    OpCode(String assembly, Operation operation) {
        this.assembly = assembly;
        this.operation = operation;
    }

    public boolean testCode(CpuState before, CpuState after, int a, int b, int c) {
        return after.equals(operation.apply(before, a, b, c));
    }

    public CpuState applyCode(CpuState before, int a, int b, int c) {
        return operation.apply(before, a, b, c);
    }
}
