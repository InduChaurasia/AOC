package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;


/**
 * @see <a href="https://adventofcode.com/2024/day/8">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/8/input">input</a>
 */
public class Day8 {

    private static class Pair {
        int r;
        int c;

        public Pair(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return r == pair.r && c == pair.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }


    }

    private final String[][] input = readInput();
    private Map<String, Set<Pair>> frequencyLocations = new HashMap<>();
    private Set<Pair> antiNodes = new HashSet<>();

    private String[][] readInput() {
        List<String> list = InputFormatter.formatLines("input/2024/Day8").stream().toList();
        int r = list.size();
        int c = list.get(0).length();
        String[][] input = new String[r][c];
        for (int i = 0; i < r; i++) {
            input[i] = list.get(i).split("");
        }
        return input;
    }

    private Pair updateAntiNodeBefore(Pair p1, Pair p2) {
        int r = input.length;
        int c = input[0].length;
        int anr, anc;

        if (p1.c < p2.c) {
            anc = p1.c - (p2.c - p1.c);
        } else {
            anc = p1.c + (p1.c - p2.c);
        }
        anr = p1.r - (p2.r - p1.r);

        if (anr >= 0 && anr < r && anc >= 0 && anc < c) {
            Pair an = new Pair(anr, anc);
            antiNodes.add(an);
            return an;
        }
        return null;
    }

    private Pair updateAntiNodeAfter(Pair p1, Pair p2) {
        int r = input.length;
        int c = input[0].length;
        int anr, anc;

        if (p1.c < p2.c) {
            anc = p2.c + (p2.c - p1.c);
        } else {
            anc = p2.c - (p1.c - p2.c);
        }
        anr = p2.r + (p2.r - p1.r);

        if (anr >= 0 && anr < r && anc >= 0 && anc < c) {
            Pair an = new Pair(anr, anc);
            antiNodes.add(an);
            return an;
        }
        return null;
    }

    private void updateAntiNodes(Set<Pair> els, Pair cl) {
        for (Pair p : els) {
            updateAntiNodeBefore(p, cl);
            updateAntiNodeAfter(p, cl);
        }
    }

    private void updateAntiNodesRecursive(Set<Pair> els, Pair cl) {

        for (Pair p : els) {
            updateAntiNodeBeforeRecursive(p, cl);
            updateAntiNodeAfterRecursive(p, cl);
            antiNodes.add(p);
            antiNodes.add(cl);
        }
    }

    private void updateAntiNodeBeforeRecursive(Pair p1, Pair p2) {
        Pair an = updateAntiNodeBefore(p1, p2);
        while (an != null) {
            p2 = p1;
            p1 = an;
            an = updateAntiNodeBefore(p1, p2);
        }
    }

    private void updateAntiNodeAfterRecursive(Pair p1, Pair p2) {
        Pair an = updateAntiNodeAfter(p1, p2);
        while (an != null) {
            p1 = p2;
            p2 = an;
            an = updateAntiNodeAfter(p1, p2);
        }
    }

    private void solutionPart1() {
        frequencyLocations = new HashMap<>();
        antiNodes = new HashSet<>();
        updateAntiNodes(false);
    }

    private void solutionPart2() {
        frequencyLocations = new HashMap<>();
        antiNodes = new HashSet<>();
        updateAntiNodes(true);
    }

    private void updateAntiNodes(boolean isRecursive) {
        int rows = input.length;
        int columns = input[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                String cf = input[r][c];
                if (!cf.equals(".")) {
                    Set<Pair> cfl = frequencyLocations.getOrDefault(cf, new HashSet<>());
                    if (!cfl.isEmpty()) {
                        if (isRecursive) {
                            updateAntiNodesRecursive(cfl, new Pair(r, c));
                        } else {
                            updateAntiNodes(cfl, new Pair(r, c));
                        }
                    }
                    cfl.add(new Pair(r, c));
                    frequencyLocations.put(cf, cfl);
                }
            }
        }
        printAntiNodes(rows, columns);
        System.out.println(antiNodes.size());
    }

    public static void main(String[] args) {
        Day8 d = new Day8();
        d.solutionPart1();
        d.solutionPart2();
    }

    private void printAntiNodes(int rows, int columns) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                Pair p = new Pair(r, c);
                String s = input[r][c];
                if (antiNodes.contains(new Pair(p.r, p.c))) {
                    s = s.equals(".") ? "#" : s.concat("$");
                }
                System.out.print(s);
            }
            System.out.println();
        }
    }
}
