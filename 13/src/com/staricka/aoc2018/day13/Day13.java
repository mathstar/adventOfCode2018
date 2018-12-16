package com.staricka.aoc2018.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day13 {
    private static final String INPUT = "input.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT));

        List<TrackSegment> trackSegments = new ArrayList<>();
        List<Minecart> minecarts = new ArrayList<>();

        String line = bufferedReader.readLine();
        int y = 0;
        while (line != null) {
            char[] tiles = line.toCharArray();
            for(int x = 0; x < tiles.length; x++) {
                Optional<TrackType> trackType = TrackType.parse(tiles[x]);
                Optional<CartHeading> cartHeading = CartHeading.parse(tiles[x]);

                if(trackType.isPresent()) {
                    trackSegments.add(new TrackSegment(x, y, trackType.get()));
                }

                if(cartHeading.isPresent()) {
                    trackSegments.add(new TrackSegment(x, y, cartHeading.get().getTrackType()));
                    minecarts.add(new Minecart(x, y, cartHeading.get()));
                }
            }

            y++;
            line = bufferedReader.readLine();
        }

//        TrackMap trackMap = new TrackMap(trackSegments, minecarts);
//        String collision = trackMap.processTicksUntilCollistion();
//        System.out.println(collision);

        TrackMap trackMap = new TrackMap(trackSegments, minecarts);
        String remaining = trackMap.processTicksWithRemovals();
        System.out.println(remaining);
    }
}
