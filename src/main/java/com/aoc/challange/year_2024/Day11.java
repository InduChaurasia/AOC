package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/11">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/11/input">input</a>
 */
public class Day11 {
    private Map<String, Long> cache = new HashMap<>();
    private int[] readInput() {
        List<String> l = InputFormatter.formatLines("input/2024/Day11").stream().toList();
        return Arrays.stream(l.get(0).split(" ")).mapToInt(Integer::parseInt).toArray();
    }
    private List<Long> applyRule(long n) {
        List<Long> r = new ArrayList<>(2);
        if (n == 0) {
            r.add(1L);
        } else if (String.valueOf(n).length() % 2 == 0) {
            String s = String.valueOf(n);
            r.add(Long.valueOf(s.substring(0, s.length() / 2)));
            r.add(Long.valueOf(s.substring(s.length() / 2)));
        } else {
            r.add(2024 * n);
        }
        return r;
    }

    private long blink(long n, int remainingBlink) {
        if (remainingBlink == 0) {
           return 1;
        }
        String key = String.format("%s-%s", n, remainingBlink);
        if (!cache.containsKey(key)) {
            List<Long> l = applyRule(n);
            long count = 0;
            for (Long i : l) {
                String key1 = String.format("%s-%s", i, remainingBlink-1);
                if(cache.containsKey(key1)){
                   count = count+cache.get(key1);
                }else{
                  count = count + blink(i, remainingBlink-1);
                }
            }
            cache.put(key, count);
        }
        return cache.getOrDefault(key, 0L);
    }
    private void solution(int blinkCount) {
        cache = new HashMap<>();
        int[] input = readInput();
        long count = 0;
        for (int i : input) {
            count = count+ blink(i,blinkCount);
        }
        System.out.printf("No of stones after %s blinks: %s%n", blinkCount, count);
    }
    public static void main(String[] args) {
        Day11 d = new Day11();
        d.solution(25);//197157
        d.solution(75);//234430066982597
    }
}
