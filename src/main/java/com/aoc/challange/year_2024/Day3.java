package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @see <a href="https://adventofcode.com/2024/day/3">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/3/input">input</a>
 */
public class Day3 {

    private static final String MUL_REGEXP = "mul\\((\\d+),(\\d+)\\)";
    private static final String DO_NOT_REGEXP = "don't\\(\\)";
    private static final String DO_REGEXP = "do\\(\\)";
    private static final Pattern MUL_PATTERN = Pattern.compile(MUL_REGEXP);
    private static final Pattern DONT_PATTERN = Pattern.compile(DO_NOT_REGEXP);
    private static final Pattern DO_PATTERN = Pattern.compile(DO_REGEXP);

    private static String getInput() {
        List<String> lines = InputFormatter.formatLines("input/2024/Day3");
        return String.join("", lines);
    }

    /**
     * @see <a href="file:resources/input/2024/Day3">/resources/input/2024/Day3 for memory</a>
     */
    private void solutionPart1() {
        String input = getInput();
        Matcher mulMatcher = MUL_PATTERN.matcher(input);

        int sum = 0;
        while (mulMatcher.find()) {
            sum += Integer.parseInt(mulMatcher.group(1)) * Integer.parseInt(mulMatcher.group(2));
        }
        System.out.println("sum part1: " + sum);
    }


    /**
     * @see <a href="file:resources/input/2024/Day3">/resources/input/2024/Day3 for memory</a>
     */
    private void solutionPart2() {
        String input = getInput();

        List<Integer> doNotPositions = new ArrayList<>();
        Matcher dontMatcher = DONT_PATTERN.matcher(input);
        while (dontMatcher.find()) {
            doNotPositions.add(dontMatcher.start());
        }

        List<Integer> doPositions = new ArrayList<>();
        Matcher doMatcher = DO_PATTERN.matcher(input);
        while (doMatcher.find()) {
            doPositions.add(doMatcher.start());
        }

        int doNotIndex = 0;
        int doIndex = 0;

        Matcher mulMatcher = MUL_PATTERN.matcher(input);

        int sum = 0;
        while (mulMatcher.find()) {
            int mulPosition = mulMatcher.start();
            while (doNotIndex < doNotPositions.size() && doNotPositions.get(doNotIndex) < mulPosition) {
                doNotIndex++;
            }
            while (doIndex < doPositions.size() && doPositions.get(doIndex) < mulPosition) {
                doIndex++;
            }

            int doNotBeforeMul = doNotIndex > 0 ? doNotPositions.get(doNotIndex - 1) : -1;
            int doBeforeMul = doIndex > 0 ? doPositions.get(doIndex - 1) : -1;
            if (doNotBeforeMul == -1 || doNotBeforeMul < doBeforeMul) {
                sum += Integer.parseInt(mulMatcher.group(1)) * Integer.parseInt(mulMatcher.group(2));
            }
        }
        System.out.println("sum part2: " + sum);
    }

    public static void main(String[] args) {
        Day3 d1 = new Day3();
        d1.solutionPart1();
        d1.solutionPart2();
    }
}
