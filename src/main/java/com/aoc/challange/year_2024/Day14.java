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
    private static final int timeInSeconds = 100;

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

    private void solutionPart1() {
        List<Robot> robots = getRobotsUpdatedLocationsAfterTime();
        int robotsPresenceFactor = getRobotsPresenceFactor(robots);
        System.out.println(robotsPresenceFactor);
    }

    private List<Robot> getRobotsUpdatedLocationsAfterTime() {
        int t = 0;
        List<Robot> robots = getRobots();
        while (t <= Day14.timeInSeconds) {
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
            }
            robots = updatedRobots;
            t++;
        }
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
        System.out.printf("q1,q2,q3,q4: %s,%s,%s,%s%n", q1, q2, q3, q4);
        return q1 * q2 * q3 * q4;
    }
    public static void main(String[] args) {
        Day14 d = new Day14();
        d.solutionPart1();
    }
}
