package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/16">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/16/input">input</a>
 */
public class Day16 {
    private static String[][] maze;
    private int sr, sc;

    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private record Step(int cost, int r, int c, Direction d) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Step path = (Step) o;
            return r == path.r && c == path.c && d == path.d;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c, d);
        }
    }

    private void initializeMaze() {
        List<String> l = InputFormatter.formatLines("input/2024/Day16").stream().toList();
        maze = new String[l.size()][l.get(0).length()];
        int i = 0;
        for (String s : l) {
            maze[i] = s.split("");
            if (s.contains("S")) {
                sr = i;
                sc = s.indexOf("S");
            }
            i++;
        }
    }

    private void solutionPart1() {
        PriorityQueue<Step> q = new PriorityQueue<>(Comparator.comparingInt(p -> p.cost));
        initializeMaze();
        q.add(new Step(0, sr, sc, Direction.RIGHT));
        List<Step> seen = new ArrayList<>();
        int cost = -1;
        while (!q.isEmpty()) {
            Step cs = q.poll();
            seen.add(cs);
            //System.out.println(cs.r+","+cs.c+","+cs.cost);
            if (maze[cs.r][cs.c].equals("E")) {
                cost = cs.cost;
                break;
            }
            getApplicableDirections(cs.d).forEach(d -> {
                int stepScore = d != cs.d ? 1001 : 1;
                int r1 = cs.r, c1 = cs.c;
                if (d == Direction.UP) {
                    r1 = cs.r - 1;
                } else if (d == Direction.DOWN) {
                    r1 = cs.r + 1;
                } else if (d == Direction.LEFT) {
                    c1 = cs.c - 1;
                } else {
                    c1 = cs.c + 1;
                }
                Step nextStep = new Step(cs.cost + stepScore, r1, c1, d);
                if (!(r1 < 0 || r1 > maze.length - 1 || c1 < 0 || c1 > maze[0].length - 1) && !maze[r1][c1].equals("#") && !seen.contains(nextStep)) {
                    q.add(nextStep);
                }
            });
        }
        System.out.println(cost);
    }

    private List<Direction> getApplicableDirections(Direction cd) {
        List<Direction> directions = new ArrayList<>(3);
        if (cd == Direction.RIGHT) {
            directions.add(Direction.RIGHT);
            directions.add(Direction.UP);
            directions.add(Direction.DOWN);
        } else if (cd == Direction.LEFT) {
            directions.add(Direction.LEFT);
            directions.add(Direction.UP);
            directions.add(Direction.DOWN);
        } else if (cd == Direction.UP) {
            directions.add(Direction.UP);
            directions.add(Direction.LEFT);
            directions.add(Direction.RIGHT);
        } else {
            directions.add(Direction.DOWN);
            directions.add(Direction.LEFT);
            directions.add(Direction.RIGHT);
        }
        return directions;
    }

    public static void main(String[] args) {
        Day16 d = new Day16();
        d.solutionPart1();
    }
}
