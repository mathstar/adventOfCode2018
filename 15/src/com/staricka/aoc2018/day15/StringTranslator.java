package com.staricka.aoc2018.day15;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class StringTranslator {
    private static final Map<Character, BiFunction<Integer, Integer, Tile>> ENTITIY_CONSTRUCTOR_MAP;

    static {
        Map<Character, BiFunction<Integer, Integer, Tile>> temp = new HashMap<>();
        temp.put(EmptyTile.REPRESENTATION, EmptyTile::new);
        temp.put(Wall.REPRESENTATION, Wall::new);
        for (UnitType unitType : UnitType.values()) {
            temp.put(unitType.getRepresentation(), (x, y) -> new Unit(x, y, unitType));
        }
        ENTITIY_CONSTRUCTOR_MAP = Collections.unmodifiableMap(temp);
    }

    public static Cave translate(Reader reader) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(reader);

        List<Tile> tiles = new ArrayList<>();
        int width = 0;
        int y = 0;
        String line = bufferedReader.readLine();
        while (line != null) {
            width = Integer.max(width, line.length());
            tiles.addAll(translateLine(line, y));

            line = bufferedReader.readLine();
            y++;
        }

        return new Cave(width, y, tiles);
    }

    public static Cave translate(Reader reader, int elfAttackOverride) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(reader);

        List<Tile> tiles = new ArrayList<>();
        int width = 0;
        int y = 0;
        String line = bufferedReader.readLine();
        while (line != null) {
            width = Integer.max(width, line.length());
            tiles.addAll(translateLine(line, y));

            line = bufferedReader.readLine();
            y++;
        }

        tiles.stream().filter(tile -> tile instanceof Unit).map(tile -> (Unit) tile)
                .filter(unit -> unit.getUnitType() == UnitType.ELF)
                .forEach(unit -> unit.setAttackPower(elfAttackOverride));

        return new Cave(width, y, tiles);
    }

    public static Collection<Tile> translateLine(String line, int y) {
        List<Tile> tiles = new ArrayList<>(line.length());
        char[] chars = line.toCharArray();
        for (int x = 0; x < chars.length; x++) {
            BiFunction<Integer, Integer, Tile> constructor = ENTITIY_CONSTRUCTOR_MAP.get(chars[x]);
            if (constructor != null) {
                tiles.add(constructor.apply(x, y));
            } else {
                throw new IllegalStateException();
            }
        }
        return tiles;
    }

    public static String translateCave(Cave cave) {
        return translateCave(cave.getTiles());
    }

    public static String translateCave(Tile[][] tiles) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                Tile tile = tiles[x][y];
                stringBuilder.append(tile.getRepresentation());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
