package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/22/problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/22/input">input</a>
 */
public class Day22 {
    private final List<Long> initialSecrets = new ArrayList<>();
    private final Map<Long, long[]> secretPrices = new HashMap<>();
    private final Map<Long, long[]> secretPriceDiffs = new HashMap<>();

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
        long sum = 0;

        for (Long initialSecret : initialSecrets) {
            int c = 0;
            long n = initialSecret;
            int limit = 2000;
            long[] prices = secretPrices.getOrDefault(initialSecret, new long[limit + 1]);
            long[] priceDiffs = secretPriceDiffs.getOrDefault(initialSecret, new long[limit]);
            prices[0] = n % 10;
            while (c < limit) {
                n = getNextSecretNumber(n);
                prices[c + 1] = n % 10;
                priceDiffs[c] = prices[c + 1] - prices[c];
                c++;
            }
            secretPrices.put(initialSecret, prices);
            secretPriceDiffs.put(initialSecret, priceDiffs);
            sum += n;
        }
        System.out.println(sum);
        solutionPart2();
    }

    private void solutionPart2() {
        Map<String, long[]> possibleFormats1 = findPricesForFormats();
        Optional<Long> max = possibleFormats1.keySet().parallelStream()
                .map(key -> Arrays.stream(possibleFormats1.get(key))
                        .map(val -> Long.valueOf(val).intValue())
                        .sum()).max(Comparator.naturalOrder());
        max.ifPresent(System.out::println);
    }

    private Map<String, long[]> findPricesForFormats() {
        Map<String, long[]> patternAndFirstPrices = new HashMap<>();
        for (int i = 0; i < initialSecrets.size(); i++) {
            long initialSecret = initialSecrets.get(i);
            long[] diffs = secretPriceDiffs.get(initialSecret);
            Set<String> processedFormats = new HashSet<>(0);
            for (int k = 3; k < diffs.length; k++) {
                String format = String.format("%s,%s,%s,%s", diffs[k - 3], diffs[k - 2], diffs[k - 1], diffs[k]);
                if (!processedFormats.contains(format)) {
                    long[] firstDiffValues = patternAndFirstPrices.getOrDefault(format, new long[initialSecrets.size()]);
                    firstDiffValues[i] = secretPrices.get(initialSecret)[k + 1];
                    patternAndFirstPrices.put(format, firstDiffValues);
                    processedFormats.add(format);
                }
            }
        }
        return patternAndFirstPrices;
    }

    public static void main(String[] args) {
        Day22 d = new Day22();
        d.solution();
    }
}
