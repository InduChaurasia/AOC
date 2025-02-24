package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;
import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/21/problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/21/input">input</a>
 */
public class Day21 {

    private record CacheKey(Index start, Index end, int count) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return count == cacheKey.count && Objects.equals(start, cacheKey.start) && Objects.equals(end, cacheKey.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, count);
        }
    }

    private record Index(int x, int y) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Index index = (Index) o;
            return x == index.x && y == index.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private final Map<Character, Index> numPad = new HashMap<>();
    private final Map<Character, Index> dirPad = new HashMap<>();
    private final Index invalidDirPadIndex = new Index(0, 0);
    private final Index invalidNumPadIndex = new Index(3, 0);
    private final Map<CacheKey, Long> dirPadCache = new HashMap<>();
    private final Map<CacheKey, Long> numPadCache = new HashMap<>();

    private List<String> getInput() {
        numPad.put('7', new Index(0, 0));
        numPad.put('8', new Index(0, 1));
        numPad.put('9', new Index(0, 2));
        numPad.put('4', new Index(1, 0));
        numPad.put('5', new Index(1, 1));
        numPad.put('6', new Index(1, 2));
        numPad.put('1', new Index(2, 0));
        numPad.put('2', new Index(2, 1));
        numPad.put('3', new Index(2, 2));
        numPad.put('0', new Index(3, 1));
        numPad.put('A', new Index(3, 2));

        dirPad.put('^', new Index(0, 1));
        dirPad.put('A', new Index(0, 2));
        dirPad.put('<', new Index(1, 0));
        dirPad.put('v', new Index(1, 1));
        dirPad.put('>', new Index(1, 2));
        return new ArrayList<>(InputFormatter.formatLines("input/2024/Day21").stream().toList());
    }

    private String duplicate(char c, int n) {
        n = Math.abs(n);
        return String.valueOf(c).repeat(n);
    }

    private String getSimplePath(Index end, Index start) {
        StringBuilder sb = new StringBuilder();
        char row = end.x < start.x ? '^' : 'v';
        char col = end.y < start.y ? '<' : '>';
        if (end.x != start.x) {
            sb.append(duplicate(row, end.x - start.x));
        }
        if (end.y != start.y) {
            sb.append(duplicate(col, end.y - start.y));
        }
        return sb.toString();
    }

    private Set<String> arrangements(String s, int index) {
        Set<String> set = new HashSet<>(1);
        if (index == s.length() || s.length() == 1) {
            set.add(s);
            return set;
        }
        String s1 = s.substring(0, index);
        String s2 = s.substring(index);
        set.addAll(arrangements(s1 + s2, index + 1));
        set.addAll(arrangements(s2 + s1, index + 1));
        return set;
    }

    private Set<String> validPaths(Index start, Index end, Index invalidIndex) {
        String sp = getSimplePath(end, start);
        Set<String> paths = arrangements(sp, 1);
        Set<String> validPaths = new HashSet<>();
        for (String path : paths) {
            int r = start.x;
            int c = start.y;
            for (int i = 0; i < path.length(); i++) {
                char cc = path.charAt(i);
                if (cc == '>') {
                    c++;
                } else if (cc == '<') {
                    c--;
                } else if (cc == '^') {
                    r--;
                } else {
                    r++;
                }
                if (r == invalidIndex.x && c == invalidIndex.y) {
                    break;
                }
            }
            if (end.x == r && end.y == c) {
                validPaths.add(path + "A");
            }
        }
        return validPaths;
    }
    private Set<String> getPaths(Index start, Index end, Index invalidIndex) {
        Set<String> arr = new HashSet<>();
        if (start == end) {
            arr.add("A");
        } else {
            arr = validPaths(start, end, invalidIndex);
        }
        return arr;
    }
    private int shortest(Set<String> pp) {
        int min = Integer.MAX_VALUE;
        for (String s : pp) {
            min = Math.min(s.length(), min);
        }
        return min;
    }
    private long shortestPath(String s, int numPad, int noOfPads) {
        long path = 0;
        for (int i = 0; i < s.length(); i++) {
            long sp = shortestPath(s, i, numPad, noOfPads);
            if (sp != Long.MAX_VALUE) {
                path = path + sp;
            }
        }
        return path;
    }

    private long shortestPath(String s, int cCharIndex, int nPad, int noOfDirPads) {
        Map<Character, Index> keyPad = nPad == noOfDirPads ? numPad : dirPad;
        Index invalidIndex = nPad == noOfDirPads ? invalidNumPadIndex : invalidDirPadIndex;
        Map<CacheKey, Long> cache = nPad == noOfDirPads ? numPadCache : dirPadCache;

        Index start = cCharIndex == 0 ? keyPad.get('A') : keyPad.get(s.charAt(cCharIndex - 1));
        Index end = keyPad.get(s.charAt(cCharIndex));
        Set<String> paths = getPaths(start, end, invalidIndex);
        int shortest = shortest(paths);
        if (noOfDirPads == 1) {
            return shortest;
        }
        CacheKey key = new CacheKey(start, end, noOfDirPads);
        if (!cache.containsKey(key)) {
            long min = Long.MAX_VALUE;
            for (String path : paths) {
                if (path.length() == shortest) {
                    long p = shortestPath(path, nPad, noOfDirPads - 1);
                    min = Math.min(p, min);
                }
            }
            cache.put(key, min);
        }
        return cache.get(key);
    }
    private void solution(int n) {
        long complexity = 0;
        for (String s : getInput()) {
            long i = shortestPath(s, n, n);
            complexity = complexity + i * Long.parseLong(s.replace("A", ""));
        }
        System.out.printf("complexity for %s keypads: %s%n",n,complexity);
    }
    public static void main(String[] args) {
        Day21 d = new Day21();
        d.solution(3);
        d.solution(26);
    }
}
