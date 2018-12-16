package com.staricka.aoc2018.day11;

public class Grid {
    FuelCell[][] fuelCells;

    public Grid(int gridSerialNumber) {
        fuelCells = new FuelCell[300][300];

        for(int i = 0; i < 300; i++) {
            for(int j = 0; j < 300; j++) {
                fuelCells[i][j] = new FuelCell(i + 1, j + 1, gridSerialNumber);
            }
        }
    }

    public int getPowerLevelAt(int x, int y) {
        return fuelCells[x-1][y-1].powerLevel;
    }

    public String findBest3x3() {
        String best = null;
        int bestPower = Integer.MIN_VALUE;
        for(int x = 1; x <= 297; x++) {
            for(int y = 1; y <= 297; y++) {
                int powerLevel = computeSquarePower(x, y, 3);
                if(powerLevel > bestPower) {
                    best = x + "," + y;
                    bestPower = powerLevel;
                }
            }
        }
        return best;
    }

    public String findBestSquare() {
        String best = null;
        int bestPower = Integer.MIN_VALUE;

        for(int size = 1; size <= 300; size++) {
            for (int x = 1; x < 301 - size; x++) {
                for (int y = 1; y < 301 - size; y++) {
                    int powerLevel = computeSquarePower(x, y, size);
                    if (powerLevel > bestPower) {
                        best = x + "," + y + "," + size;
                        bestPower = powerLevel;
                    }
                }
            }
        }

        return best;
    }

    private int computeSquarePower(int x, int y, int size) {
        int power = 0;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                power += fuelCells[x + i - 1][y + j - 1].powerLevel;
            }
        }
        return power;
    }
}
