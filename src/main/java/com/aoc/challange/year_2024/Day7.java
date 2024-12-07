package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @see <a href="https://adventofcode.com/2024/day/7">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/7/input">input</a>
 */
public class Day7 {
    private Map<Long, List<Integer>> resultAndOperands = new HashMap<>();

    private void readInput() {
        resultAndOperands = InputFormatter.formatLines("input/2024/Day7").stream().map(s -> s.split(":")).collect(Collectors.toMap(arr -> Long.valueOf(arr[0]), arr -> convert(arr[1])));
    }

    private List<Integer> convert(String s) {
        String[] s1 = s.split(" ");
        return Arrays.stream(s1).filter(cs -> !cs.isEmpty()).map(Integer::parseInt).toList();
    }

    private void solutionPart1() {

        long sum = resultAndOperands.keySet().parallelStream().map(key -> {
            boolean r = evaluateExpression(0, resultAndOperands.get(key), key, 0);
            return r ? key : 0;
        }).mapToLong(Long::valueOf).sum();

        System.out.println("part1: "+sum);
    }

    private void solutionPart2() {

        long sum = resultAndOperands.keySet().parallelStream().map(key -> {
            boolean r = evaluateExpression1(0, resultAndOperands.get(key), key, 0);
            return r ? key : 0;
        }).mapToLong(Long::valueOf).sum();

        System.out.println("part2: "+sum);
    }

    private boolean evaluateExpression(int currentIndex, List<Integer> operands, long expectedResult, long previousResult) {
        if (currentIndex == operands.size() - 1) {
            long r1 = previousResult + operands.get(currentIndex);
            long r2 = previousResult * operands.get(currentIndex);
            return r1 == expectedResult || r2 == expectedResult;
        } else {
            if (currentIndex == 0) {
                return evaluateExpression(currentIndex + 1, operands, expectedResult, operands.get(currentIndex));
            } else {
                boolean r1 = evaluateExpression(currentIndex + 1, operands, expectedResult, (previousResult + operands.get(currentIndex)));
                boolean r2 = evaluateExpression(currentIndex + 1, operands, expectedResult, (previousResult * operands.get(currentIndex)));
                return r1 || r2;
            }
        }
    }

    private boolean evaluateExpression1(int currentIndex, List<Integer> operands, long expectedResult, long previousResult) {
        if (currentIndex == operands.size() - 1) {
            long r1 = previousResult + operands.get(currentIndex);
            long r2 = previousResult * operands.get(currentIndex);
            long r3 = Long.parseLong(String.valueOf(previousResult).concat(String.valueOf(operands.get(currentIndex))));
            return r1 == expectedResult || r2 == expectedResult || r3 == expectedResult;
        } else {
            if (currentIndex == 0) {
                return evaluateExpression1(currentIndex + 1, operands, expectedResult, operands.get(currentIndex));
            } else {
                boolean r1 = evaluateExpression1(currentIndex + 1, operands, expectedResult, (previousResult + operands.get(currentIndex)));
                boolean r2 = evaluateExpression1(currentIndex + 1, operands, expectedResult, (previousResult * operands.get(currentIndex)));
                long concatResult = Long.parseLong(String.valueOf(previousResult).concat(String.valueOf(operands.get(currentIndex))));
                boolean r3 = evaluateExpression1(currentIndex + 1, operands, expectedResult, concatResult);
                return r1 || r2 || r3;
            }
        }
    }
    public static void main(String[] args) {
        Day7 d = new Day7();
        d.readInput();
        d.solutionPart1();
        d.solutionPart2();
    }
}
