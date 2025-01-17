package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;


/**
 * @see <a href="https://adventofcode.com/2024/day/5">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/5/input">input</a>
 */
public class Day6 {
    private GuardLocation igl;
    private final char[][] pathMatrix = getPathMatrix();
    private Map<String, Set<DIRECTION>> traversedPath = new HashMap<>();

    private enum DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private static class GuardLocation {
        private final int cr;
        private final int cc;
        private final DIRECTION direction;

        public GuardLocation(int cr, int cc, DIRECTION d) {
            this.cr = cr;
            this.cc = cc;
            this.direction = d;
        }

        public int getCr() {
            return cr;
        }

        public int getCc() {
            return cc;
        }

        public DIRECTION getDirection() {
            return direction;
        }
    }

    private char[][] getPathMatrix() {
        List<String> lines = InputFormatter.formatLines("input/2024/Day6");
        int r = lines.size();
        int c = lines.get(0).length();
        char[][] m = new char[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] = lines.get(i).charAt(j);
                if (m[i][j] == '^') {
                    igl = new GuardLocation(i, j, DIRECTION.UP);
                }
            }
        }

        return m;
    }

    private GuardLocation moveInDirection(GuardLocation gl) {
        int cgr = gl.getCr();
        int cgc = gl.getCc();
        switch (gl.getDirection()) {
            case UP: {
                cgr--;
                break;
            }
            case DOWN: {
                cgr++;
                break;
            }
            case LEFT: {
                cgc--;
                break;
            }
            case RIGHT: {
                cgc++;
                break;
            }
            default: {
                throw new RuntimeException("invalid direction");
            }
        }
        return new GuardLocation(cgr, cgc, gl.getDirection());
    }

    private GuardLocation changeDirection(GuardLocation gl) {
        int cgr = gl.getCr();
        int cgc = gl.getCc();
        DIRECTION cd;
        switch (gl.getDirection()) {
            case UP: {
                cgr++;
                cd = DIRECTION.RIGHT;
                break;
            }
            case DOWN: {
                cgr--;
                cd = DIRECTION.LEFT;
                break;
            }
            case LEFT: {
                cgc++;
                cd = DIRECTION.UP;
                break;
            }
            case RIGHT: {
                cgc--;
                cd = DIRECTION.DOWN;
                break;
            }
            default: {
                throw new RuntimeException("invalid direction");
            }
        }

        return new GuardLocation(cgr, cgc, cd);
    }

    private void updateTraversedPath(GuardLocation gl) {
        int cgr = gl.getCr();
        int cgc = gl.getCc();
        String key = String.format("%s,%s", cgr, cgc);
        Set<DIRECTION> vds = traversedPath.getOrDefault(key, new HashSet<>());
        vds.add(gl.getDirection());
        traversedPath.put(key, vds);
    }

    /**
     * @see <a href="file:resources/input/2024/Day4">/resources/input/2024/Day6 for input</a>
     */
    private void findGuardPath() {
        traversedPath = new HashMap<>();
        int r = pathMatrix.length;
        int c = pathMatrix[0].length;
        int cgr = igl.getCr();
        int cgc = igl.getCc();
        DIRECTION cd = igl.getDirection();
        while (cgr >= 0 && cgr < r && cgc >= 0 && cgc < c) {
            GuardLocation cgl = new GuardLocation(cgr, cgc, cd);
            if (pathMatrix[cgr][cgc] != '#') {
                updateTraversedPath(cgl);
                GuardLocation ugl = moveInDirection(cgl);
                cgr = ugl.getCr();
                cgc = ugl.getCc();

            } else {
                GuardLocation ugl = changeDirection(cgl);
                cgr = ugl.getCr();
                cgc = ugl.getCc();
                cd = ugl.getDirection();
            }

        }
        System.out.println(traversedPath.size());
    }

    public static void main(String[] args) {
        Day6 d = new Day6();
        System.out.println("Part1 output: ");
        d.findGuardPath();
    }
}
