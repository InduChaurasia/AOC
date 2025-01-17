package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

public class Day12 {

    private static final char[][] gardenMap = Day12.readInput();

    private enum DIRECTION {
        UP,
        Down,
        LEFT,
        RIGHT
    }

    private record Region(
            Set<Plant> plants,
            int fenceSize,
            char plantType) {
    }

    private record Plant(int row, int column, Set<DIRECTION> fenceDirections) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Plant index = (Plant) o;
            return row == index.row && column == index.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static char[][] readInput() {
        List<String> l = InputFormatter.formatLines("input/2024/Day12").stream().toList();
        char[][] gardenMap = new char[l.size()][l.get(0).length()];
        int r = 0;
        for (String s : l) {
            int c = 0;
            while (c < l.get(0).length()) {
                gardenMap[r][c] = s.charAt(c);
                c++;
            }
            r++;
        }
        return gardenMap;
    }

    private void solutionPart1() {
        List<Region> regions = new ArrayList<>();
        int columns = gardenMap[0].length;
        Queue<Plant> regionPlantsQueue = new LinkedList<>();
        Queue<Plant> gardenPlantsQueue = new LinkedList<>();
        List<Plant> processedPlants = new ArrayList<>();
        Plant firstPlant = new Plant(0, 0, new HashSet<>(0));
        gardenPlantsQueue.add(firstPlant);
        while (!gardenPlantsQueue.isEmpty()) {
            Plant gardenPlant = gardenPlantsQueue.poll();
            regionPlantsQueue.add(gardenPlant);
            int fenceSize = 0;
            Set<Plant> regionPlants = new HashSet<>();
            Character plantType = null;
            while (!regionPlantsQueue.isEmpty()) {
                Plant currentRegionPlant = regionPlantsQueue.poll();
                if (!processedPlants.contains(currentRegionPlant)) {
                    int r = currentRegionPlant.row;
                    int c = currentRegionPlant.column;
                    plantType = gardenMap[r][c];
                    fenceSize = fenceSize + getFenceDirections(r, c).size();
                    processedPlants.add(currentRegionPlant);
                    regionPlants.add(currentRegionPlant);

                    if (c != 0 && gardenMap[r][c - 1] == plantType) {
                        regionPlantsQueue.add(new Plant(r, c - 1, new HashSet<>(0)));
                    } else {
                        if (c != 0) {
                            gardenPlantsQueue.add(new Plant(r, c - 1, new HashSet<>(0)));
                        }
                    }
                    if (r != 0 && gardenMap[r - 1][c] == plantType) {
                        regionPlantsQueue.add(new Plant(r - 1, c, new HashSet<>(0)));
                    } else {
                        if (r != 0) {
                            gardenPlantsQueue.add(new Plant(r - 1, c, new HashSet<>(0)));
                        }
                    }
                    if (c != columns - 1 && gardenMap[r][c + 1] == plantType) {
                        regionPlantsQueue.add(new Plant(r, c + 1, new HashSet<>(0)));
                    } else {
                        if (c != columns - 1) {
                            gardenPlantsQueue.add(new Plant(r, c + 1, new HashSet<>(0)));
                        }
                    }
                    if (r != gardenMap.length - 1 && gardenMap[r + 1][c] == plantType) {
                        regionPlantsQueue.add(new Plant(r + 1, c, new HashSet<>(0)));
                    } else {
                        if (r != gardenMap.length - 1) {
                            gardenPlantsQueue.add(new Plant(r + 1, c, new HashSet<>(0)));
                        }
                    }
                }
            }

            if (plantType != null) {
                Region r = new Region(regionPlants, fenceSize, plantType);
                regions.add(r);
            }
        }

        System.out.println("Total regions: " + regions.size());
        int totalFenceSize = regions.stream().peek(r -> System.out.printf("Region of %s plants with price %s * %s = %s%n", r.plantType, r.plants.size(), r.fenceSize, r.plants.size() * r.fenceSize)).map(r -> r.fenceSize * r.plants.size()).mapToInt(Integer::valueOf).sum();
        System.out.println("Fence Size : " + totalFenceSize);
    }

    private Set<DIRECTION> getFenceDirections(int r, int c) {
        int columns = gardenMap[0].length;
        char plant = gardenMap[r][c];
        Set<DIRECTION> fenceDirections = new HashSet<>();
        if (c == 0 || gardenMap[r][c - 1] != plant) {
            fenceDirections.add(DIRECTION.LEFT);
        }
        if (r == 0 || gardenMap[r - 1][c] != plant) {
            fenceDirections.add(DIRECTION.UP);
        }
        if (c == columns - 1 || gardenMap[r][c + 1] != plant) {
            fenceDirections.add(DIRECTION.RIGHT);
        }
        if (r == gardenMap.length - 1 || gardenMap[r + 1][c] != plant) {
            fenceDirections.add(DIRECTION.Down);
        }
        return fenceDirections;
    }

    public static void main(String[] args) {
        Day12 d = new Day12();
        d.solutionPart1();
    }
}
