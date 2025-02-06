package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/19/problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/19/input">input</a>
 */
public class Day19 {

    List<String> towels = new ArrayList<>();
    List<String> designs = new ArrayList<>();
    Map<String, Boolean> possibilityCache = new HashMap<>();
    Map<String, Long> combinationCache = new HashMap<>();


    private void initialiseInputs() {
        List<String> inputs = new ArrayList<>(InputFormatter.formatLines("input/2024/Day19").stream().toList());
        towels = Arrays.stream(inputs.get(0).split(",")).map(String::trim).toList();
        inputs.remove(0);
        designs = inputs;
    }

    private void solutionPart1() {
        int count = 0;
        for (String design : designs) {
            possibilityCache = new HashMap<>();
            possibilityCache.put("", true);
            if (isDesignPossible(design)) {
                count++;
            }
        }
        System.out.println("Designs possible: " + count);
    }

    private boolean isDesignPossible(String design) {
        if (!possibilityCache.containsKey(design)) {
            boolean isPossible = false;
            for (String towel : towels) {
                if (design.startsWith(towel)) {
                    if (isDesignPossible(design.substring(towel.length()))) {
                        isPossible = true;
                        break;
                    }
                }
            }
            possibilityCache.put(design, isPossible);
        }
        return possibilityCache.getOrDefault(design, false);
    }

    private void solutionPart2() {
        long combinationsSum = 0;
        for (String design : designs) {
            combinationCache = new HashMap<>();
            combinationCache.put("", 1L);
            combinationsSum += countTowelCombinations(design);
        }
        System.out.println("Sum of possible combinations " + combinationsSum);
    }

    private long countTowelCombinations(String design) {
        if (!combinationCache.containsKey(design)) {
            long combinations = 0;
            for (String towel : towels) {
                if (design.startsWith(towel)) {
                    combinations += countTowelCombinations(design.substring(towel.length()));
                }
            }
            combinationCache.put(design, combinations);
        }
        return combinationCache.get(design);
    }


    private void solution() {
        initialiseInputs();
        solutionPart1();
        solutionPart2();
    }

    public static void main(String[] args) {
        Day19 d = new Day19();
        d.solution();

    }
}
