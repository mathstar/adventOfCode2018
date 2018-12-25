package com.staricka.aoc2018.day22;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Field {
    private static final int TRAVEL_TIME = 1;
    private static final int TOOL_SWITCH_TIME = 7;

    private Map<Integer, Map<Integer, Integer>> erosionIndexMap;
    private Map<Integer, Map<Integer, TileType>> tiles;
    private Map<Integer, Map<Integer, SearchState>> searchStateMap;

    public static void main(String[] args) {
        Field field = new Field();
        //System.out.println(field.getRisk(10, 10, 510));
        //System.out.println(field.getRisk(13, 726, 3066));
        //System.out.println(field.computeShortestPath(10, 10, 510));
        System.out.println(field.computeShortestPath(13, 726, 3066));
    }

    public Field() {
        erosionIndexMap = new HashMap<>();
        tiles = new HashMap<>();
        searchStateMap = new HashMap<>();
    }

    public long getRisk(int targetX, int targetY, int depth) {
        long risk = 0;
        for (int x = 0; x <= targetX; x++) {
            for (int y = 0; y <= targetY; y++) {
                int thisX = x;
                int thisY = y;
                risk += erosionIndexMap.computeIfAbsent(x, i -> new HashMap<>())
                        .computeIfAbsent(y, i -> computeErosionIndex(thisX, thisY, targetX, targetY, depth)) % 3;
            }
        }

        for (int y = 0; y <= targetY * 2; y++) {
            for (int x = 0; x <= targetX * 2; x++) {
                System.out.print(getOrComputeTileType(x, y, targetX, targetY, depth).getRepresentation());
            }
            System.out.println();
        }

        return risk;
    }

    private int computeShortestPath(int targetX, int targetY, int depth) {
        int maxX = targetX * 5;
        int maxY = targetY * 5;

        searchStateMap.computeIfAbsent(0, i -> new HashMap<>()).put(0, new SearchState(Collections.singletonMap(Tool.TORCH, 0)));
        boolean updated;
        int count = 0;
        do {
            System.out.println(count++);
            updated = false;
            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    updated = update(x, y, targetX, targetY, depth) || updated;
                }
            }
        } while (updated);

        System.out.println(searchStateMap);
        SearchState end = searchStateMap.get(targetX).get(targetY);
        Map<Tool, Integer> endToolMap = new EnumMap<>(end.getToolPathLength());
        for(Entry<Tool, Integer> entry : endToolMap.entrySet()) {
            if(entry.getKey() != Tool.TORCH) {
                entry.setValue(entry.getValue() + TOOL_SWITCH_TIME);
            }
        }
        return endToolMap.values().stream().mapToInt(Integer::intValue).min().getAsInt();
    }

    private boolean update(int x, int y, int targetX, int targetY, int depth) {
        boolean updated = false;
        updated = update(x, y, x - 1, y, targetX, targetY, depth) || updated;
        updated = update(x, y, x, y - 1, targetX, targetY, depth) || updated;
        updated = update(x, y, x + 1, y, targetX, targetY, depth) || updated;
        updated = update(x, y, x, y + 1, targetX, targetY, depth) || updated;
        return updated;
    }

    private boolean update(int x, int y, int fromX, int fromY, int targetX, int targetY, int depth) {
        TileType tileType = getOrComputeTileType(x, y, targetX, targetY, depth);
        Set<Tool> validTools = tileType.getValidTools();

        if (fromX < 0 || fromY < 0) {
            return false;
        }

        SearchState fromSearchState = searchStateMap.computeIfAbsent(fromX, i -> new HashMap<>()).get(fromY);
        TileType fromTileType = getOrComputeTileType(fromX, fromY, targetX, targetY, depth);

        Map<Tool, Integer> toolMap = new EnumMap<>(Tool.class);
        for(Tool tool : Tool.values()) {
            if (fromSearchState == null) {
                fromSearchState = new SearchState(Collections.emptyMap());
            }

            if(validTools.contains(tool) && fromTileType.getValidTools().contains(tool)) {
                Integer pathLength = null;
                for(Entry<Tool, Integer> fromTime : fromSearchState.getToolPathLength().entrySet()) {
                    if(fromTime.getKey() == tool) {
                        int thisLength = fromTime.getValue() + TRAVEL_TIME;
                        if(pathLength == null) {
                            pathLength = thisLength;
                        } else {
                            pathLength = Integer.min(pathLength, thisLength);
                        }
                    } else {
                        int thisLength = fromTime.getValue() + TOOL_SWITCH_TIME + TRAVEL_TIME;
                        if(pathLength == null) {
                            pathLength = thisLength;
                        } else {
                            pathLength = Integer.min(pathLength, thisLength);
                        }
                    }
                }
                if(toolMap.get(tool) == null || toolMap.get(tool) > pathLength) {
                    toolMap.put(tool, pathLength);
                }
            }
        }

        SearchState existingSearchState = searchStateMap.computeIfAbsent(x, i -> new HashMap<>()).get(y);
        Map<Tool, Integer> mergedToolMap;
        boolean updated = false;
        if(existingSearchState == null) {
            mergedToolMap = new EnumMap<>(Tool.class);
        } else {
            mergedToolMap = new EnumMap<>(existingSearchState.getToolPathLength());
        }
        for(Entry<Tool, Integer> entry : toolMap.entrySet()) {
            Integer existing = mergedToolMap.get(entry.getKey());
            if(entry.getValue() != null && (existing == null || existing > toolMap.get(entry.getKey()))) {
                mergedToolMap.put(entry.getKey(), entry.getValue());
                updated = true;
            }
        }

        searchStateMap.computeIfAbsent(x, i -> new HashMap<>()).put(y, new SearchState(mergedToolMap));
        return updated;
    }

    private TileType getOrComputeTileType(int x, int y, int targetX, int targetY, int depth) {
        int erosionIndex = erosionIndexMap.computeIfAbsent(x, i -> new HashMap<>())
                .computeIfAbsent(y, i -> computeErosionIndex(x, y, targetX, targetY, depth));
        TileType tileType = tiles.computeIfAbsent(x, i -> new HashMap<>())
                .computeIfAbsent(y, i -> TileType.getTypeFromErosionIndex(erosionIndex));
        return tileType;
    }

    private int computeErosionIndex(int x, int y, int targetX, int targetY, int depth) {
        if ((x == 0 && y == 0) || (x == targetX && y == targetY)) {
            return 0;
        } else if (y == 0) {
            return ((x * 16807) % 20183 + depth % 20183) % 20183;
        } else if (x == 0) {
            return ((y * 48271) % 20183 + depth % 20183) % 20183;
        } else {
            return ((erosionIndexMap.get(x).get(y - 1) % 20183) * (erosionIndexMap.get(x - 1).get(y) % 20183)
                    + depth % 20183) % 20183;
        }
    }
}
