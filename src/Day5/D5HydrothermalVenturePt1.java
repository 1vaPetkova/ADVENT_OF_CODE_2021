package Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class D5HydrothermalVenturePt1 {
    private static final String PATH = "src/input/InputDay5.txt";

    public static void main(String[] args) throws IOException {

        List<int[]> points = new ArrayList<>();

        Files.readAllLines(Path.of(PATH))
                .forEach(input -> {
                    input = input.replaceAll("\\s+->\\s+", ",");
                    int[] ints = Arrays.stream(input.split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    points.add(ints);
                });

        List<int[]> lines = points.stream()
                .filter(c -> (c[0] == c[2]) || (c[1] == c[3])).toList();

        int maxCoord = findMaxCoords(lines);
        int[][] matrix = new int[maxCoord + 1][maxCoord + 1];

        for (int[] line : lines) {
            int x1 = line[0];
            int y1 = line[1];
            int x2 = line[2];
            int y2 = line[3];

            if (x1 == x2) {
                int start = Math.min(y1, y2);
                int end = Math.max(y1, y2);
                for (int y = start; y <= end; y++) {
                    matrix[y][x1] += 1;
                }
            }
            if (y1 == y2) {
                int start = Math.min(x1, x2);
                int end = Math.max(x1, x2);
                for (int x = start; x <= end; x++) {
                    matrix[y1][x] += 1;
                }
            }
        }

        System.out.println(findCrosses(matrix));

    }

    private static int findCrosses(int[][] matrix) {
        int counter = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] > 1) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static int findMaxCoords(List<int[]> lines) {
        int max = 0;
        int[] maxCoords = new int[2];
        for (int[] line : lines) {
            int maxCoord = Arrays.stream(line).max().orElse(0);
            if (maxCoord >= max) {
                max = maxCoord;
            }
        }
        return max;
    }


}
