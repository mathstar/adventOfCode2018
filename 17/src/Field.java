import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Field {
    private static final int SPRING_X = 500;

    private Map<Integer, Map<Integer, Tile>> tiles;
    private int minY;
    private int maxY;

    public Field(int minY, int maxY, Collection<Tile> tiles) {
        this.minY = minY;
        this.maxY = maxY;

        this.tiles = new HashMap<>();
        tiles.forEach(this::putTile);
    }

    public void propagateWater() {
        Tile initialWater = new Tile(SPRING_X, minY, TileType.FLOWING_WATER);
        putTile(initialWater);

        Stack<Tile> flowing = new Stack<>();
        flowing.push(initialWater);

        while (flowing.size() > 0) {
            //System.out.println(this);
            Tile tile = flowing.pop();

            Tile below = getTile(tile.getX(), tile.getY() + 1);
            switch (below.getTileType()) {
                case OPEN: {
                    FlowResult down = flowDown(tile);
                    if (down.isContained()) {
                        for (int y = tile.getY() + 1; y < down.contained; y++) {
                            Tile newTile = new Tile(below.getX(), y, TileType.FLOWING_WATER);
                            putTile(newTile);
                            flowing.push(newTile);
                        }
                    } else {
                        for (int y = tile.getY() + 1; y <= maxY; y++) {
                            Tile newTile = new Tile(below.getX(), y, TileType.FLOWING_WATER);
                            putTile(newTile);
                        }
                    }
                    break;
                }
                case WALL:
                case STILL_WATER: {
                    FlowResult left = flowLeft(tile);
                    FlowResult right = flowRight(tile);

                    if (left.isContained() && right.isContained()) {
                        for (int x = left.contained + 1; x < right.contained; x++) {
                            Tile newTile = new Tile(x, tile.getY(), TileType.STILL_WATER);
                            putTile(newTile);

                            Tile checkTile = findFlowingUp(newTile);
                            if (checkTile != null) {
                                flowing.push(checkTile);
                            }
                        }
                    } else if (left.isContained()) {
                        for (int x = left.contained + 1; x <= right.falls; x++) {
                            //System.out.println(x);
                            Tile newTile = new Tile(x, tile.getY(), TileType.FLOWING_WATER);
                            putTile(newTile);
                        }
                        if (getTile(right.falls, tile.getY() + 1).getTileType() == TileType.OPEN) {
                            flowing.push(getTile(right.falls, tile.getY()));
                        }
                    } else if (right.isContained()) {
                        for (int x = left.falls; x < right.contained; x++) {
                            Tile newTile = new Tile(x, tile.getY(), TileType.FLOWING_WATER);
                            putTile(newTile);
                        }
                        if (getTile(left.falls, tile.getY() + 1).getTileType() == TileType.OPEN) {
                            flowing.push(getTile(left.falls, tile.getY()));
                        }
                    } else {
                        for (int x = left.falls; x <= right.falls; x++) {
                            Tile newTile = new Tile(x, tile.getY(), TileType.FLOWING_WATER);
                            putTile(newTile);
                        }
                        if (getTile(left.falls, tile.getY() + 1).getTileType() == TileType.OPEN) {
                            flowing.push(getTile(left.falls, tile.getY()));
                        }
                        if (getTile(right.falls, tile.getY() + 1).getTileType() == TileType.OPEN) {
                            flowing.push(getTile(right.falls, tile.getY()));
                        }
                    }
                    break;
                }
            }
        }
    }

    public long countWater() {
        return tiles.values().stream().flatMap(t -> t.values().stream())
                .filter(tile -> tile.getTileType() == TileType.STILL_WATER
                        || tile.getTileType() == TileType.FLOWING_WATER).count();
    }

    public long countStillWater() {
        return tiles.values().stream().flatMap(t -> t.values().stream())
                .filter(tile -> tile.getTileType() == TileType.STILL_WATER).count();
    }

    private Tile getTile(int x, int y) {
        return tiles.computeIfAbsent(x, i -> new HashMap<>()).computeIfAbsent(y, i -> new Tile(x, y, TileType.OPEN));
    }

    private FlowResult flowLeft(Tile tile) {
        for (int x = tile.getX() - 1; true; x--) {
            if (getTile(x, tile.getY()).getTileType() == TileType.WALL) {
                return FlowResult.ofContained(x);
            }
            if (getTile(x, tile.getY() + 1).getTileType() == TileType.OPEN) {
                return FlowResult.ofFalls(x);
            }
            if (getTile(x, tile.getY() + 1).getTileType() == TileType.FLOWING_WATER) {
                return FlowResult.ofFalls(x + 1);
            }
        }
    }

    private FlowResult flowRight(Tile tile) {
        for (int x = tile.getX() + 1; true; x++) {
            if (getTile(x, tile.getY()).getTileType() == TileType.WALL) {
                return FlowResult.ofContained(x);
            }
            if (getTile(x, tile.getY() + 1).getTileType() == TileType.OPEN) {
                return FlowResult.ofFalls(x);
            }
            if (getTile(x, tile.getY() + 1).getTileType() == TileType.FLOWING_WATER) {
                return FlowResult.ofFalls(x - 1);
            }
        }
    }

    private FlowResult flowDown(Tile tile) {
        for (int y = tile.getY(); y <= maxY; y++) {
            if (getTile(tile.getX(), y).getTileType() == TileType.WALL) {
                return FlowResult.ofContained(y);
            }
        }
        return FlowResult.ofFalls(maxY);
    }

    private Tile findFlowingUp(Tile tile) {
        for (int y = tile.getY() - 1; y > minY; y--) {
            Tile checkTile = getTile(tile.getX(), y);
            if (checkTile.getTileType() == TileType.OPEN) {
                continue;
            } else if (checkTile.getTileType() == TileType.FLOWING_WATER) {
                return checkTile;
            } else {
                break;
            }
        }
        return null;
    }

    private static class FlowResult {
        Integer contained;
        Integer falls;

        static FlowResult ofContained(int contained) {
            FlowResult flowResult = new FlowResult();
            flowResult.contained = contained;
            return flowResult;
        }

        static FlowResult ofFalls(int falls) {
            FlowResult flowResult = new FlowResult();
            flowResult.falls = falls;
            return flowResult;
        }

        boolean isContained() {
            return contained != null;
        }

        boolean isFalls() {
            return contained != null;
        }
    }

    private void putTile(Tile tile) {
        tiles.computeIfAbsent(tile.getX(), i -> new HashMap<>()).put(tile.getY(), tile);
    }

    @Override
    public String toString() {
        int minX = tiles.values().stream().flatMap(m -> m.values().stream()).mapToInt(Tile::getX).min().getAsInt();
        int maxX = tiles.values().stream().flatMap(m -> m.values().stream()).mapToInt(Tile::getX).max().getAsInt();

        StringBuilder stringBuilder = new StringBuilder();
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Tile tile = getTile(x, y);
                if (tile == null) {
                    stringBuilder.append(TileType.OPEN.getRepresentation());
                } else {
                    stringBuilder.append(tile.getTileType().getRepresentation());
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
