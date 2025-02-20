package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2024/day/17">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/17/input">input</a>
 */
public class Day17 {
    private long A = 0;
    private long B = 0;
    private long C = 0;
    private int[] program;

    private void initializeInputs() {
        List<String> l = InputFormatter.formatLines("input/2024/Day17").stream().toList();
        String[] registers = l.get(0).replace("Register ", "").split(",");
        A = Integer.parseInt(registers[0].split(":")[1]);
        B = Integer.parseInt(registers[1].split(":")[1]);
        C = Integer.parseInt(registers[2].split(":")[1]);
        String s = l.get(1).replace("Program ", "").replace(",", "");
        program = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            program[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }
    }

    private long getComboVal(int val) {
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

    private List<String> program(int programIndex) {
        List<String> out = new ArrayList<>(1);
        int opCode = program[programIndex];
        int operand = program[programIndex + 1];
        switch (opCode) {
            case 0 -> A = (long) (A / Math.pow(2, getComboVal(operand)));
            case 1 -> B = (B ^ operand);
            case 2 -> B = getComboVal(operand) % 8;
            case 3 -> {/*do nothing*/}
            case 4 -> B = B ^ C;
            case 5 -> {
                long val = getComboVal(operand) % 8;
                out.add(String.valueOf(val));
            }
            case 6 -> B = (long) (A / Math.pow(2, getComboVal(operand)));
            case 7 -> C = (long) (A / Math.pow(2, getComboVal(operand)));
            default -> throw new RuntimeException("invalid program " + opCode);
        }
        return out;
    }

    private List<String> runProgram() {
        List<String> outputs = new ArrayList<>();
        int programIndex = 0;
        while (programIndex < program.length) {
            int opCode = program[programIndex];
            int operand = program[programIndex + 1];
            List<String> out = program(programIndex);
            if (!out.isEmpty()) {
                outputs.add(out.get(0));
            }
            programIndex = opCode != 3 ? programIndex + 2 : A == 0 ? programIndex + 2 : operand;
        }
        return outputs;
    }

    private void solutionPart1() {
        List<String> outputs = runProgram();
        System.out.println("part1: " + String.join(",", outputs));
    }


    private void solution() {
        initializeInputs();
        solutionPart1();
    }

    public static void main(String[] args) {
        Day17 d = new Day17();
        d.solution();

    }

}
