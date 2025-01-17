package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @see <a href="https://adventofcode.com/2024/day/9">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/9/input">input</a>
 */
public class Day9 {
    private final String diskMap = readInput();

    private String readInput() {
        return InputFormatter.formatLines("input/2024/Day9").stream().toList().get(0);
    }

    private long[] generateFileBlocks() {
        String[] arr = diskMap.split("");
        return IntStream.range(0, arr.length).mapToObj(i -> {
            int n = Integer.parseInt(arr[i]);
            int fileIndex = i / 2;
            boolean isFile = i % 2 == 0;
            String val = isFile ? String.valueOf(fileIndex) : "-1";
            List<Long> diskBlocks = new ArrayList<>(n);
            while (n > 0) {
                diskBlocks.add(Long.valueOf(val));
                n--;
            }
            return diskBlocks;
        }).flatMap(Collection::stream).mapToLong(Long::valueOf).toArray();
    }

    private int findNextSpaceFromLeft(int fromIndex, int max, long[] arr) {
        for (int i = fromIndex; i <= max; i++) {
            if (arr[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    private int findNextFileFromRight(int left, int maxLen, long[] arr) {
        for (int i = maxLen; i >= left; i--) {
            if (arr[i] != -1) {
                return i;
            }
        }
        return -1;
    }

    private long getChecksum(long[] fileBlocks) {
        return LongStream.range(0, fileBlocks.length)
                .filter(index -> fileBlocks[(int) index] != -1)
                .reduce(0L, (a, b) -> a + fileBlocks[(int) b] * b);
    }

    private void solutionPart1() {
        long[] fileBlocks = generateFileBlocks();

        int left = findNextSpaceFromLeft(0, fileBlocks.length - 1, fileBlocks);
        int right = findNextFileFromRight(0, fileBlocks.length - 1, fileBlocks);

        while (left < right && left != -1 && right != -1) {
            fileBlocks[left] = fileBlocks[right];
            fileBlocks[right] = -1;
            left = findNextSpaceFromLeft(left + 1, right - 1, fileBlocks);
            right = findNextFileFromRight(left + 1, right - 1, fileBlocks);
        }
        System.out.println(getChecksum(fileBlocks));
    }

    public static void main(String[] args) {
        Day9 d = new Day9();
        d.solutionPart1();
    }
}
