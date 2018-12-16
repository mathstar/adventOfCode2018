package com.staricka.aoc2018.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Main2 {
    public static void main(final String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));

        int count = 0;

        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (line != null) {
            count++;
            System.out.println(count);

            lines.add(line);

            line = bufferedReader.readLine();
        }

        for(int i = 0; i < lines.size(); i++){
            for(int j = i + 1; j < lines.size(); j++){
                char[] line1 = lines.get(i).toCharArray();
                char[] line2 = lines.get(j).toCharArray();

                int diffs = 0;
                for(int k = 0; k < line1.length; k++) {
                    if(line1[k] != line2[k]) {
                        diffs++;
                    }

                    if (diffs > 1) {
                        break;
                    }
                }

                if (diffs == 1) {
                    System.out.println(line1);
                    System.out.println(line2);
                }
            }


        }

    }

}
