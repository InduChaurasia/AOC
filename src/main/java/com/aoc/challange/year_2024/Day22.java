package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2024/day/22/problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/22/input">input</a>
 */
public class Day22 {
    private final List<Long> initialSecrets = new ArrayList<>();

    private void initialiseInputs() {
        List<String> inputs = new ArrayList<>(InputFormatter.formatLines("input/2024/Day22").stream().toList());
        for (String input : inputs) {
            initialSecrets.add(Long.valueOf(input.trim()));
        }
    }

    private long getNextSecretNumber(long n) {
        long D = 16777216;

        long a = n * 64;
        long b = a ^ n;
        long d = b % D;

        long e = d / 32;
        long f = e ^ d;
        long g = f % D;

        long h = g * 2048;
        long i = h ^ g;
        long j = i % D;

        return j;
    }

    private void solution() {
        initialiseInputs();
        int limit = 2000;
        long sum = 0;

        for (Long initialSecret : initialSecrets) {
            int c = 0;
            long n = initialSecret;
            while (c < limit) {
                n = getNextSecretNumber(n);
                c++;
            }
            sum += n;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        Day22 d = new Day22();
        d.solution();
    }
}
