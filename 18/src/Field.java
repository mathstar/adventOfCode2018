import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Field {
    private static final BiFunction<Tile, Integer, Integer> INCREMENT_COUNT = (a, b) -> b + 1;

    private Map<Integer, Map<Integer, Tile>> tiles;

    public Field(Map<Integer, Map<Integer, Tile>> tiles) {
        this.tiles = tiles;
    }

    public boolean step() {
        Map<Integer, Map<Integer, Tile>> newTiles = new HashMap<>();

        for (int x = 0; x < tiles.size(); x++) {
            for (int y = 0; y < tiles.get(0).size(); y++) {
                EnumMap<Tile, Integer> counts = new EnumMap<>(Tile.class);
                Arrays.stream(Tile.values()).forEach(t -> counts.put(t, 0));

                boolean up = (y - 1) >= 0;
                boolean down = (y + 1) < tiles.get(x).size();
                boolean left = (x - 1) >= 0;
                boolean right = (x + 1) < tiles.size();

                if (up && left) {
                    counts.compute(tiles.get(x - 1).get(y - 1), INCREMENT_COUNT);
                }
                if (up) {
                    counts.compute(tiles.get(x).get(y - 1), INCREMENT_COUNT);
                }
                if (up && right) {
                    counts.compute(tiles.get(x + 1).get(y - 1), INCREMENT_COUNT);
                }
                if (left) {
                    counts.compute(tiles.get(x - 1).get(y), INCREMENT_COUNT);
                }
                if (right) {
                    counts.compute(tiles.get(x + 1).get(y), INCREMENT_COUNT);
                }
                if (down && left) {
                    counts.compute(tiles.get(x - 1).get(y + 1), INCREMENT_COUNT);
                }
                if (down) {
                    counts.compute(tiles.get(x).get(y + 1), INCREMENT_COUNT);
                }
                if (down && right) {
                    counts.compute(tiles.get(x + 1).get(y + 1), INCREMENT_COUNT);
                }

                newTiles.computeIfAbsent(x, i -> new HashMap<>()).put(y, tiles.get(x).get(y).transition(counts));
            }
        }

        boolean unchanged = newTiles.equals(tiles);

        tiles = newTiles;
        return unchanged;
    }

    public Map<Integer, Map<Integer, Tile>> getTiles() {
        return tiles;
    }

    public long getResourceValue() {
        return tiles.values().stream().flatMap(t -> t.values().stream()).filter(Tile.TREE::equals).count() * tiles
                .values().stream().flatMap(t -> t.values().stream()).filter(Tile.LUMBERYARD::equals).count();
    }
}
