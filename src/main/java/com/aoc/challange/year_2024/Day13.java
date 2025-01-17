package com.aoc.challange.year_2024;


import com.aoc.challange.utils.InputFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2024/day/13">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/13/input">input</a>
 */
public class Day13 {

    private record Prize(float X, float Y, ClawMachine cm) {
    }

    private static class ClawMachine {
        float ax;
        float ay;
        float bx;
        float by;

        public ClawMachine(float ax, float ay, float bx, float by) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
        }
    }

    private List<Prize> readInput() {
        List<Prize> input = new ArrayList<>();
        List<String> l = InputFormatter.formatLines("input/2024/Day13").stream().toList();
        float ax = -1, ay = -1, bx = -1, by = -1;
        for (String s : l) {
            if (!s.isEmpty()) {
                if (s.startsWith("Button A:")) {
                    String[] ain = s.split(",");
                    ax = Float.parseFloat(ain[0].trim().replace("Button A: X+", ""));
                    ay = Float.parseFloat(ain[1].trim().replace("Y+", ""));
                } else if (s.startsWith("Button B:")) {
                    String[] bin = s.split(",");
                    bx = Float.parseFloat(bin[0].trim().replace("Button B: X+", ""));
                    by = Float.parseFloat(bin[1].trim().replace("Y+", ""));
                } else if (s.startsWith("Prize:")) {
                    String[] pin = s.split(",");
                    float X = Float.parseFloat(pin[0].trim().replace("Prize: X=", ""));
                    float Y = Float.parseFloat(pin[1].trim().replace("Y=", ""));
                    if (ax != -1 && ay != -1 && bx != -1 && by != -1) {
                        ClawMachine cm = new ClawMachine(ax, ay, bx, by);
                        Prize p = new Prize(X, Y, cm);
                        input.add(p);
                        ax = -1;
                        ay = -1;
                        bx = -1;
                        by = -1;
                    }
                }
            }
        }
        return input;
    }

    private void solution(boolean isPart1) {

        double add = isPart1 ? 0d : 10000000000000d;

        int coinA = 3;
        int coinB = 1;
        double cost = 0;
        for (Prize prize : readInput()) {

            ClawMachine m = prize.cm;
            double bn = ((prize.Y + add) * m.ax - (prize.X + add) * m.ay) / (m.ax * m.by - m.ay * m.bx);
            double an = ((prize.X + add) - bn * m.bx) / m.ax;

            if (an % 1 == 0 && bn % 1 == 0 && (!isPart1 || ((long) an <= 100 && (long) bn <= 100))) {
                System.out.println("Hit A " + an + " times, Hit B " + bn + " times to reach (" + prize.X + "," + prize.Y + ")");
                cost += an * coinA + bn * coinB;
            }

        }
        System.out.println((long) cost);
    }


    public static void main(String[] args) {

       /* ClawMachine m = new ClawMachine(ax, ay, bx, by);
        float bn = (Y * m.ax - X * m.ay) / (m.ax * m.by - m.ay * m.bx);
        float an = (X - bn * m.bx) / m.ax;*/

        Day13 d = new Day13();
        d.solution(true);
        d.solution(false);

        // System.out.println(BigDecimal.valueOf(10000000000000f*100).compareTo(BigDecimal.valueOf(Float.MAX_VALUE)));
    }
}
