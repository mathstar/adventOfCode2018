package com.staricka.aoc2018.day21;

import java.util.Arrays;

public class CpuState {
    private int[] registers;

    public CpuState(int numRegisters) {
        registers = new int[numRegisters];
    }

    public CpuState(int[] registers) {
        this.registers = registers;
    }

    public CpuState(CpuState cpuState) {
        registers = Arrays.copyOf(cpuState.registers, cpuState.registers.length);
    }

    public int getRegister(int id) {
        return registers[id];
    }

    public CpuState setRegister(int id, int value) {
        CpuState cpuState = new CpuState(this);
        cpuState.registers[id] = value;
        return cpuState;
    }

    @Override
    public String toString() {
        return Arrays.toString(registers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CpuState cpuState = (CpuState) o;
        return Arrays.equals(registers, cpuState.registers);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(registers);
    }
}
