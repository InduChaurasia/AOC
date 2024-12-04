package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @see <a href="https://adventofcode.com/2024/day/4">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/4/input">input</a>
 */
public class Day4 {
    private static final String searchInput = "XMAS";

    private static char[][] getInput() {
        List<String> lines = InputFormatter.formatLines("input/2024/Day4");
        int rows = lines.size();
        int columns = lines.get(0).length();
        char[][] searchText = new char[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                searchText[row][column] = lines.get(row).charAt(column);
            }
        }
        return searchText;
    }

    /**
     * @see <a href="file:resources/input/2024/Day4">/resources/input/2024/Day4 for input</a>
     */
    private void solutionPart1() {
        char[][] searchText = getInput();
        Set<String> matches = new HashSet<>();
        int rows = searchText.length;
        int columns = searchText[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (checkForward(searchText, r, c)) {
                    matches.add(formatMatch(r, c, "F"));
                }
                if (checkBackward(searchText, r, c)) {
                    matches.add(formatMatch(r, c, "B"));
                }
                if (checkUp(searchText, r, c)) {
                    matches.add(formatMatch(r, c, "U"));
                }
                if (checkDown(searchText, r, c)) {
                    matches.add(formatMatch(r, c, "D"));
                }
                if (checkRightDiagonalDown(searchText, r, c)) {
                    matches.add(formatMatch(r, c, "RD"));
                }
                if (checkLeftDiagonalDown(searchText, r, c)) {
                    matches.add(formatMatch(r, c, "LD"));
                }
                if (checkRightDiagonalUp(searchText, r, c)) {
                    matches.add(formatMatch(r, c, "RU"));
                }
                if (checkLeftDiagonalUp(searchText, r, c)) {
                    matches.add(formatMatch(r, c, "LU"));
                }
            }
        }
        System.out.println(matches.size());
    }

    private String formatMatch(int r, int c, String direction) {
        return String.format("%s-%s-%s", r, c, direction);
    }

    private boolean checkForward(char[][] searchText, int r, int c) {
        int columns = searchText[0].length;
        StringBuilder sb = new StringBuilder();
        while (c < columns && sb.length() < searchInput.length()) {
            sb.append(searchText[r][c]);
            c++;
        }
        return sb.toString().equals(searchInput);
    }

    private boolean checkBackward(char[][] searchText, int r, int c) {
        StringBuilder sb = new StringBuilder();
        while (c >= 0 && sb.length() < searchInput.length()) {
            sb.append(searchText[r][c]);
            c--;
        }
        return sb.toString().equals(searchInput);
    }

    private boolean checkDown(char[][] searchText, int r, int c) {
        int rows = searchText.length;
        StringBuilder sb = new StringBuilder();
        while (r < rows && sb.length() < searchInput.length()) {
            sb.append(searchText[r][c]);
            r++;
        }
        return sb.toString().equals(searchInput);
    }

    private boolean checkUp(char[][] searchText, int r, int c) {
        StringBuilder sb = new StringBuilder();
        while (r >= 0 && sb.length() < searchInput.length()) {
            sb.append(searchText[r][c]);
            r--;
        }
        return sb.toString().equals(searchInput);
    }

    private boolean checkLeftDiagonalDown(char[][] searchText, int r, int c) {
        int rows = searchText.length;
        int columns = searchText[0].length;
        StringBuilder sb = new StringBuilder();
        while (r < rows && c < columns && sb.length() < searchInput.length()) {
            sb.append(searchText[r][c]);
            r++;
            c++;
        }
        return sb.toString().equals(searchInput);
    }

    private boolean checkRightDiagonalDown(char[][] searchText, int r, int c) {
        int rows = searchText.length;
        StringBuilder sb = new StringBuilder();
        while (r < rows && c >= 0 && sb.length() < searchInput.length()) {
            sb.append(searchText[r][c]);
            r++;
            c--;
        }
        return sb.toString().equals(searchInput);
    }

    private boolean checkLeftDiagonalUp(char[][] searchText, int r, int c) {
        int columns = searchText[0].length;
        StringBuilder sb = new StringBuilder();
        while (r >= 0 && c < columns && sb.length() < searchInput.length()) {
            sb.append(searchText[r][c]);
            r--;
            c++;
        }
        return sb.toString().equals(searchInput);
    }

    private boolean checkRightDiagonalUp(char[][] searchText, int r, int c) {
        StringBuilder sb = new StringBuilder();
        while (r >= 0 && c >= 0 && sb.length() < searchInput.length()) {
            sb.append(searchText[r][c]);
            r--;
            c--;
        }
        return sb.toString().equals(searchInput);
    }

    /**
     * @see <a href="file:resources/input/2024/Day4">/resources/input/2024/Day4 for input</a>
     */
    private void solutionPart2() {
        char[][] searchText = getInput();
        Set<String> matches = new HashSet<>();
        int rows = searchText.length;
        int columns = searchText[0].length;
        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < columns - 1; c++) {
                if (searchText[r][c] == 'A') {
                    if (searchText[r - 1][c - 1] == 'M' && searchText[r - 1][c + 1] == 'M' && searchText[r + 1][c - 1] == 'S' && searchText[r + 1][c + 1] == 'S') {
                        matches.add(formatMatch(r, c, "M.M.A.S.S"));
                    } else if (searchText[r - 1][c - 1] == 'M' && searchText[r - 1][c + 1] == 'S' && searchText[r + 1][c - 1] == 'M' && searchText[r + 1][c + 1] == 'S') {
                        matches.add(formatMatch(r, c, "M.S.A.M.S"));
                    } else if (searchText[r - 1][c - 1] == 'S' && searchText[r - 1][c + 1] == 'S' && searchText[r + 1][c - 1] == 'M' && searchText[r + 1][c + 1] == 'M') {
                        matches.add(formatMatch(r, c, "S.S.A.M.M"));
                    } else if (searchText[r - 1][c - 1] == 'S' && searchText[r - 1][c + 1] == 'M' && searchText[r + 1][c - 1] == 'S' && searchText[r + 1][c + 1] == 'M') {
                        matches.add(formatMatch(r, c, "S.M.A.S.M"));
                    }
                }
            }
        }
        System.out.println(matches.size());
    }

    public static void main(String[] args) {
        Day4 d = new Day4();
        d.solutionPart1();
        d.solutionPart2();
    }
}
