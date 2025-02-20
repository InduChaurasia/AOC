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

    private enum DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private record GuardLocation(int cr, int cc, DIRECTION d) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GuardLocation that = (GuardLocation) o;
            return cr == that.cr && cc == that.cc && d == that.d;
        }

        @Override
        public int hashCode() {
            return Objects.hash(cr, cc, d);
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
        int cgr = gl.cr;
        int cgc = gl.cc;
        switch (gl.d) {
            case UP -> cgr--;
            case DOWN -> cgr++;
            case LEFT -> cgc--;
            case RIGHT -> cgc++;
            default -> throw new RuntimeException("invalid direction");
        }
        return new GuardLocation(cgr, cgc, gl.d);
    }

    private GuardLocation changeDirection(GuardLocation gl) {
        int cgr = gl.cr;
        int cgc = gl.cc;
        DIRECTION cd;
        switch (gl.d) {
            case UP -> {
                cgr++;
                cd = DIRECTION.RIGHT;
            }
            case DOWN -> {
                cgr--;
                cd = DIRECTION.LEFT;
            }
            case LEFT -> {
                cgc++;
                cd = DIRECTION.UP;
            }
            case RIGHT -> {
                cgc--;
                cd = DIRECTION.DOWN;
            }
            default -> throw new RuntimeException("invalid direction");
        }

        return new GuardLocation(cgr, cgc, cd);
    }

    private Set<String> findGuardPath() {
        Set<String> distinctPaths = new HashSet<>();
        List<GuardLocation> obstacles = new ArrayList<>();
        int r = pathMatrix.length;
        int c = pathMatrix[0].length;
        GuardLocation cgl = igl;
        while (cgl.cr >= 0 && cgl.cr < r && cgl.cc >= 0 && cgl.cc < c) {
            if (pathMatrix[cgl.cr][cgl.cc] != '#') {
                distinctPaths.add(String.format("%s,%s", cgl.cr, cgl.cc));
                cgl = moveInDirection(cgl);
            } else {
                if (obstacles.contains(cgl)) {
                    distinctPaths.clear();
                    break;
                }
                obstacles.add(cgl);
                cgl = changeDirection(cgl);
            }
        }
        return distinctPaths;
    }

    private Set<String> findObstaclesForLoop() {
        Set<String> guardPath = findGuardPath();
        Set<String> obstacles = new HashSet<>();
        for (String s : guardPath) {
            String[] split = s.split(",");
            int r = Integer.parseInt(split[0]);
            int c = Integer.parseInt(split[1]);
            if(igl.cr!=r || igl.cc!=c){
                pathMatrix[r][c] = '#';
                if(findGuardPath().isEmpty()){
                    obstacles.add(String.format("%s,%s",r,c));
                }
                pathMatrix[r][c] = '.';
            }
        }
        return obstacles;
    }

    private void solution() {
        Set<String> guardPath = findGuardPath();
        System.out.printf("Distinct guard path length: %s%n", guardPath.size());
        Set<String> obstacles = findObstaclesForLoop();
        System.out.printf("No of obstacles to form loop: %s%n",obstacles.size());
    }

    public static void main(String[] args) {
        Day6 d = new Day6();
        d.solution();
    }
}
