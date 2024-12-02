package com.aoc.challange;

import com.aoc.challange.utils.InputFormatter;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        List<String> strings = InputFormatter.formatLines("input/2024/Day1_1");
        System.out.println("File has "+strings.size()+" lines");
    }
}