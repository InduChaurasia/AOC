package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;
import java.util.*;
import java.util.stream.LongStream;

/**
 * @see <a href="https://adventofcode.com/2024/day/9">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/9/input">input</a>
 */
public class Day9 {
    private final String diskMap = readInput();
    private final Map<Integer, Integer> blockSizeMap = new HashMap<>();

    private String readInput() {
        return InputFormatter.formatLines("input/2024/Day9").stream().toList().get(0);
    }

    private int[] generateBlocks() {
        List<Integer> blocks = new ArrayList<>();
        String[] arr = diskMap.split("");
        for (int i = 0; i < arr.length; i++) {
            int n = Integer.parseInt(arr[i]);
            int blockIndex = i / 2;
            boolean isFile = i % 2 == 0;
            //assign positive value for file blocks and negative value for space
            int val = isFile ? blockIndex : -1 * blockIndex;
            //exception case when space index is 0 handled here
            if (!isFile && val == 0) {
                val = Integer.MIN_VALUE;
            }
            blockSizeMap.put(val, n);
            while (n > 0) {
                blocks.add(val);
                n--;
            }
        }
        return blocks.stream().mapToInt(Integer::valueOf).toArray();
    }

    private long getChecksum(int[] fileBlocks) {
        return LongStream.range(0, fileBlocks.length)
                .filter(index -> fileBlocks[(int) index] >= 0)
                .reduce(0L, (a, b) -> a + fileBlocks[(int) b] * b);
    }

    private void solutionPart1() {
        int[] blocks = generateBlocks();
        int left = 0;
        int right = blocks.length - 1;
        while (left < right) {
            while (blocks[left] >= 0) {
                left++;
            }
            while (blocks[right] < 0) {
                right--;
            }
            if (left < right) {
                blocks[left] = blocks[right];
                blocks[right] = -1;
            }
        }
        System.out.println(getChecksum(blocks));
        //6258319840548
    }

    private void solutionPart2() {
        int[] blocks = generateBlocks();
        List<Integer> processedFiles = new ArrayList<>();
        for (int i = blocks.length - 1; i >= 0; i--) {
            int fIndex = blocks[i];
            if (blocks[i] > 0 && !processedFiles.contains(fIndex)) {
                int fSize = blockSizeMap.get(fIndex);
                for (int j = 0; j < i; j++) {
                    int sIndex = blocks[j];
                    int sSize = blockSizeMap.get(sIndex);
                    if (sIndex < 0 && sSize >= fSize) {
                        int n = fSize;
                        int s = j;
                        int f = i;
                        while (n > 0) {
                            int temp = blocks[s];
                            blocks[s] = blocks[f];
                            blocks[f] = temp;
                            s++;
                            f--;
                            n--;
                        }
                        blockSizeMap.put(sIndex, sSize - fSize);
                        break;
                    }
                }
                processedFiles.add(fIndex);
                i = i - fSize + 1;
            }
        }
        System.out.println(getChecksum(blocks));
        //6286182965311
    }

    public static void main(String[] args) {
        Day9 d = new Day9();
        d.solutionPart1();
        d.solutionPart2();
    }
}
