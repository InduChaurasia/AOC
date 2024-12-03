package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @see <a href="https://adventofcode.com/2024/day/2">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/2/input">input</a>
 */
public class Day2 {

    private static final int minSafeLimit = 1;
    private static final int maxSafeLimit = 3;

    private List<String> getReports() {
        return InputFormatter.formatLines("input/2024/Day2");
    }

    private List<Integer> getReportLevels(String report) {
        return Arrays.stream(report.split(" ")).map(Integer::valueOf).toList();
    }

    private boolean isReportSafe(String report) {
        List<Integer> reportLevels = getReportLevels(report);
        int badLevel = findBadLevel(reportLevels);
        if (badLevel != -1) {
            System.out.println(report);
        }
        return badLevel == -1;
    }

    private boolean isDampenReportSafe(String report) {
        List<Integer> reportLevels = getReportLevels(report);
        int badLevel = findBadLevel(reportLevels);
        if (badLevel == -1) {
            return Boolean.TRUE;
        }

        if (findDampenReportBadLevel(reportLevels, badLevel - 1) == -1
                || findDampenReportBadLevel(reportLevels, badLevel) == -1
                || findDampenReportBadLevel(reportLevels, badLevel + 1) == -1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static int findDampenReportBadLevel(List<Integer> reportLevels, int badLevel) {
        List<Integer> dampenReportLevels = new ArrayList<>(reportLevels.size() - 1);
        for (int index = 0; index < reportLevels.size(); index++) {
            if (index != badLevel) {
                dampenReportLevels.add(reportLevels.get(index));
            }
        }
        return findBadLevel(dampenReportLevels);
    }

    private static int findBadLevel(List<Integer> reportLevels) {
        boolean isAscending = (reportLevels.get(1) - reportLevels.get(0)) >= 0;
        for (int index = 0; index < reportLevels.size() - 1; index++) {
            int levelDiff = reportLevels.get(index + 1) - reportLevels.get(index);
            if (isAscending) {
                if (!(levelDiff >= minSafeLimit && levelDiff <= maxSafeLimit)) {
                    return index;
                }
            } else {
                int positiveLevelDiff = levelDiff * (-1);
                if (!(positiveLevelDiff >= minSafeLimit && positiveLevelDiff <= maxSafeLimit)) {
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * @see <a href="file:resources/input/2024/Day2">/resources/input/2024/Day2 for reports</a>
     */
    private void solutionPart1() {
        long safeReportCount = getReports().parallelStream().filter(this::isReportSafe).count();
        System.out.println("safe reports count: " + safeReportCount);
    }


    /**
     * @see <a href="file:resources/input/2024/Day2">/resources/input/2024/Day2 for reports</a>
     */
    private void solutionPart2() {

        long safeReportCount = getReports().parallelStream().filter(this::isDampenReportSafe).count();
        System.out.println("safe reports count after dampener: " + safeReportCount);

    }

    public static void main(String[] args) {
        Day2 d1 = new Day2();
        d1.solutionPart1();
        d1.solutionPart2();
    }
}
