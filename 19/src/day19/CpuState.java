package day19;

import java.util.Arrays;

public class CpuState {
    private long[] registers;

    public CpuState(int numRegisters) {
        registers = new long[numRegisters];
    }

    public CpuState(long[] registers) {
        this.registers = registers;
    }

    public CpuState(CpuState cpuState) {
        registers = Arrays.copyOf(cpuState.registers, cpuState.registers.length);
    }

    public long getRegister(long id) {
        return registers[(int)id];
    }

    public CpuState setRegister(long id, long value) {
        CpuState cpuState = new CpuState(this);
        cpuState.registers[(int)id] = value;
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
