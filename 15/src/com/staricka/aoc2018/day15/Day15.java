package com.staricka.aoc2018.day15;

import java.io.FileReader;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Day15 {
    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws Exception {
        //normalExecute();
        executeUntilNoElfDeaths();
    }

    private static void normalExecute() throws Exception {
        Cave cave = StringTranslator.translate(new FileReader(INPUT));
        System.out.println(StringTranslator.translateCave(cave));

        cave.executeTurnsUntilFinished(turns -> {
            System.out.println("Combat finished in turn " + turns);
            System.out.println(StringTranslator.translateCave(cave));

            int remainingHitPoints = cave.getUnits().stream().filter(Unit::isAlive).mapToInt(Unit::getHealth).sum();
            System.out.println("Turns: " + (turns - 1));
            System.out.println("Remaining hit points: " + remainingHitPoints);
            System.out.println("Result: " + (turns - 1) * remainingHitPoints);
        }, false);
    }

    private static void executeUntilNoElfDeaths() throws Exception {
        AtomicInteger elfAttackPower = new AtomicInteger(3);
        AtomicBoolean elvesDied = new AtomicBoolean(true);

        do {
            System.out.println("Executing with elf attack power: " + elfAttackPower);
            Cave cave = StringTranslator.translate(new FileReader(INPUT), elfAttackPower.get());
            int elves = cave.getUnits(Collections.singleton(UnitType.ELF)).size();

            cave.executeTurnsUntilFinished(turns -> {
                long elvesAlive = cave.getUnits().stream().filter(Unit::isAlive).filter(unit -> unit.getUnitType().equals(UnitType.ELF))
                        .count();
                if(elvesAlive == elves) {
                    int remainingHitPoints = cave.getUnits().stream().filter(Unit::isAlive).mapToInt(Unit::getHealth).sum();
                    System.out.println("Turns: " + (turns - 1));
                    System.out.println("Remaining hit points: " + remainingHitPoints);
                    System.out.println("Result: " + (turns - 1) * remainingHitPoints);
                    System.out.println("Elf attack power: " + elfAttackPower.get());
                    elvesDied.set(false);
                }
            }, false);
            elfAttackPower.incrementAndGet();
        } while (elvesDied.get());
    }

    private static void verifyCave(Cave cave) {
        Tile[][] tiles = cave.getTiles();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                Tile tile = tiles[x][y];
                if (tile.getX() != x || tile.getY() != y) {
                    System.out.println(String.format("Unit coord mismatch for %s at array %d,%d with coord %d,%d",
                            tile.getRepresentation(), x, y, tile.getX(), tile.getY()));
                }
            }
        }
    }
}
