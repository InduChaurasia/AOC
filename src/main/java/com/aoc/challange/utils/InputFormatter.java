package com.aoc.challange.utils;

import com.google.common.io.Resources;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InputFormatter {

    private static Path path;
    private static Path path1;

    public static List<String> formatLines(String filePath) {
        //input/2024/Day1_1

        try {
            URL url = Resources.getResource(filePath);
            Path path1 = Paths.get(url.toURI());
            return Files.readAllLines(path1, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error while reading lines from file " + filePath, e);
        }
    }

    public static List<Integer> formatList(List<String> l) {
        return l.parallelStream().map(Integer::valueOf).toList();
    }

    public static List<Integer> sortAndFormatList(List<String> l) {
        return l.parallelStream().map(Integer::valueOf).sorted().toList();
    }

}
