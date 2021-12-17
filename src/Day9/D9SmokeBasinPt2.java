package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class D9SmokeBasinPt2 {
    static int[][] matrix;
    private static Map<int[], Integer> lowPoints = new HashMap<>();
    private static List<Integer> basins = new ArrayList<>();
    private static StringBuilder path;
    private static final String PATH = "Day9/input_day_9.txt";

    public static void main(String[] args) throws IOException {
        matrix = getMatrix();

        countLowPoints();

        findBasins();
       basins = basins.stream().sorted((f, s) -> Integer.compare(s, f))
                .limit(3).toList();
        long result = (long) basins.get(0) *basins.get(1)* basins.get(2);
        System.out.println(result);

    }

    private static void findBasins() {
        for (int[] lowPoint : lowPoints.keySet()) {
            path = new StringBuilder();
            int row = lowPoint[0];
            int col = lowPoint[1];
            String[][] board = getCharMatrix();
            findPath(board, row, col, "S");
            basins.add(path.length());
        }
    }

    private static void findPath(String[][] board, int row, int col, String direction) {
        if (!isInBounds(row, col)) {
            return;
        }
        int value = matrix[row][col];
        if (board[row][col].equals("9")
                || board[row][col].equals("V")
                || Integer.parseInt(board[row][col]) > value) {
            return;
        }
        path.append(direction);
        board[row][col] = "V";
        findPath(board, row - 1, col, "U");
        findPath(board, row + 1, col, "D");
        findPath(board, row, col - 1, "L");
        findPath(board, row, col + 1, "R");

        board[row][col] = "9"; // backtracking
    }

    private static String[][] getCharMatrix() {
        String[][] board = new String[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {

                board[row][col] = String.valueOf(matrix[row][col]);
            }
        }
        return board;
    }


    private static void countLowPoints() {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                int point = matrix[row][col];
                if (noLowerNeighbours(row, col)) {
                    lowPoints.put(new int[]{row, col}, point);
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
            return matrix[r][c] > matrix[row][col];
        }
        return true;

    }


    private static boolean isInBounds(int row, int col) {
        return row >= 0 && row < matrix.length && col >= 0 && col < matrix[row].length;
    }

    private static int[][] getMatrix() throws IOException {
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
