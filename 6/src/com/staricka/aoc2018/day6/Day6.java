package com.staricka.aoc2018.day6;

import com.staricka.aoc2018.day6.BoundingBox.PointResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day6 {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));

        List<Point> points = bufferedReader.lines().map(line -> new Point(Integer.valueOf(line.split(",")[0].trim()),
                Integer.valueOf(line.split(",")[1].trim()))).collect(Collectors.toList());

        BoundingBox boundingBox = new BoundingBox(points);
        PointResult largestPointArea = boundingBox.getLargestFiniteArea();

        System.out.println("Largest point: " + largestPointArea.point.id);
        System.out.println("Largest point area: " + largestPointArea.area);
        System.out.println("Area within 10000: " + boundingBox.getAreaWithinRadius(10000, points));

        /*
        System.out.println(
                "Bounding box: " + boundingBox.leftX + "-" + boundingBox.rightX + ", " + boundingBox.topY + "-"
                        + boundingBox.bottomY);
        for (Point point : points) {
            boolean isBounding = boundingBox.isBoundingPoint(point);
            System.out.println(
                    "Point " + point.id + " @ " + point.x + "," + point.y + " is " + (isBounding ? "" : "not ")
                            + "a bounding point");
        }
        */
    }
}
