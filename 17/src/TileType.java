public enum  TileType {
    WALL('#'), OPEN('.'), FLOWING_WATER('|'), STILL_WATER('~');

    private char representation;

    TileType(char representation) {
        this.representation = representation;
    }

    public char getRepresentation() {
        return representation;
    }
}
