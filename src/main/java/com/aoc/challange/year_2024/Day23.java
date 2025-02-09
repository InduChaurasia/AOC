package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/23/problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/23/input">input</a>
 */
public class Day23 {
    private record Node(String val, List<String> connections) {
    }

    private final Map<String, Node> nodes = new HashMap<>();

    private void initialiseNodes() {
        List<String> inputs = new ArrayList<>(InputFormatter.formatLines("input/2024/Day23").stream().toList());
        for (String input : inputs) {
            String[] split = input.split("-");
            Node n1 = nodes.getOrDefault(split[0], new Node(split[0], new ArrayList<>()));
            Node n2 = nodes.getOrDefault(split[1], new Node(split[1], new ArrayList<>()));
            n1.connections.add(n2.val);
            n2.connections.add(n1.val);
            nodes.put(n1.val, n1);
            nodes.put(n2.val, n2);
        }
    }

    private void solutionPart1() {
        Set<String> triplets = new HashSet<>();
        List<Node> tNodes = nodes.values().stream().filter(n -> n.val.startsWith("t")).toList();
        for (Node n1 : tNodes) {
            TreeSet<String> subset = new TreeSet<>();
            subset.add(n1.val);
            triplets(subset, n1.connections, 0, triplets);
        }
        System.out.println(triplets.size());
    }

    private void triplets(TreeSet<String> subset, List<String> options, int currentIndex, Set<String> result) {
        if (subset.size() == 3) {
            result.add(String.join(",", subset));
            return;
        }
        if (currentIndex >= options.size()) {
            return;
        }

        String node = options.get(currentIndex);
        if (isNodeValidToAdd(subset, node)) {
            TreeSet<String> subset1 = new TreeSet<>(subset);
            subset1.add(node);
            triplets(subset1, options, currentIndex + 1, result);
        }
        triplets(subset, options, currentIndex + 1, result);
    }

    private void solutionPart2() {
        String maxLengthlan = "";
        for (String n : nodes.keySet()) {
            TreeSet<String> subset = new TreeSet<>();
            subset.add(nodes.get(n).val);
            String lan = maxLengthLan(subset, nodes.get(n).connections, 0);
            if (lan.length() >= maxLengthlan.length()) {
                maxLengthlan = lan;
            }
        }
        System.out.printf("Max length lan: %s%n", maxLengthlan);
    }

    private String maxLengthLan(TreeSet<String> subset, List<String> options, int currentIndex) {
        if (currentIndex >= options.size()) {
            return String.join(",", subset);
        }

        String node = options.get(currentIndex);
        String r1 = "";
        if (isNodeValidToAdd(subset, node)) {
            TreeSet<String> subset1 = new TreeSet<>(subset);
            subset1.add(node);
            r1 = maxLengthLan(subset1, options, currentIndex + 1);
        }
        String r2 = maxLengthLan(subset, options, currentIndex + 1);
        return r1.length() > r2.length() ? r1 : r2;
    }

    private boolean isNodeValidToAdd(TreeSet<String> subset, String toMatch) {
        if (subset.isEmpty()) {
            return true;
        }
        return subset.stream().allMatch(node -> nodes.get(node).connections.contains(toMatch));
    }

    private void solution() {
        initialiseNodes();
        solutionPart1();
        solutionPart2();
    }

    public static void main(String[] args) {
        Day23 d = new Day23();
        d.solution();
    }
}
