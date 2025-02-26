package com.aoc.challange.year_2024;


import com.aoc.challange.utils.InputFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2024/day/14">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/14/input">input</a>
 */
public class Day14 {

    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;
    private static int timeInSeconds = 100;

    private static int lowestFactor = Integer.MAX_VALUE;

    private record Robot(int px, int py, int vx, int vy) {
    }

    private static List<Robot> getRobots() {
        List<String> l = InputFormatter.formatLines("input/2024/Day14").stream().toList();
        List<Robot> robots = new ArrayList<>(l.size());
        for (String s : l) {
            String[] parts = s.split("v=");
            String[] pParts = parts[0].trim().replace("p=", "").split(",");
            String[] vParts = parts[1].trim().split(",");
            Robot r = new Robot(Integer.parseInt(pParts[0]), Integer.parseInt(pParts[1]), Integer.parseInt(vParts[0]), Integer.parseInt(vParts[1]));
            robots.add(r);
        }
        return robots;
    }

    private List<Robot> getRobotsUpdatedLocationsAfterTime() {
        int t = 0;
        int time = 0;
        List<Robot> robots = getRobots();
        while (t <= timeInSeconds) {
            int[][] map = new int[HEIGHT][WIDTH];
            List<Robot> updatedRobots = new ArrayList<>();
            for (Robot robot : robots) {
                int x = robot.px;
                int y = robot.py;
                if (t != 0) {
                    x = x + robot.vx;
                    y = y + robot.vy;
                }
                if (x < 0) {
                    x = WIDTH + x;
                } else if (x >= WIDTH) {
                    x = x - WIDTH;
                }
                if (y < 0) {

                    y = HEIGHT + y;

                } else if (y >= HEIGHT) {
                    y = y - HEIGHT;
                }
                updatedRobots.add(new Robot(x, y, robot.vx, robot.vy));
                map[y][x] = 1;
            }
            robots = updatedRobots;
            int robotsPresenceFactor = getRobotsPresenceFactor(robots);

            if (robotsPresenceFactor < lowestFactor) {
                lowestFactor = robotsPresenceFactor;
                time = t;
                //because at 6355 time presence factor was lowest
                if (t == 6355) {
                    print(map);
                }
            }
            t++;
        }
        System.out.printf("lowest presence factor fount at %s sec for range (0-%s)%n", time, timeInSeconds);
        return robots;
    }

    private int getRobotsPresenceFactor(List<Robot> robots) {

        int lx = WIDTH / 2;
        int ly = HEIGHT / 2;

        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (Robot robot : robots) {
            if (robot.px < lx && robot.py < ly) {
                q1 += 1;
            } else if (robot.px > lx && robot.py < ly) {
                q2 += 1;
            } else if (robot.px < lx && robot.py > ly) {
                q3 += 1;
            } else if (robot.px > lx && robot.py > ly) {
                q4 += 1;
            }

        }
        // System.out.printf("q1,q2,q3,q4: %s,%s,%s,%s%n", q1, q2, q3, q4);
        return q1 * q2 * q3 * q4;
    }

    private void print(int[][] map) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {

                if (map[i][j] == 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private void solutionPart1() {
        System.out.println("part1->");
        List<Robot> robots = getRobotsUpdatedLocationsAfterTime();
        int robotsPresenceFactor = getRobotsPresenceFactor(robots);
        System.out.printf("presence factor after time %s : %s%n", timeInSeconds, robotsPresenceFactor);
    }

    private void solutionPart2() {
        System.out.println("part2->");
        timeInSeconds = 10000;
        getRobotsUpdatedLocationsAfterTime();
    }

    public static void main(String[] args) {
        Day14 d = new Day14();
        d.solutionPart1();
        d.solutionPart2();
    }
}
