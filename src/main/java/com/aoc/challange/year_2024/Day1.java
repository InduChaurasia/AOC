package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @see <a href="https://adventofcode.com/2024/day/1">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/1/input">input</a>
 */
public class Day1 {

    /**
     * @see <a href="file:resources/input/2024/Day1_1">/resources/input/2024/Day1_1 for left locations</a>
     * @see <a href="file:resources/input/2024/Day1_2">/resources/input/2024/Day1_2 for right locations</a>
     */
    private void solutionPart1(){
        List<Integer> leftLocations = InputFormatter.sortAndFormatList(InputFormatter.formatLines("input/2024/Day1_1"));
        List<Integer> rightLocations = InputFormatter.sortAndFormatList(InputFormatter.formatLines("input/2024/Day1_2"));
        int sum = IntStream.range(0, leftLocations.size()).parallel().map(index -> Math.abs(leftLocations.get(index) - rightLocations.get(index))).sum();
        System.out.println("result part1 "+sum);
    }


    /**
     * @see <a href="file:resources/input/2024/Day1_1">/resources/input/2024/Day1_1 for left locations</a>
     * @see <a href="file:resources/input/2024/Day1_2">/resources/input/2024/Day1_2 for right locations</a>
     */
    private void solutionPart2(){
        List<Integer> leftLocations = InputFormatter.sortAndFormatList(InputFormatter.formatLines("input/2024/Day1_1"));
        List<Integer> rightLocations = InputFormatter.sortAndFormatList(InputFormatter.formatLines("input/2024/Day1_2"));
        Map<Integer, Long> rightLocationsFrequency = rightLocations.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        int sum = leftLocations.parallelStream().mapToInt(leftLocation->leftLocation*rightLocationsFrequency.getOrDefault(leftLocation,0l).intValue()).sum();
        System.out.println("result part2 "+sum);
    }

    public static void main(String[] args) {
        Day1 d1 = new Day1();
        d1.solutionPart2();
    }
}
