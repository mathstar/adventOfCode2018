package day19;

import java.util.function.BiFunction;

public class RegisterOperation implements Operation {
    private final BiFunction<Long, Long, Long> intOp;

    public RegisterOperation(BiFunction<Long, Long, Long> intOp) {
        this.intOp = intOp;
    }

    @Override
    public CpuState apply(CpuState cpuState, long a, long b, long c) {
        return cpuState.setRegister((int)c, intOp.apply(cpuState.getRegister((int)a), cpuState.getRegister((int)b)));
    }
}
