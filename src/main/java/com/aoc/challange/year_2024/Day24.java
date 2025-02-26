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
    private final Map<String, Integer> calculatedValues = new HashMap<>();
    private final Map<String, Integer> expectedValues = new HashMap<>();

    private void initialiseWire() {

        List<String> inputs1 = new ArrayList<>(InputFormatter.formatLines("input/2024/Day24_1").stream().toList());
        for (String s : inputs1) {
            String[] splits = s.split(": ");
            calculatedValues.put(splits[0], Integer.parseInt(splits[1]));
            expectedValues.put(splits[0], Integer.parseInt(splits[1]));
        }

        List<String> inputs2 = new ArrayList<>(InputFormatter.formatLines("input/2024/Day24_2").stream().toList());

        for (String s : inputs2) {

            String[] split1 = s.split(" -> ");
            String[] split2 = split1[0].split(" ");
            String[] inputs = new String[2];
            inputs[0] = split2[0];
            inputs[1] = split2[2];
            Arrays.sort(inputs);
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

        if (calculatedValues.containsKey(wireName)) {
            return calculatedValues.get(wireName);
        }

        Wire w = wireNameMap.get(wireName);
        String[] inputWires = w.inputs;
        int wireVal1 = calculateValue(inputWires[0]);
        int wireVal2 = calculateValue(inputWires[1]);

        int val = operation(OPERATION.valueOf(w.gate), wireVal1, wireVal2);
        if (val != -1) {
            calculatedValues.put(w.name, val);
        }
        return val;
    }
    private void calculateExpectedValue(String wireName) {
        String suffix = wireName.substring(1);
        String x = "x" + suffix;
        String y = "y" + suffix;
        String c = "c" + suffix;
        int val;
        int co;
        if (suffix.equals("00")) {
            val = expectedValues.get(x) ^ expectedValues.get(y);
            co = expectedValues.get(x) & expectedValues.get(y);
        } else {
            String ps = String.valueOf(Integer.parseInt(suffix) - 1);
            String pco = "c" + (ps.length() == 2 ? ps : "0" + ps);
            val = expectedValues.get(pco) ^ (expectedValues.get(x) ^ expectedValues.get(y));
            co = (expectedValues.get(x) & expectedValues.get(y)) | (expectedValues.get(pco) & (expectedValues.get(x) ^ expectedValues.get(y)));
        }
        expectedValues.put(wireName, val);
        expectedValues.put(c, co);
    }
    private String printWireMapping(String wireName, int index) {
        Wire w = wireNameMap.get(wireName);
        if (wireName.startsWith("x") | wireName.startsWith("y") || w == null) {
            return "";
        }
        String indent = " ".repeat(index);
        String wire = String.format("%s %s %s", w.inputs[0], w.gate, w.inputs[1]);
        String input1 = String.format("%s%s", indent, printWireMapping(w.inputs[0], index + 1));
        String input2 = String.format("%s%s", indent, printWireMapping(w.inputs[1], index + 1));
        if (!input1.isBlank() && !input2.isBlank()) {
            return String.format("%s = %s%n%s%n%s", wireName, wire, input1, input2);
        } else if (!input1.isBlank()) {
            return String.format("%s = %s%n%s", wireName, wire, input1);
        } else if (!input2.isBlank()) {
            return String.format("%s = %s%n%s", wireName, wire, input2);
        } else {
            return String.format("%s = %s", wireName, wire);
        }
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

    private void solutionPart2() {
        List<String> wireNames = wireNameMap.keySet().stream().filter(name -> name.startsWith("z")).sorted().toList();
        for (int i = 0; i < wireNames.size(); i++) {
            String wireName = wireNames.get(i);
            if (i == wireNames.size() - 1) {
                String suffix = wireName.substring(1);
                String ps = String.valueOf(Integer.parseInt(suffix) - 1);
                String pco = "c" + (ps.length() == 2 ? ps : "0" + ps);
                Integer co = expectedValues.get(pco);
                if (co != null) {
                    expectedValues.put(wireName, co);
                }
            } else {
                calculateExpectedValue(wireName);
            }
            if (expectedValues.get(wireName) == null || !(expectedValues.get(wireName).equals(calculatedValues.get(wireName)))) {
                System.out.println(wireName);
            }
        }
        //printWireMapping(wireName,1);
        //manually checked wrong wire mappings for wires having expectedVal != calculatedVal
        String[] wrongOutputs = {"tst","z05","sps","z11","z23","frt","cgh","pmd"};
        Arrays.sort(wrongOutputs);
        System.out.println(String.join(",",wrongOutputs));
    }

    private void solution() {
        initialiseWire();
        solutionPart1();
        solutionPart2();
    }

    public static void main(String[] args) {
        Day24 d = new Day24();
        d.solution();
    }

}
