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
    private static int INPUT_SIZE;

    private int solution(int limit) {
        char[][] grid = initilizeGrid(limit);
        int[][] sGrid = initilizeScoreGrid();
        int cellsWithValidScore = 1;
        while (cellsWithValidScore != 0) {
            cellsWithValidScore = 0;
            for (int i = M_SIZE - 1; i >= 0; i--) {
                for (int j = M_SIZE - 1; j >= 0; j--) {
                    if (grid[i][j] != '#' && sGrid[i][j] == Integer.MAX_VALUE) {
                        int up = i > 0 ? sGrid[i - 1][j] : Integer.MAX_VALUE;
                        int down = i < M_SIZE - 1 ? sGrid[i + 1][j] : Integer.MAX_VALUE;
                        int left = j > 0 ? sGrid[i][j - 1] : Integer.MAX_VALUE;
                        int right = j < M_SIZE - 1 ? sGrid[i][j + 1] : Integer.MAX_VALUE;
                        int score = Math.min(up, Math.min(down, Math.min(left, right)));
                        sGrid[i][j] = score == Integer.MAX_VALUE ? score : score + 1;
                        if (score != Integer.MAX_VALUE) {
                            cellsWithValidScore++;
                        }
                    }
                }
            }
        }
        return sGrid[0][0];
    }

    private char[][] initilizeGrid(int limit) {
        char[][] grid = new char[M_SIZE][M_SIZE];
        List<String> coordinates = InputFormatter.formatLines("input/2024/Day18").stream().toList();
        INPUT_SIZE = coordinates.size();
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

    private void solutionPart1() {
        M_SIZE = 71;
        int score = solution(1024);
        if (score != Integer.MAX_VALUE) {
            System.out.println("Min path score: " + score + " for limit: " + 1024);
        }
    }

    private void solutionPart2() {
        M_SIZE = 71;
        for (int i = 0; i < INPUT_SIZE; i++) {
            int score = solution(i);
            if (score == Integer.MAX_VALUE) {
                System.out.println("No path after limit :" + i);
                break;
            }
        }
    }

    public static void main(String[] args) {
        Day18 d = new Day18();
        d.solutionPart1();
        d.solutionPart2();
    }
}
