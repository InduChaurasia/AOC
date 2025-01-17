package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/11">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/11/input">input</a>
 */
public class Day11 {

    private static class Node {
        Long val;
        Node next;

        public Node(Long val) {
            this.val = val;
        }

        public Node(String val) {
            this.val = Long.parseLong(val);
        }
    }

    private record StoneArrangement(Node head, Node tail, long totalNodes) {
    }

    private StoneArrangement readInput() {
        List<String> l = InputFormatter.formatLines("input/2024/Day11").stream().toList();
        Node head = null;
        Node previous = null;
        for (String s : l.get(0).split(" ")) {
            Node n = new Node(Long.parseLong(s));
            if (head == null) {
                head = n;
            } else {
                previous.next = n;
            }
            previous = n;
        }
        return new StoneArrangement(head, previous, l.get(0).split(" ").length);
    }

    private void solution() {
        int blinkCount = 25;
        int currentBlink = blinkCount;
        StoneArrangement stones = readInput();
        while (currentBlink > 0) {
            stones = blink(stones);
            System.out.printf("blink: %s, nodes: %s %n", (blinkCount - currentBlink + 1), stones.totalNodes);
            currentBlink--;
        }
    }

    private StoneArrangement blink(StoneArrangement stones) {
        long totalNodes = 0;
        Node pn = null;
        Node head = stones.head;
        Node cn = stones.head;

        while (cn != null) {
            totalNodes++;
            Long cnv = cn.val;
            if (cnv == 0) {
                cn.val = 1L;
            } else if (String.valueOf(cnv).length() % 2 == 0) {
                totalNodes++;
                List<Node> nodes = splitNode(cn, pn, head);
                cn = nodes.get(1);
                head = nodes.get(0);

            } else {
                cn.val = cnv * 2024;
            }
            pn = cn;
            cn = cn.next;
        }
        return new StoneArrangement(head, stones.tail, totalNodes);
    }

    private List<Node> splitNode(Node n, Node pn, Node head) {
        int l = String.valueOf(n.val).length();
        Node n1 = new Node(String.valueOf(n.val).substring(0, l / 2));
        Node n2 = new Node(String.valueOf(n.val).substring(l / 2, l));

        n1.next = n2;
        if (pn != null) {
            pn.next = n1;
        } else {
            head = n1;
        }
        if (n.next != null) {
            n2.next = n.next;
        }
        return Arrays.asList(head, n2);
    }

    public static void main(String[] args) {
        Day11 d = new Day11();
        d.solution();
    }
}
