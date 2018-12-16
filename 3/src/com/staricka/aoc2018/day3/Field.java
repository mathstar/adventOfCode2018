package com.staricka.aoc2018.day3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class Field {
    final Tile[][] tiles;

    public Field(int width, int height) {
        tiles = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    public void claim(int id, int x, int y, int width, int height) {
        for (int i = 0; i < width; i ++) {
            for (int j = 0; j < height; j++) {
                tiles[x + i][y + j].claim(id);
            }
        }
    }

    public List<Tile> tilesWithMultipleClaims(){
        List<Tile> result = new ArrayList<>();

        for(Tile[] row : tiles) {
            for(Tile tile : row) {
                if(tile.hasMultipleClaims()) {
                    result.add(tile);
                }
            }
        }
        return result;
    }

    public int uniqueClaim() {
        Map<Integer, Boolean> hasOverlap = new HashMap<>();

        for(Tile[] row : tiles) {
            for(Tile tile : row) {
                boolean isOverlapping = tile.claims.size() > 1;
                for(Integer claim : tile.claims) {
                    hasOverlap.computeIfPresent(claim, (id, state) -> state || isOverlapping);
                    hasOverlap.putIfAbsent(claim, isOverlapping);
                }
            }
        }

        Optional<Entry<Integer, Boolean>> result = hasOverlap.entrySet().stream().filter(entry -> !entry.getValue()).findAny();
        return result.get().getKey();
    }
}
