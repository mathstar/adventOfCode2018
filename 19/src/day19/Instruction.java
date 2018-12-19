package day19;

public class Instruction {
    private OpCode opCode;
    private int a;
    private int b;
    private int c;

    public Instruction(OpCode opCode, int a, int b, int c) {
        this.opCode = opCode;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public CpuState execute(CpuState initial) {
        return opCode.applyCode(initial, a, b, c);
    }

    @Override
    public String toString() {
        return opCode + " " + a + " " + b + " " + c;
    }
}
