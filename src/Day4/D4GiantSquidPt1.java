package Day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class D4GiantSquidPt1 {
    private static final String PATH = "src/input/InputDay4.txt";
    private static List<int[][]> boards;
    private static List<Integer> numbers;
    private static int[][] bingoBoard;
    private static boolean[][] markedBingo;
    private static List<boolean[][]> marked;
    private static int sumOfUnmarked;
    private static int finalValue;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(PATH));

        numbers = Arrays.stream(lines.get(0).split(","))
                .map(Integer::parseInt).toList();

        boards = new ArrayList<>();

        lines = lines.stream().filter(l -> !l.isBlank()).skip(1).collect(Collectors.toList());

        for (int row = 0; row < lines.size(); row += 5) {
            int[][] matrix = new int[5][5];
            for (int i = 0; i < 5; i++) {
                String nums = lines.get(row + i);
                matrix[i] = Arrays.stream(nums.trim().split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }
            boards.add(matrix);
        }

        //mark boards
        marked = new ArrayList<>();
        IntStream.range(0, boards.size()).forEach(i -> marked.add(new boolean[5][5]));

        boolean isBingo = false;

        for (int value = 0; value < numbers.size(); value++) {
            int currentNumber = numbers.get(value);
            for (int i = 0; i < boards.size(); i++) {
                int[][] currentBoard = boards.get(i);
                boolean[][] currentMarked = marked.get(i);
                markPositions(currentBoard, currentMarked, currentNumber);
                if (checkIfAnyRowFullyMarked(currentMarked) || checkIfAnyColFullyMarked(currentMarked)) {
                    bingoBoard = currentBoard;
                    markedBingo = currentMarked;
                    isBingo = true;
                    break;
                }
            }
            if (isBingo) {
                finalValue = currentNumber;
                break;
            }
        }
        System.out.println(findSumOfUnmarkedNumbers() * finalValue);

    }

    private static int findSumOfUnmarkedNumbers() {
        int sum = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (!markedBingo[row][col]) {
                    sum += bingoBoard[row][col];
                }
            }
        }
        return sum;
    }

    private static void markPositions(int[][] currentBoard, boolean[][] currentMarked, int currentNumber) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (currentNumber == currentBoard[row][col]) {
                    currentMarked[row][col] = true;
                }
            }
        }
    }

    private static boolean checkIfAnyColFullyMarked(boolean[][] marked) {
        int count = 0;
        for (int c = 0; c < 5; c++) {
            for (int r = 0; r < 5; r++) {
                if (marked[r][c]) {
                    count++;
                }
            }
            if (count == 5) {
                return true;
            }
            count = 0;
        }

        return false;
    }

    private static boolean checkIfAnyRowFullyMarked(boolean[][] marked) {
        int count = 0;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (marked[r][c]) {
                    count++;
                }
            }
            if (count == 5) {
                return true;
            }
            count = 0;
        }
        return false;
    }


}
