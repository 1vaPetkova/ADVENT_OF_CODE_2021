package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D9SmokeBasinPt1 {
    static int[][] basin;
    private static List<Integer> lowPoints = new ArrayList<>();
    private static final String PATH = "Day9/input_day_9.txt";

    public static void main(String[] args) throws IOException {
        basin = getBasin();

        countLowPoints();
        int sum = lowPoints.stream().mapToInt(e -> e).sum() + lowPoints.size();
        System.out.println(sum);

    }

    private static void countLowPoints() {
        for (int row = 0; row < basin.length; row++) {
            for (int col = 0; col < basin[row].length; col++) {
                int point = basin[row][col];
                if (noLowerNeighbours(row, col)) {
                    lowPoints.add(point);
                }
            }
        }
    }


    private static boolean noLowerNeighbours(int row, int col) {
        return notALowerNeighbour(row, col, -1, 0)            //up
                && notALowerNeighbour(row, col, 0, +1)        //right
                && notALowerNeighbour(row, col, +1, 0)        //down
                && notALowerNeighbour(row, col, 0, -1);       // left

    }

    private static boolean notALowerNeighbour(int row, int col, int deltaRow, int deltaCol) {
        int r = row + deltaRow;
        int c = col + deltaCol;
        if (isInBounds(r, c)) {
            return basin[r][c] > basin[row][col];
        }
        return true;

    }


    private static boolean isInBounds(int row, int col) {
        return row >= 0 && row < basin.length && col >= 0 && col < basin[row].length;
    }

    private static int[][] getBasin() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(PATH));
        int[][] matrix = new int[lines.size()][lines.get(0).length()];
        for (int row = 0; row < matrix.length; row++) {
            int[] currentRow = Arrays.stream(lines.get(row).split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            for (int col = 0; col < matrix[row].length; col++) {
                matrix[row][col] = currentRow[col];
            }
        }
        return matrix;
    }


}
