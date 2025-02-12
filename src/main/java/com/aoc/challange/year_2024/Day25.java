package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @see <a href="https://adventofcode.com/2024/day/25/problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/25/input">input</a>
 */
public class Day25 {

    private final List<int[]> locks = new ArrayList<>();
    private final List<int[]> keys = new ArrayList<>();
    private final int rows = 7;
    private final int maxPinHeight = rows - 1;

    private int[] getPinHeights(String[][] scemantic) {
        boolean isLock = String.join("", scemantic[0]).replaceAll("#", "").isEmpty();

        int[] pinHeights = new int[scemantic[0].length];
        if (isLock) {
            for (int c = 0; c < scemantic[0].length; c++) {
                int r = 0;
                while (scemantic[r][c].equals("#")) {
                    r++;
                }
                pinHeights[c] = r - 1;
            }
        } else {
            for (int c = 0; c < scemantic[0].length; c++) {
                int r = scemantic.length - 1;
                while (scemantic[r][c].equals("#")) {
                    r--;
                }
                pinHeights[c] = scemantic.length - 1 - r - 1;
            }
        }
        return pinHeights;
    }

    private void initialiseInput() {
        List<String> lines = new ArrayList<>(InputFormatter.formatLines("input/2024/Day25").stream().toList());
        int columns = lines.get(0).length();
        String[][] schematic = null;
        boolean isLock = false;
        int i = 0;
        for (String line : lines) {
            if (!line.isEmpty()) {
                if (i == 0) {
                    isLock = line.replaceAll("#", "").isEmpty();
                    schematic = new String[rows][columns];
                }
                schematic[i] = line.split("");
                i++;

                if (i == rows) {
                    if (isLock) {
                        locks.add(getPinHeights(schematic));
                    } else {
                        keys.add(getPinHeights(schematic));
                    }
                    i = 0;
                }
            }
        }
    }

    private void solutionPart1() {

        int lockKeyPair = 0;
        int noOfPins = locks.get(0).length;

        for (int[] lock : locks) {
            for (int[] key : keys) {
                boolean allPinFit = IntStream.range(0, noOfPins).allMatch(i -> (lock[i] + key[i]) < maxPinHeight);
                if (allPinFit) {
                    lockKeyPair++;
                }
            }
        }
        System.out.println("Fitting lock/key pair: " + lockKeyPair);
    }

    private void solution() {
        initialiseInput();
        solutionPart1();

    }

    public static void main(String[] args) {

        Day25 d = new Day25();
        d.solution();
    }
}
