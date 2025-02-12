package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/24/problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/24/input">input</a>
 */
public class Day24 {

    private enum OPERATION {
        AND,
        OR,
        XOR
    }

    private record Wire(String name, String[] inputs, String gate) {
    }

    private final Map<String, Wire> wireNameMap = new TreeMap<>();
    private final Map<String, Integer> wireValues = new HashMap<>();

    private void initialiseWire() {

        List<String> inputs1 = new ArrayList<>(InputFormatter.formatLines("input/2024/Day24_1").stream().toList());
        for (String s : inputs1) {
            String[] splits = s.split(": ");
            wireValues.put(splits[0], Integer.parseInt(splits[1]));
        }

        List<String> inputs2 = new ArrayList<>(InputFormatter.formatLines("input/2024/Day24_2").stream().toList());

        for (String s : inputs2) {

            String[] split1 = s.split(" -> ");
            String[] split2 = split1[0].split(" ");
            String[] inputs = new String[2];
            inputs[0] = split2[0];
            inputs[1] = split2[2];
            String gate = split2[1];

            String name = split1[1];
            Wire w = new Wire(name, inputs, gate);
            wireNameMap.put(name, w);
        }
    }

    private int operation(OPERATION o, int in1, int in2) {
        int result = -1;
        switch (o) {
            case AND -> result = (in1 == 0 || in2 == 0) ? 0 : 1;
            case OR -> result = (in1 == 1 || in2 == 1) ? 1 : 0;
            case XOR -> result = (in1 == in2) ? 0 : 1;
            default -> {
            }
        }
        return result;
    }

    private int calculateValue(String wireName) {

        if (wireValues.containsKey(wireName)) {
            return wireValues.get(wireName);
        }

        Wire w = wireNameMap.get(wireName);
        String[] inputWires = w.inputs;
        int wireVal1 = calculateValue(inputWires[0]);
        int wireVal2 = calculateValue(inputWires[1]);

        int val = operation(OPERATION.valueOf(w.gate), wireVal1, wireVal2);
        if (val != -1) {
            wireValues.put(w.name, val);
        }
        return val;
    }

    private void solutionPart1() {
        List<String> wireNames = wireNameMap.keySet().stream().filter(name -> name.startsWith("z")).sorted().toList();
        long result = 0;
        for (int i = 0; i < wireNames.size(); i++) {
            int val = calculateValue(wireNames.get(i));
            System.out.print(val);
            result = result + ((long) Math.pow(2, i) * val);
        }
        System.out.println();
        System.out.println(result);
    }

    private void solution() {
        initialiseWire();
        solutionPart1();
    }

    public static void main(String[] args) {
        Day24 d = new Day24();
        d.solution();
    }


}
