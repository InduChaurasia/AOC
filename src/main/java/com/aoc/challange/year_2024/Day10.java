package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/10">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/10/input">input</a>
 */
public class Day10 {

    private record TrailPoint(int r, int c, int val) {
    }

    private int[][] topographicMap;
    private Map<TrailPoint, Integer> trailPoints;

    private int[][] readInput() {
        trailPoints = new HashMap<>();
        List<String> list = InputFormatter.formatLines("input/2024/Day10").stream().toList();
        int r = list.size();
        int c = list.get(0).length();
        int[][] m = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (list.get(i).charAt(j) != '.') {
                    m[i][j] = Character.getNumericValue(list.get(i).charAt(j));
                } else {
                    m[i][j] = 9999;
                }
                if (m[i][j] == 0) {
                    trailPoints.put(new TrailPoint(i, j, m[i][j]), 0);
                }
            }
        }
        return m;
    }

    private void solutionPart1() {
        calculateScore(false);
    }

    private void calculateScore(boolean isDistinctPath) {
        topographicMap = readInput();
        for (TrailPoint startPoint : trailPoints.keySet()) {
            List<TrailPoint> trailPath = new ArrayList<>();
            trailPath.add(startPoint);
            Set<TrailPoint> endPoints = new HashSet<>();
            int paths = 0;
            while (!trailPath.isEmpty()) {
                TrailPoint ctp = trailPath.remove(trailPath.size() - 1);
                if (ctp.val == 9) {
                    if (isDistinctPath) {
                        paths++;
                    } else {
                        endPoints.add(ctp);
                    }
                } else {
                    addNextTrailPoints(ctp, trailPath);
                }
            }
            int score = isDistinctPath ? paths : endPoints.size();
            trailPoints.put(startPoint, score);
        }
        System.out.println(trailPoints.values().stream().mapToInt(Integer::valueOf).sum());
    }

    private void addNextTrailPoints(TrailPoint ctp, List<TrailPoint> trailPath) {
        addUp(ctp, trailPath);
        addDown(ctp, trailPath);
        addLeft(ctp, trailPath);
        addRight(ctp, trailPath);
    }

    private void addUp(TrailPoint ctp, List<TrailPoint> trailPath) {
        int r = ctp.r - 1;
        int c = ctp.c;
        if (r >= 0 && topographicMap[r][c] == ctp.val + 1) {
            trailPath.add(new TrailPoint(r, c, topographicMap[r][c]));
        }
    }

    private void addDown(TrailPoint ctp, List<TrailPoint> trailPath) {
        int r = ctp.r + 1;
        int c = ctp.c;
        if (r < topographicMap.length && topographicMap[r][c] == ctp.val + 1) {
            trailPath.add(new TrailPoint(r, c, topographicMap[r][c]));
        }
    }

    private void addLeft(TrailPoint ctp, List<TrailPoint> trailPath) {
        int r = ctp.r;
        int c = ctp.c - 1;
        if (c >= 0 && topographicMap[r][c] == ctp.val + 1) {
            trailPath.add(new TrailPoint(r, c, topographicMap[r][c]));
        }
    }

    private void addRight(TrailPoint ctp, List<TrailPoint> trailPath) {
        int cMax = topographicMap[0].length;
        int r = ctp.r;
        int c = ctp.c + 1;
        if (c < cMax && topographicMap[r][c] == ctp.val + 1) {
            trailPath.add(new TrailPoint(r, c, topographicMap[r][c]));
        }
    }

    private void solutionPart2() {
        calculateScore(true);

    }

    public static void main(String[] args) {
        Day10 d = new Day10();
        d.solutionPart1();
        d.solutionPart2();
    }
}
