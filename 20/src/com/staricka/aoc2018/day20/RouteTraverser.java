package com.staricka.aoc2018.day20;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.IntStream;

public class RouteTraverser {
    private static final char NORTH = 'N';
    private static final char SOUTH = 'S';
    private static final char EAST = 'E';
    private static final char WEST = 'W';
    private static final char PIPE = '|';
    private static final char OPEN_PAREN = '(';
    private static final char CLOSE_PAREN = ')';

    private static final char ROOM = '.';
    private static final char ORIGIN = 'X';
    private static final char VERTICAL_DOOR = '|';
    private static final char HORIZONTAL_DOOR = '-';
    private static final char WALL = '#';

    private Map<Integer, Map<Integer, Room>> rooms;
    private Room origin;

    public RouteTraverser(final String regex) {
        origin = new Room(0, 0, 0);
        rooms = new HashMap<>();
        rooms.computeIfAbsent(0, i -> new HashMap<>()).put(0, origin);

        Room current = origin;

        Stack<Room> backtrackStack = new Stack<>();
        for (char c : regex.toCharArray()) {
            Optional<Room> next = Optional.empty();
            final Room finalCurrent = current;
            switch (c) {
                case NORTH:
                    next = Optional.of(rooms.computeIfAbsent(current.getX(), i -> new HashMap<>())
                            .computeIfAbsent(current.getY() - 1,
                                    i -> new Room(finalCurrent.getDistanceToOrigin() + 1, finalCurrent.getX(),
                                            finalCurrent.getY() - 1)));
                    current.setNorth(next.get());
                    next.get().setSouth(current);
                    break;
                case SOUTH:
                    next = Optional.of(rooms.computeIfAbsent(current.getX(), i -> new HashMap<>())
                            .computeIfAbsent(current.getY() + 1,
                                    i -> new Room(finalCurrent.getDistanceToOrigin() + 1, finalCurrent.getX(),
                                            finalCurrent.getY() + 1)));
                    current.setSouth(next.get());
                    next.get().setNorth(current);
                    break;
                case EAST:
                    next = Optional.of(rooms.computeIfAbsent(current.getX() + 1, i -> new HashMap<>())
                            .computeIfAbsent(current.getY(),
                                    i -> new Room(finalCurrent.getDistanceToOrigin() + 1, finalCurrent.getX() + 1,
                                            finalCurrent.getY())));
                    current.setEast(next.get());
                    next.get().setWest(current);
                    break;
                case WEST:
                    next = Optional.of(rooms.computeIfAbsent(current.getX() - 1, i -> new HashMap<>())
                            .computeIfAbsent(current.getY(),
                                    i -> new Room(finalCurrent.getDistanceToOrigin() + 1, finalCurrent.getX() - 1,
                                            finalCurrent.getY())));
                    current.setWest(current);
                    next.get().setEast(current);
                    break;
                case PIPE:
                    current = backtrackStack.peek();
                    break;
                case OPEN_PAREN:
                    backtrackStack.push(current);
                    break;
                case CLOSE_PAREN:
                    current = backtrackStack.pop();
            }

            if (next.isPresent()) {
                current = next.get();
            }
        }
    }

    public int getMaxDistanceFromOrigin() {
        return rooms.values().stream().flatMap(r -> r.values().stream()).mapToInt(Room::getDistanceToOrigin).max()
                .getAsInt();
    }

    public long getNumberOfPathsAtLeast(int least) {
        return rooms.values().stream().flatMap(r -> r.values().stream()).mapToInt(Room::getDistanceToOrigin)
                .filter(x -> x >= least).count();
    }

    @Override
    public String toString() {
        int minX = rooms.values().stream().flatMap(r -> r.values().stream()).mapToInt(Room::getX).min().getAsInt();
        int maxX = rooms.values().stream().flatMap(r -> r.values().stream()).mapToInt(Room::getX).max().getAsInt();
        int minY = rooms.values().stream().flatMap(r -> r.values().stream()).mapToInt(Room::getY).min().getAsInt();
        int maxY = rooms.values().stream().flatMap(r -> r.values().stream()).mapToInt(Room::getY).max().getAsInt();

        StringBuilder stringBuilder = new StringBuilder();
        IntStream.range(0, 2 * (maxX - minX + 1) + 1).forEach(i -> stringBuilder.append(WALL));
        stringBuilder.append('\n');
        for (int y = minY; y <= maxY; y++) {
            // draw rooms
            for (int x = minX; x <= maxX; x++) {
                Room room = rooms.get(x).get(y);

                // draw left wall
                if (x == minX) {
                    stringBuilder.append(WALL);
                } else if (room.getWest().isPresent()) {
                    stringBuilder.append(VERTICAL_DOOR);
                } else {
                    stringBuilder.append(WALL);
                }

                if (room.getDistanceToOrigin() == 0) {
                    stringBuilder.append(ORIGIN);
                } else {
                    stringBuilder.append(ROOM);
                }

                // if end, draw right wall
                if (x == maxX) {
                    stringBuilder.append(WALL);
                }
            }
            stringBuilder.append('\n');

            // draw walls
            for (int x = minX; x <= maxX; x++) {
                Room room = rooms.get(x).get(y);
                stringBuilder.append(WALL);

                // draw left wall
                if (room.getSouth().isPresent()) {
                    stringBuilder.append(HORIZONTAL_DOOR);
                } else {
                    stringBuilder.append(WALL);
                }

                // if end, draw right wall
                if (x == maxX) {
                    stringBuilder.append(WALL);
                }
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
