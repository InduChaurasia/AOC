package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

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
    private final Map<Point, Integer> distanceMap = new HashMap<>();

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
            distanceMap.put(cp, cp.distance);
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
                if (isIndexValid(p) && maze[p.x][p.y] != '#' && !distanceMap.containsKey(p)) {
                    Point p1 = new Point(p.x, p.y, cp.distance + 1);
                    q.add(p1);
                }
            }
        }
        return pathLength;
    }

    private Set<Point> findPossiblePoints(Point p, int t) {
        Set<Point> uniqueSet = new HashSet<>();
        for (int t1 = 0; t1 <= t; t1++) {
            int t2 = t - t1;
            uniqueSet.add(new Point(p.x - t1, p.y + t2, 0));
            uniqueSet.add(new Point(p.x + t1, p.y + t2, 0));
            uniqueSet.add(new Point(p.x - t1, p.y - t2, 0));
            uniqueSet.add(new Point(p.x + t1, p.y - t2, 0));
        }
        return uniqueSet;
    }

    private void findCheatWays(int maxCheatTime) {
        long start = System.currentTimeMillis();
        Map<Integer, Integer> saveAndCheatWays = new HashMap<>();
        for (Point p1 : distanceMap.keySet()) {
            int d1 = distanceMap.get(p1);
            for (int cheat = 2; cheat <= maxCheatTime; cheat++) {
                Set<Point> possiblePoints = findPossiblePoints(p1, cheat);
                for (Point p2 : possiblePoints) {
                    if (isIndexValid(p2) && distanceMap.containsKey(p2) && distanceMap.get(p2) - d1 >= (100 + cheat)) {
                        int save = distanceMap.get(p2) - d1;
                        saveAndCheatWays.put(save, saveAndCheatWays.getOrDefault(save, 0) + 1);
                    }
                }
            }
        }

        int count = 0;
        for (Integer i : saveAndCheatWays.keySet().stream().sorted().toList()) {
            count += saveAndCheatWays.get(i);
            // System.out.printf("save %s , ways %s%n", i, saveAndCheatWays.get(i));
        }
        System.out.printf("For max cheat time %s, no of ways %s%n", maxCheatTime, count);
        long end = System.currentTimeMillis();
        System.out.printf("time taken: %s millis%n",end-start);
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
