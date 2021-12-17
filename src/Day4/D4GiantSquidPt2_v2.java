package Day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class D4GiantSquidPt2_v2 {
    private static final String PATH = "src/input/InputDay4Ex.txt";

    private static class Board {
        int[][] nums;
        boolean[][] markedValues;

        public Board(int[][] nums) {
            this.nums = nums;
            this.markedValues = new boolean[5][5];
        }

        public int findSumOfUnmarkedNumbers() {
            int sum = 0;
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    if (!markedValues[row][col]) {
                        sum += nums[row][col];
                    }
                }
            }
            return sum;
        }

        public void markPositions(int currentNumber) {
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    if (currentNumber == nums[row][col]) {
                        markedValues[row][col] = true;
                    }
                }
            }
        }

        public boolean isBingo() {
            return checkIfAnyColFullyMarked() || checkIfAnyRowFullyMarked();
        }

        private boolean checkIfAnyColFullyMarked() {
            for (int c = 0; c < 5; c++) {
                int count = 0;
                for (int r = 0; r < 5; r++) {
                    if (markedValues[r][c]) {
                        count++;
                    }
                }
                if (count == 5) {
                    return true;
                }
            }

            return false;
        }

        private boolean checkIfAnyRowFullyMarked() {
            for (int r = 0; r < 5; r++) {
                int count = 0;
                for (int c = 0; c < 5; c++) {
                    if (markedValues[r][c]) {
                        count++;
                    }
                }
                if (count == 5) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(PATH));

        List<Integer> numbers = Arrays.stream(lines.get(0).split(","))
                .map(Integer::parseInt).toList();
        int finalValue = 0;

        List<Board> boards = new ArrayList<>();

        lines = lines.stream().filter(l -> !l.isBlank()).skip(1).collect(Collectors.toList());

        for (int row = 0; row < lines.size(); row += 5) {
            int[][] matrix = new int[5][5];
            for (int i = 0; i < 5; i++) {
                String nums = lines.get(row + i);
                matrix[i] = Arrays.stream(nums.trim().split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }
            Board board = new Board(matrix);
            boards.add(board);
        }

        //mark boards
        Board latestBingoBoard = null;

        for (int currentNumber : numbers) {
            List<Board> temp = new ArrayList<>();
            for (Board board : boards) {
                board.markPositions(currentNumber);
                if (board.isBingo()) {
                    latestBingoBoard = board;
                    finalValue = currentNumber;
                } else {
                    temp.add(board);
                }
            }
            boards = temp;
        }


        assert latestBingoBoard != null;
        System.out.println(latestBingoBoard.findSumOfUnmarkedNumbers() * finalValue);
    }
}
