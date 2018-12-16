package com.staricka.aoc2018.day15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Unit extends Tile {
    private static final int INITIAL_HEALTH = 200;
    private static final int ATTACK_POWER = 3;

    private static int nextId = 0;

    private int id;
    private int health;
    private int attackPower;
    private boolean alive;
    private UnitType unitType;

    public Unit(int x, int y, UnitType unitType) {
        super(x, y);
        id = nextId++;
        health = INITIAL_HEALTH;
        attackPower = ATTACK_POWER;
        alive = true;
        this.unitType = unitType;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public boolean isAlive() {
        return alive;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(Cave cave, int damage) {
        health -= damage;
        if(health <= 0) {
            alive = false;
            cave.kill(this);
        }
    }

    @Override
    public char getRepresentation() {
        return unitType.getRepresentation();
    }

    public void executeTurn(Cave cave, Runnable finishedCallback) {
        if(!isAlive()) {
            // dead, don't do anything
            return;
        }

        List<Unit> targets = identifyTargets(cave);
        if (targets.isEmpty()) {
            // no more targets, combat ends
            finishedCallback.run();
            return;
        }

        List<Unit> targetsInRange = targets.stream().filter(this::isInRange).collect(Collectors.toList());

        if (!targetsInRange.isEmpty()) {
            // target in range, attack
            attack(cave, targetsInRange);
            return;
        }

        List<Tile> openTargetTiles = findOpenTilesInRangeOfTargets(cave, targets);
        if (openTargetTiles.isEmpty()) {
            // no movement targets, end turn
            return;
        }

        List<Tile> closestTargetTiles = cave.findClosestTargets(this, new HashSet<>(openTargetTiles));
        if (closestTargetTiles.isEmpty()) {
            // no path to targets, end turn
            return;
        }

        // find best step towards target and step
        Tile movementTarget = closestTargetTiles.stream().sorted().findFirst().get();
        Tile step = cave.findBestStep(this, movementTarget);
        cave.step(this, step);

        // attack phase
        targetsInRange = identifyTargets(cave).stream().filter(this::isInRange).collect(Collectors.toList());
        attack(cave, targetsInRange);
    }

    private List<Unit> identifyTargets(Cave cave) {
        return cave.getUnits(unitType.getEnemies());
    }

    private void attack(Cave cave, List<Unit> targets) {
        if(targets.isEmpty()) {
            // nothing to attack, skip
            return;
        }

        List<Unit> bestTargets = null;
        int lowestHealth = Integer.MAX_VALUE;

        for(Unit target : targets) {
            if(target.getHealth() < lowestHealth) {
                bestTargets = new ArrayList<>();
                bestTargets.add(target);
                lowestHealth = target.getHealth();
            } else if (target.getHealth() == lowestHealth) {
                bestTargets.add(target);
            }
        }

        Unit bestTarget = bestTargets.stream().sorted().findFirst().get();
        bestTarget.takeDamage(cave, attackPower);
    }

    private List<Tile> findOpenTilesInRangeOfTargets(Cave cave, List<Unit> targets) {
        List<Tile> openTiles = new ArrayList<>(targets.size() * 4);
        targets.forEach(target -> openTiles.addAll(cave.getOpenTilesAround(target)));
        return openTiles;
    }

    private boolean isInRange(Unit unit) {
        return Math.abs(unit.getX() - getX()) + Math.abs(unit.getY() - getY()) == 1;
    }
}
