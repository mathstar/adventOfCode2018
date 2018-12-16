package com.staricka.aoc2018.day3;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    final int x;
    final int y;
    final List<Integer> claims = new ArrayList<>();

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void claim(int id) {
        claims.add(id);
    }

    public boolean hasMultipleClaims() {
        return claims.size() > 1;
    }
}
