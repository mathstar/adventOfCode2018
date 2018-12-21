package com.staricka.aoc2018.day21;

@FunctionalInterface
public interface Operation {
    CpuState apply(CpuState cpuState, int a, int b, int c);
}
