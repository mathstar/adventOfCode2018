package day19;

import java.util.function.BiFunction;

public class ImmediateOperation implements Operation {
    private final BiFunction<Long, Long, Long> intOp;

    public ImmediateOperation(BiFunction<Long, Long, Long> intOp) {
        this.intOp = intOp;
    }

    @Override
    public CpuState apply(CpuState cpuState, long a, long b, long c) {
        return cpuState.setRegister((int)c, intOp.apply(cpuState.getRegister((int)a), b));
    }
}
