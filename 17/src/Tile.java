public class Tile {
    private int x;
    private int y;
    private TileType tileType;

    public Tile(int x, int y, TileType tileType) {
        this.x = x;
        this.y = y;
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
