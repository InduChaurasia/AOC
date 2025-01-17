package com.aoc.challange.year_2024;

import com.aoc.challange.utils.InputFormatter;

import java.util.*;

/**
 * @see <a href="https://adventofcode.com/2024/day/12">problem description</a>
 * @see <a href="https://adventofcode.com/2024/day/12/input">input</a>
 */
public class Day12 {

    private static final char[][] gardenMap = Day12.readInput();
    private static Plant[][] plantsFenceMap;

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

    private void solution(String solutionPart) {

        boolean isPart1 = solutionPart.equals("1");
        List<Region> regions = new ArrayList<>();
        int columns = gardenMap[0].length;
        if(!isPart1) {
            plantsFenceMap = new Plant[gardenMap.length][columns];
        }

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
                    Set<DIRECTION> fenceDirections = isPart1?getFenceDirections(r, c):getOverlappedFenceDirections(r,c);
                    fenceSize = fenceSize + fenceDirections.size();
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

    private Set<DIRECTION> getOverlappedFenceDirections(int r, int c) {
        Set<DIRECTION> applicableFences = getFenceDirections(r, c);
        Set<DIRECTION> overlappedFences = new HashSet<>();
        if (applicableFences.contains(DIRECTION.LEFT)) {
            //up down
            if (upDownPlantHasNoFenceForDirection(r, c, DIRECTION.LEFT)) {
                overlappedFences.add(DIRECTION.LEFT);
            }
        }
        if (applicableFences.contains(DIRECTION.UP)) {
            //left right
            if (leftRightPlantHasNoFenceForDirection(r, c, DIRECTION.UP)) {
                overlappedFences.add(DIRECTION.UP);
            }
        }
        if (applicableFences.contains(DIRECTION.RIGHT)) {
            //up down
            if (upDownPlantHasNoFenceForDirection(r, c, DIRECTION.RIGHT)) {
                overlappedFences.add(DIRECTION.RIGHT);
            }
        }
        if (applicableFences.contains(DIRECTION.Down)) {
            //left right
            if (leftRightPlantHasNoFenceForDirection(r, c, DIRECTION.Down)) {
                overlappedFences.add(DIRECTION.Down);
            }
        }

        if (plantsFenceMap[r][c] == null) {
            plantsFenceMap[r][c] = new Plant(r, c, applicableFences);
        }
        return overlappedFences;
    }

    private boolean upDownPlantHasNoFenceForDirection(int r, int c, DIRECTION d) {
        Character plantType = gardenMap[r][c];

        Character upPlantType = r == 0 ? null : gardenMap[r - 1][c];
        Character downPlantType = r == gardenMap.length - 1 ? null : gardenMap[r + 1][c];

        boolean upfencePresent = false;
        if (upPlantType == plantType) {
            Plant plant = plantsFenceMap[r - 1][c];
            upfencePresent = plant != null && plant.fenceDirections != null && plant.fenceDirections.contains(d);

        }
        boolean downFencePresent = false;
        if (downPlantType == plantType) {
            Plant plant = plantsFenceMap[r + 1][c];
            downFencePresent = plant != null && plant.fenceDirections != null && plant.fenceDirections.contains(d);
        }

        return !upfencePresent && !downFencePresent;
    }

    private boolean leftRightPlantHasNoFenceForDirection(int r, int c, DIRECTION d) {
        Character plantType = gardenMap[r][c];

        Character leftPlantType = c == 0 ? null : gardenMap[r][c - 1];
        Character rightPlantType = c == gardenMap[0].length - 1 ? null : gardenMap[r][c + 1];

        boolean leftFencePresent = false;
        if (leftPlantType == plantType) {
            Plant plant = plantsFenceMap[r][c - 1];
            leftFencePresent = plant != null && plant.fenceDirections != null && plant.fenceDirections.contains(d);

        }
        boolean rightFencePresent = false;
        if (rightPlantType == plantType) {
            Plant plant = plantsFenceMap[r][c + 1];
            rightFencePresent = plant != null && plant.fenceDirections != null && plant.fenceDirections.contains(d);
        }

        return !leftFencePresent && !rightFencePresent;
    }

    private void solutionPart1() {
        solution("1");
    }
    private void solutionPart2() {
        solution("2");
    }
    public static void main(String[] args) {
        Day12 d = new Day12();
        d.solutionPart1();
        d.solutionPart2();
    }
}
