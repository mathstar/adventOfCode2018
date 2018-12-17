import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 {
    private static final Pattern PATTERN = Pattern.compile("(x|y)=([0-9]+).*(x|y)=([0-9]+)\\.\\.([0-9]+)");
//    private static final String INPUT = "example.txt";
    private static final String INPUT = "input.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT));

        List<Tile> tiles = new ArrayList<>();
        bufferedReader.lines().forEach(line -> {
            Matcher matcher = PATTERN.matcher(line);
            matcher.find();

            String fixedVariable = matcher.group(1);
            int fixed = Integer.parseInt(matcher.group(2));
            int flexStart = Integer.parseInt(matcher.group(4));
            int flexEnd = Integer.parseInt(matcher.group(5));

            for (int flex = flexStart; flex <= flexEnd; flex++) {
                if (fixedVariable.equals("x")) {
                    tiles.add(new Tile(fixed, flex, TileType.WALL));
                } else {
                    tiles.add(new Tile(flex, fixed, TileType.WALL));
                }
            }
        });

        Field field = new Field(tiles.stream().mapToInt(Tile::getY).min().getAsInt(),
                tiles.stream().mapToInt(Tile::getY).max().getAsInt(), tiles);
        System.out.println(field);
        field.propagateWater();
        System.out.println(field);
        System.out.println("All water: " + field.countWater());
        System.out.println("Still water: " + field.countStillWater());
    }
}
