package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.List;

public class Day15Part2 {
    private record RobotPosition(int r, int c) {
    }

    private String[][] getDoubledRoomMap() {
        List<String> l = InputFormatter.formatLines("input/2024/Day15_1").stream().toList();
        int r = l.size();
        int c = l.get(0).length() * 2;
        String[][] map = new String[r][c];
        for (int i = 0; i < r; i++) {
            String s = l.get(i);
            int k = 0;
            for (int j = 0; j < c; j++) {
                if (s.charAt(k) == '#') {
                    map[i][j] = "#";
                    map[i][j + 1] = "#";
                } else if (s.charAt(k) == '.') {
                    map[i][j] = ".";
                    map[i][j + 1] = ".";
                } else if (s.charAt(k) == 'O') {
                    map[i][j] = "[";
                    map[i][j + 1] = "]";
                } else {
                    map[i][j] = "@";
                    map[i][j + 1] = ".";
                }
                k++;
                j++;
            }
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

    private void solutionPart2() {
        String[][] map = getDoubledRoomMap();
        RobotPosition p = findRobotPosition(map);
        String moves = getMovement();
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
            } else if (next.equals("[")) {
                int rc1 = rc + 1;
                while (rc1 < c - 1) {
                    rc1++;
                    if (map[rr][rc1].equals(".") || map[rr][rc1].equals("#")) {
                        break;
                    }
                }
                if (map[rr][rc1].equals(".")) {
                    int k = rc1;
                    String s = "]";
                    while (k > rc + 1) {
                        map[rr][k] = s;
                        k--;
                        s = s.equals("]") ? "[" : "]";
                    }
                    map[rr][rc] = ".";
                    map[rr][rc + 1] = "@";
                    rc = rc + 1;
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
            } else if (previous.equals("]")) {
                int rc1 = rc - 1;
                while (rc1 > 0) {
                    rc1--;
                    if (map[rr][rc1].equals(".") || map[rr][rc1].equals("#")) {
                        break;
                    }
                }
                if (map[rr][rc1].equals(".")) {
                    int k = rc1;
                    String s = "[";
                    while (k < rc + 1) {
                        map[rr][k] = s;
                        k++;
                        s = s.equals("]") ? "[" : "]";
                    }
                    map[rr][rc] = ".";
                    map[rr][rc - 1] = "@";
                    rc = rc - 1;
                }
            }
        }
        return new RobotPosition(rr, rc);
    }

    private RobotPosition moveUp(String[][] map, RobotPosition p) {
        int rr = p.r;
        int rc = p.c;
        if (rr > 0) {
            if (map[rr - 1][rc].equals(".")) {
                map[rr - 1][rc] = "@";
                map[rr][rc] = ".";
                rr = rr - 1;
            } else if ((map[rr - 1][rc].equals("[") || map[rr - 1][rc].equals("]")) && canMoveUp(rr - 1, rc, map)) {
                moveUp(rr - 1, rc, map);
                map[rr - 1][rc] = "@";
                map[rr][rc] = ".";
                rr = rr - 1;
            }
        }
        return new RobotPosition(rr, rc);
    }


    private boolean canMoveUp(int r, int c, String[][] m) {
        if (r > 0) {
            if (m[r][c].equals("]")) {
                if (m[r - 1][c].equals(".") && m[r - 1][c - 1].equals(".")) {
                    return true;
                } else if (m[r - 1][c].equals("#") || m[r - 1][c - 1].equals("#")) {
                    return false;
                } else {
                    return canMoveUp(r - 1, c, m) && canMoveUp(r - 1, c - 1, m);
                }

            } else if (m[r][c].equals("[")) {
                if (m[r - 1][c].equals(".") && m[r - 1][c + 1].equals(".")) {
                    return true;
                } else if (m[r - 1][c].equals("#") || m[r - 1][c + 1].equals("#")) {
                    return false;
                } else {
                    return canMoveUp(r - 1, c, m) && canMoveUp(r - 1, c + 1, m);
                }
            } else {
                return m[r][c].equals(".");
            }
        } else {
            return false;
        }
    }

    private void moveUp(int r, int c, String[][] m) {
        if (r > 0 && (m[r][c].equals("]") || m[r][c].equals("["))) {
            if (m[r][c].equals("]")) {
                if (!m[r - 1][c].equals(".") || !m[r - 1][c - 1].equals(".")) {
                    moveUp(r - 1, c, m);
                    moveUp(r - 1, c - 1, m);
                }
                m[r][c] = ".";
                m[r][c - 1] = ".";
                m[r - 1][c] = "]";
                m[r - 1][c - 1] = "[";
            } else if (m[r][c].equals("[")) {
                if (!m[r - 1][c].equals(".") || !m[r - 1][c + 1].equals(".")) {
                    moveUp(r - 1, c, m);
                    moveUp(r - 1, c + 1, m);
                }
                m[r][c] = ".";
                m[r][c + 1] = ".";
                m[r - 1][c] = "[";
                m[r - 1][c + 1] = "]";
            }
        }
    }

    private RobotPosition moveDown(String[][] map, RobotPosition p) {
        int r = map.length;
        int rr = p.r;
        int rc = p.c;
        if (rr < r - 1) {
            if (map[rr + 1][rc].equals(".")) {
                map[rr + 1][rc] = "@";
                map[rr][rc] = ".";
                rr = rr + 1;
            } else if ((map[rr + 1][rc].equals("]") || map[rr + 1][rc].equals("[")) && canMoveDown(rr + 1, rc, map)) {
                moveDown(rr + 1, rc, map);
                map[rr + 1][rc] = "@";
                map[rr][rc] = ".";
                rr = rr + 1;
            }
        }
        return new RobotPosition(rr, rc);
    }

    private boolean canMoveDown(int r, int c, String[][] m) {
        if (r < m.length - 1) {
            if (m[r][c].equals("]")) {
                if (m[r + 1][c].equals(".") && m[r + 1][c - 1].equals(".")) {
                    return true;
                } else if (m[r + 1][c].equals("#") || m[r + 1][c - 1].equals("#")) {
                    return false;
                } else {
                    return canMoveDown(r + 1, c, m) && canMoveDown(r + 1, c - 1, m);
                }
            } else if (m[r][c].equals("[")) {
                if (m[r + 1][c].equals(".") && m[r + 1][c + 1].equals(".")) {
                    return true;
                } else if (m[r + 1][c].equals("#") || m[r + 1][c + 1].equals("#")) {
                    return false;
                } else {
                    return canMoveDown(r + 1, c, m) && canMoveDown(r + 1, c + 1, m);
                }
            } else return m[r][c].equals(".");
        } else {
            return false;
        }
    }

    private void moveDown(int r, int c, String[][] m) {
        if (r < m.length - 1 && (m[r][c].equals("]") || m[r][c].equals("["))) {
            if (m[r][c].equals("]")) {
                if (!m[r + 1][c].equals(".") || !m[r + 1][c - 1].equals(".")) {
                    moveDown(r + 1, c, m);
                    moveDown(r + 1, c - 1, m);
                }
                m[r][c] = ".";
                m[r][c - 1] = ".";
                m[r + 1][c] = "]";
                m[r + 1][c - 1] = "[";
            } else if (m[r][c].equals("[")) {
                if (!m[r + 1][c].equals(".") || !m[r + 1][c + 1].equals(".")) {
                    moveDown(r + 1, c, m);
                    moveDown(r + 1, c + 1, m);
                }
                m[r][c] = ".";
                m[r][c + 1] = ".";
                m[r + 1][c] = "[";
                m[r + 1][c + 1] = "]";
            }
        }
    }

    private void print(String[][] map) {
        int r = map.length;
        int c = map[0].length;
        int sum = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(map[i][j]);
                if (map[i][j].equals("[")) {
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

    public static void main(String[] args) {

        Day15Part2 d = new Day15Part2();
        d.print(d.getDoubledRoomMap());
        d.solutionPart2();

    }

}
