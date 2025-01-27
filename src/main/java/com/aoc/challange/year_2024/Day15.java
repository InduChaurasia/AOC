package com.aoc.challange.year_2024;


import com.aoc.challange.utils.InputFormatter;

import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2024/day/15">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/15/input">input</a>
 */
public class Day15 {

    private record RobotPosition(int r, int c) {
    }

    private String[][] getRoomMap() {
        List<String> l = InputFormatter.formatLines("input/2024/Day15_1").stream().toList();
        int r = l.size();
        int c = l.get(0).length();
        String[][] map = new String[r][c];
        for (int i = 0; i < r; i++) {
            map[i] = l.get(i).split("");
        }
        return map;
    }

    private String getMovement() {
        List<String> l = InputFormatter.formatLines("input/2024/Day15_2").stream().toList();
        StringBuilder sb = new StringBuilder();
        for (String s : l) {
            sb.append(s);
        }
        return sb.toString();
    }

    private void solutionPart1() {
        String[][] map = getRoomMap();
        RobotPosition p = findRobotPosition(map);
        String moves = getMovement();
        print(map);
        for (int l = 0; l < moves.length(); l++) {
            char c = moves.charAt(l);
            if (c == '>') {
                p = moveRight(map, p);

            } else if (c == '<') {
                p = moveLeft(map, p);

            } else if (c == '^') {
                p = moveUp(map, p);

            } else if (c == 'v') {
                p = moveDown(map, p);
            }
        }
        System.out.println("after moves " + moves);
        print(map);

    }

    private void print(String[][] map) {
        int r = map.length;
        int c = map[0].length;
        int sum = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(map[i][j]);
                if (map[i][j].equals("O")) {
                    sum += i * 100 + j;
                }
            }
            System.out.println();
        }
        System.out.println("gps: " + sum);
    }

    private RobotPosition findRobotPosition(String[][] map) {
        int r = map.length;
        int c = map[0].length;
        RobotPosition p = null;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j].equals("@")) {
                    p = new RobotPosition(i, j);
                    break;
                }
            }
            if (p != null) {
                break;
            }
        }
        return p;
    }

    private RobotPosition moveDown(String[][] map, RobotPosition p) {
        int r = map.length;
        int rr = p.r;
        int rc = p.c;
        if (rr < r - 1) {
            String down = map[rr + 1][rc];
            if (down.equals(".")) {
                map[rr][rc] = ".";
                map[rr + 1][rc] = "@";
                rr = rr + 1;
            } else if (down.equals("O")) {
                int rr1 = rr + 1;
                while (rr1 < r - 1) {
                    rr1++;
                    if (map[rr1][rc].equals(".") || map[rr1][rc].equals("#")) {
                        break;
                    }
                }
                if (map[rr1][rc].equals(".")) {
                    map[rr1][rc] = "O";
                    map[rr][rc] = ".";
                    map[rr + 1][rc] = "@";
                    rr = rr + 1;
                }
            }
        }
        return new RobotPosition(rr, rc);
    }

    private RobotPosition moveUp(String[][] map, RobotPosition p) {
        int rr = p.r;
        int rc = p.c;
        if (rr > 0) {
            String up = map[rr - 1][rc];
            if (up.equals(".")) {
                map[rr][rc] = ".";
                map[rr - 1][rc] = "@";
                rr = rr - 1;
            } else if (up.equals("O")) {
                int rr1 = rr - 1;
                while (rr1 > 0) {
                    rr1--;
                    if (map[rr1][rc].equals(".") || map[rr1][rc].equals("#")) {
                        break;
                    }
                }
                if (map[rr1][rc].equals(".")) {
                    map[rr1][rc] = "O";
                    map[rr][rc] = ".";
                    map[rr - 1][rc] = "@";
                    rr = rr - 1;
                }
            }
        }
        return new RobotPosition(rr, rc);
    }

    private RobotPosition moveLeft(String[][] map, RobotPosition p) {
        int rr = p.r;
        int rc = p.c;
        if (rc > 0) {
            String previous = map[rr][rc - 1];
            if (previous.equals(".")) {
                map[rr][rc] = ".";
                map[rr][rc - 1] = "@";
                rc = rc - 1;
            } else if (previous.equals("O")) {
                int rc1 = rc - 1;
                while (rc1 > 0) {
                    rc1--;
                    if (map[rr][rc1].equals(".") || map[rr][rc1].equals("#")) {
                        break;
                    }
                }
                if (map[rr][rc1].equals(".")) {
                    map[rr][rc1] = "O";
                    map[rr][rc] = ".";
                    map[rr][rc - 1] = "@";
                    rc = rc - 1;
                }
            }
        }
        return new RobotPosition(rr, rc);
    }

    private RobotPosition moveRight(String[][] map, RobotPosition p) {
        int c = map[0].length;
        int rr = p.r;
        int rc = p.c;
        if (rc < c - 1) {
            String next = map[rr][rc + 1];
            if (next.equals(".")) {
                map[rr][rc] = ".";
                map[rr][rc + 1] = "@";
                rc = rc + 1;
            } else if (next.equals("O")) {
                int rc1 = rc + 1;
                while (rc1 < c - 1) {
                    rc1++;
                    if (map[rr][rc1].equals(".") || map[rr][rc1].equals("#")) {
                        break;
                    }
                }
                if (map[rr][rc1].equals(".")) {
                    map[rr][rc1] = "O";
                    map[rr][rc] = ".";
                    map[rr][rc + 1] = "@";
                    rc = rc + 1;
                }
            }
        }
        return new RobotPosition(rr, rc);
    }


    public static void main(String[] args) {

        Day15 d = new Day15();
        d.solutionPart1();
    }

}
