import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day18 {
    //private static final String INPUT = "example.txt";
    private static final String INPUT = "input.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT));

        String[] rows = bufferedReader.lines().toArray(String[]::new);
        Map<Integer, Map<Integer, Tile>> tiles = new HashMap<>();

        for(int y = 0; y < rows.length; y++) {
            char[] chars = rows[y].toCharArray();

            for(int x = 0; x < chars.length; x++) {
                tiles.computeIfAbsent(x, i -> new HashMap<>()).put(y, Tile.parse(chars[x]));
            }
        }

        Field field = new Field(tiles);
//        int count = 0;
//        boolean unchanged = false;
//        while (count < 10 && !unchanged) {
//            unchanged = field.step();
//            count++;
//        }
        field = patternFinder(field, 1000000000);
        System.out.println(field.getResourceValue());
    }

    /*
     * Originally used arrays rather than maps, but Java made this cycle indentification logic a pain to write with arrays.
     */
    private static Field patternFinder(Field field, int target) {
        List<Map<Integer, Map<Integer, Tile>>> fields = new ArrayList<>();
        HashMap<Map<Integer, Map<Integer, Tile>>, Integer> seenFields = new HashMap<>();

        int count = 0;
        boolean unchanged = false;
        while (count < target && !unchanged) {
            unchanged = field.step();
            count++;

            if(seenFields.containsKey(field.getTiles())) {
                int firstSeen = seenFields.get(field.getTiles());
                int interval = count - firstSeen;
                int desired = (target - count) % interval - 1;

                return new Field(fields.get(firstSeen + desired));
            } else {
                fields.add(field.getTiles());
                seenFields.put(field.getTiles(), count);
            }
        }
        return field;
    }

}
