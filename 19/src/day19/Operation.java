package day19;

@FunctionalInterface
public interface Operation {
    CpuState apply(CpuState cpuState, long a, long b, long c);
}
