package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;


/**
 * @see <a href="https://adventofcode.com/2024/day/5">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/5/input">input</a>
 */
public class Day5 {
    public static Map<Integer, Set<Integer>> beforeMap = new HashMap<>();
    public static Map<Integer, Set<Integer>> afterMap = new HashMap<>();

    private static void updateRules() {
        List<String> lines = InputFormatter.formatLines("input/2024/Day5_1");
        for (String line : lines) {
            String[] arr = line.split("\\|");
            Integer num1 = Integer.parseInt(arr[0]);
            Integer num2 = Integer.parseInt(arr[1]);

            Set<Integer> before = beforeMap.getOrDefault(num2, new TreeSet<>());
            before.add(num1);
            beforeMap.put(num2, before);
            Set<Integer> after = afterMap.getOrDefault(num1, new TreeSet<>());
            after.add(num2);
            afterMap.put(num1, after);
        }
    }

    private static List<List<Integer>> getUpdates() {
        List<String> lines = InputFormatter.formatLines("input/2024/Day5_2");
        List<List<Integer>> updates = new ArrayList<>(lines.size());
        for (String line : lines) {
            List<Integer> pages = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
            updates.add(pages);
        }
        return updates;
    }


    /**
     * @see <a href="file:resources/input/2024/Day4">/resources/input/2024/Day5 for input</a>
     */
    private void solutionPart1(List<List<Integer>> updates) {

        Integer sum = 0;
        for (List<Integer> pageNumbers : updates) {
            int validPageOrderPerUpdate = getValidPageOrderPerUpdate(pageNumbers);
            if (validPageOrderPerUpdate == pageNumbers.size()) {
                int mid = pageNumbers.size() / 2;
                sum = sum + pageNumbers.get(mid);
            }
        }
        System.out.println(sum);
    }

    /**
     * @see <a href="file:resources/input/2024/Day4">/resources/input/2024/Day4 for input</a>
     */
    private void solutionPart2(List<List<Integer>> updates) {
        Integer sum = 0;
        for (List<Integer> pageNumbers : updates) {
            int validPageOrderPerUpdate = getValidPageOrderPerUpdate(pageNumbers);
            if (validPageOrderPerUpdate != pageNumbers.size()) {
                List<Integer> updatedPageNumbers = reorderPagesAsPerRule(pageNumbers);
                int mid = updatedPageNumbers.size() / 2;
                sum = sum + updatedPageNumbers.get(mid);
            }
        }
        System.out.println(sum);
    }

    private List<Integer> reorderPagesAsPerRule(List<Integer> pageNumbers) {
        Integer[] arr = new Integer[pageNumbers.size()];
        int index = 0;
        for (Integer pageNumber : pageNumbers) {
            System.out.print(pageNumber + ",");
            arr[index] = pageNumber;
            index++;
        }
        System.out.println();
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int keyIndex = i;
            for (int j = i - 1; j >= 0; j--) {
                Set<Integer> before = beforeMap.getOrDefault(key, new HashSet<>(0));
                Set<Integer> after = afterMap.getOrDefault(key, new HashSet<>(0));
                if (!before.contains(arr[j]) || after.contains(arr[j])) {
                    int temp = arr[j];
                    arr[j] = arr[keyIndex];
                    arr[keyIndex] = temp;
                    keyIndex = j;
                }
            }
        }
        return Arrays.asList(arr);
    }

    private int getValidPageOrderPerUpdate(List<Integer> pageNumbers) {
        int validPageOrderPerUpdate = 0;
        for (int i = 0; i < pageNumbers.size(); i++) {
            boolean beforeValid = checkPagesBefore(pageNumbers, i);
            boolean afterValid = checkPagesAfter(pageNumbers, i);
            if (beforeValid && afterValid) {
                validPageOrderPerUpdate++;
            } else {
                break;
            }
        }
        return validPageOrderPerUpdate;
    }

    private boolean checkPagesBefore(List<Integer> update, int index) {
        Integer num = update.get(index);
        if (index == 0 || afterMap.get(num) == null || afterMap.get(num).isEmpty()) {
            return Boolean.TRUE;
        }
        List<Integer> after = new ArrayList<>(afterMap.get(num));
        for (int i = index - 1; i >= 0; i--) {
            if (after.contains(update.get(i))) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    private boolean checkPagesAfter(List<Integer> update, int index) {
        Integer num = update.get(index);

        if (index == update.size() - 1 || beforeMap.get(num) == null || beforeMap.get(num).isEmpty()) {
            return Boolean.TRUE;
        }
        List<Integer> before = new ArrayList<>(beforeMap.get(num));
        for (int i = index + 1; i < update.size(); i++) {
            if (before.contains(update.get(i))) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    public static void main(String[] args) {
        updateRules();
        List<List<Integer>> updates = getUpdates();

        Day5 d = new Day5();
        d.solutionPart1(updates);
        d.solutionPart2(updates);
    }
}
