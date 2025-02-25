package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2024/day/17">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/17/input">input</a>
 * @see <a href="https://www.youtube.com/watch?v=y-UPxMAh2N8">Solution credit to HyperNeutrino algo</a>
 */
public class Day17 {
    private int[] program;
    private long[] registers;

    private void initializeInputs() {
        List<String> l = InputFormatter.formatLines("input/2024/Day17").stream().toList();
        registers = Arrays.stream(l.get(0).replace("Register ", "").split(",")).map(val -> val.split(":")[1]).mapToLong(Long::valueOf).toArray();
        String s = l.get(1).replace("Program ", "").replace(",", "");
        program = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            program[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }
    }

    private long getComboVal(int val, long A, long B, long C) {
        if (val >= 0 && val <= 3) {
            return val;
        }
        if (val == 4) {
            return A;
        }
        if (val == 5) {
            return B;
        }
        if (val == 6) {
            return C;
        }
        throw new RuntimeException("invalid val " + val);
    }

    private List<Long> solutionPart1() {
        List<Long> out = new ArrayList<>(1);
        int index = 0;
        long A = registers[0];
        long B = registers[1];
        long C = registers[2];

        while (index < program.length - 1) {
            int opCode = program[index];
            int operand = program[index + 1];
            if (opCode == 0) {
                A = A >> getComboVal(operand, A, B, C);
            } else if (opCode == 1) {
                B = (B ^ operand);
            } else if (opCode == 2) {
                B = getComboVal(operand, A, B, C) % 8;
            } else if (opCode == 3) {
                if (A != 0) {
                    index = operand;
                    continue;
                }
            } else if (opCode == 4) {
                B = B ^ C;
            } else if (opCode == 5) {
                out.add((getComboVal(operand, A, B, C) % 8));
            } else if (opCode == 6) {
                B = A >> getComboVal(operand, A, B, C);
            } else if (opCode == 7) {
                C = A >> getComboVal(operand, A, B, C);
            } else {
                throw new RuntimeException("invalid program " + opCode);
            }
            index = index + 2;
        }
        return out;
    }

    private long solutionPart2(int index, long ans) {
        long out = -1;
        if (index == -1) {
            return ans;
        }
        //for loop handles instruction (2,4)
        for (int b = 0; b < 8; b++) {
            long B = b;
            long A = (ans << 3) + B; //(0,3)
            B = B ^ 1; //(1,1)
            long C = A >> B; //(7,5)
            B = B ^ 5; //(1,5)
            B = B ^ C; //(4,3)
            long o = (B % 8); //(5,5)
            if (o == program[index]) {
                long r1 = solutionPart2(index - 1, A);
                if (r1 != -1) {
                    out = r1;
                    break;
                }
            }
        }
        return out;
    }

    private void solution() {
        initializeInputs();
        for (Long l : solutionPart1()) {
            System.out.printf("%s, ", l);
        }
        System.out.println();
        long l = solutionPart2(program.length - 1, 0);
        System.out.println("min val for A: " + l);
    }

    public static void main(String[] args) {
        Day17 d = new Day17();
        d.solution();
    }

}
