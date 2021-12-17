package Day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class D11DumboOctopusPt1 {
    private static final String PATH = "Day11/input_day_11_ex.txt";
    private static int countFlashes = 0;

    public static void main(String[] args) throws IOException {
        int[][] octopuses = getOctopuses();
        for (int i = 1; i <= 100; i++) {
            increaseAllCells(octopuses);
            while (containsMaxEnergy(octopuses)) {
                countFlashingOctopuses(octopuses);

            }
        }
        System.out.println(countFlashes);
    }


    private static void countFlashingOctopuses(int[][] octopuses) {
        for (int row = 0; row < octopuses.length; row++) {
            for (int col = 0; col < octopuses[row].length; col++) {
                if (octopuses[row][col] > 9) {
                    countFlashes++;
                    octopuses[row][col] = 0;
                    increaseNeighbours(octopuses,row,col);
                }
            }
        }

    }

    private static boolean containsMaxEnergy(int[][] octopuses) {
        for (int row = 0; row < octopuses.length; row++) {
            for (int col = 0; col < octopuses[row].length; col++) {
                if (octopuses[row][col] > 9) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void increaseNeighbours(int[][] octopuses, int row, int col) {
        increaseCell(octopuses, row, col, -1, -1);
        increaseCell(octopuses, row, col, -1, 0);
        increaseCell(octopuses, row, col, -1, +1);
        increaseCell(octopuses, row, col, 0, -1);
        increaseCell(octopuses, row, col, 0, +1);
        increaseCell(octopuses, row, col, +1, -1);
        increaseCell(octopuses, row, col, +1, 0);
        increaseCell(octopuses, row, col, +1, +1);


    }

    private static void increaseCell(int[][] octopuses, int row, int col, int deltaRow, int deltaCol) {
        int r = row + deltaRow;
        int c = col + deltaCol;
        if (isInBounds(octopuses, r, c) && octopuses[r][c]!=0) {
            octopuses[r][c]++;
        }
    }

    private static void increaseAllCells(int[][] octopuses) {
        for (int row = 0; row < octopuses.length; row++) {
            for (int col = 0; col < octopuses[row].length; col++) {
                octopuses[row][col]++;
            }
        }
    }


    private static boolean isInBounds(int[][] octopuses, int row, int col) {
        return row >= 0 && row < octopuses.length && col >= 0 && col < octopuses[row].length;
    }

    private static int[][] getOctopuses() throws IOException {
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
