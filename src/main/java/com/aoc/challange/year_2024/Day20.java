package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @see <a href="https://adventofcode.com/2024/day/20/problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/20/input">input</a>
 */
public class Day20 {

    private record Point(int x, int y, int distance) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private char[][] maze;
    private Point start;
    private final List<Point> racePoints = new ArrayList<>();

    private void initialiseInputs() {
        List<String> inputs = new ArrayList<>(InputFormatter.formatLines("input/2024/Day20").stream().toList());
        int size = inputs.size();
        maze = new char[size][inputs.get(0).length()];

        for (int r = 0; r < inputs.size(); r++) {
            String s = inputs.get(r);
            for (int c = 0; c < inputs.get(r).length(); c++) {
                maze[r][c] = s.charAt(c);
                if (maze[r][c] == 'S') {
                    start = new Point(r, c, 0);
                }
            }
        }
    }

    private boolean isIndexValid(Point p) {
        return p.x >= 0 && p.x < maze.length && p.y >= 0 && p.y < maze[0].length;
    }

    private int findHonestPath() {
        Queue<Point> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        q.add(start);
        int pathLength = 0;
        while (!q.isEmpty()) {
            Point cp = q.poll();
            int r = cp.x;
            int c = cp.y;
            racePoints.add(cp);
            if (maze[r][c] == 'E') {
                pathLength = cp.distance;
                break;
            }
            List<Point> nextPoints = new ArrayList<>();
            nextPoints.add(new Point(r, c + 1, -1));
            nextPoints.add(new Point(r, c - 1, -1));
            nextPoints.add(new Point(r - 1, c, -1));
            nextPoints.add(new Point(r + 1, c, -1));
            for (Point p : nextPoints) {
                if (isIndexValid(p) && maze[p.x][p.y] != '#' && !racePoints.contains(p)) {
                    Point p1 = new Point(p.x, p.y, cp.distance + 1);
                    q.add(p1);
                }
            }
        }
        return pathLength;
    }

    private void findCheatWays(int maxCheatTime) {
        long start = System.currentTimeMillis();
        long count = 0;
        for (int i = 0; i < racePoints.size() - 1; i++) {
            Point p1 = racePoints.get(i);
            long cheatCount = IntStream.range(i, racePoints.size()).filter(index -> {
                Point p2 = racePoints.get(index);
                int cheatTime = Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
                int pathDistance = Math.abs(p2.distance - p1.distance);
                return cheatTime >= 2 && cheatTime <= maxCheatTime && pathDistance >= (100 + cheatTime);
            }).count();
            count += cheatCount;
        }
        System.out.printf("For max cheat time %s, no of cheat ways %s%n", maxCheatTime, count);
        long end = System.currentTimeMillis();
        System.out.printf("Time taken to calculate cheat ways for max cheat time %s : %s millis%n", maxCheatTime,end - start);
    }

    private void solution() {
        initialiseInputs();
        int honestPath = findHonestPath();
        System.out.println("Honest path length: " + honestPath);
        findCheatWays(2);
        findCheatWays(20);
    }

    public static void main(String[] args) {
        Day20 d = new Day20();
        d.solution();
    }

}
