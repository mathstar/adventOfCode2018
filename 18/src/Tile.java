import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Tile {
    OPEN('.'), TREE('|'), LUMBERYARD('#');

    private static final Map<Character, Tile> REPRESENTATION_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(Tile::getRepresentation, Function.identity()));

    private char representation;

    Tile(char representation) {
        this.representation = representation;
    }

    public char getRepresentation() {
        return representation;
    }

    public Tile transition(EnumMap<Tile, Integer> counts) {
        switch (this) {
            case OPEN:
                if (counts.get(TREE) >= 3) {
                    return TREE;
                } else {
                    return OPEN;
                }
            case TREE:
                if (counts.get(LUMBERYARD) >= 3) {
                    return LUMBERYARD;
                } else {
                    return TREE;
                }
            case LUMBERYARD:
                if (counts.get(LUMBERYARD) >= 1 && counts.get(TREE) >= 1) {
                    return LUMBERYARD;
                } else {
                    return OPEN;
                }
        }
        throw new IllegalStateException();
    }

    public static Tile parse(char c) {
        return REPRESENTATION_MAP.get(c);
    }
}
