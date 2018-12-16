package com.staricka.aoc2018.day11;

public class FuelCell {
    int x;
    int y;
    int powerLevel;

    public FuelCell(int x, int y, int gridSerialNumber) {
        this.x = x;
        this.y = y;

        int rackId = x + 10;
        powerLevel = rackId * y;
        powerLevel += gridSerialNumber;
        powerLevel *= rackId;
        String decimalString = String.format("%03d", powerLevel);
        powerLevel = Integer.valueOf(decimalString.substring(decimalString.length() - 3, decimalString.length() - 2));
        powerLevel -= 5;
    }
}
