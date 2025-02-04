package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @see <a href="https://adventofcode.com/2024/day/18">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/18/input">input</a>
 */
public class Day18 {
    private static int M_SIZE;
    private void solution(int limit) {
        char[][] grid = initilizeGrid(limit);
        int[][] sGrid = initilizeScoreGrid();
        while (sGrid[0][0] == Integer.MAX_VALUE) {
            for (int i = M_SIZE - 1; i >= 0; i--) {
                for (int j = M_SIZE - 1; j >= 0; j--) {
                    if (grid[i][j] != '#' && sGrid[i][j] == Integer.MAX_VALUE) {
                        int up = i > 0 ? sGrid[i - 1][j] : Integer.MAX_VALUE;
                        int down = i < M_SIZE - 1 ? sGrid[i + 1][j] : Integer.MAX_VALUE;
                        int left = j > 0 ? sGrid[i][j - 1] : Integer.MAX_VALUE;
                        int right = j < M_SIZE - 1 ? sGrid[i][j + 1] : Integer.MAX_VALUE;
                        int score = Math.min(up, Math.min(down, Math.min(left, right)));
                        sGrid[i][j] = score == Integer.MAX_VALUE ? score : score + 1;
                    }
                }
            }
        }

        System.out.println(sGrid[0][0]);
    }

    private char[][] initilizeGrid(int limit) {
        char[][] grid = new char[M_SIZE][M_SIZE];
        List<String> coordinates = InputFormatter.formatLines("input/2024/Day18").stream().toList();
        IntStream.range(0, limit).mapToObj(index -> coordinates.get(index).split(",")).forEach(arr -> {
            int row = Integer.parseInt(arr[1]);
            int column = Integer.parseInt(arr[0]);
            grid[row][column] = '#';
        });
        return grid;
    }

    private int[][] initilizeScoreGrid() {
        int[][] grid = new int[M_SIZE][M_SIZE];
        for (int i = M_SIZE - 1; i >= 0; i--) {
            for (int j = M_SIZE - 1; j >= 0; j--) {
                grid[i][j] = Integer.MAX_VALUE;
            }
        }
        grid[M_SIZE - 1][M_SIZE - 1] = 0;
        return grid;
    }

    public static void main(String[] args) {
        Day18.M_SIZE = 71;
        Day18 d = new Day18();
        d.solution(1024);
        //part 2 answer 2903, when solution while loop runs infinitely.
    }
}
