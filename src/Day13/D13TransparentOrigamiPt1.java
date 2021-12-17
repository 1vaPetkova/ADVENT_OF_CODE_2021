package Day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class D13TransparentOrigamiPt1 {

    private static final String PATH = "Day13/input_day_13_ex.txt";

    public static void main(String[] args) throws IOException {
        char[][] sheet = findDots();
        List<String[]> folds = getFolds();
        int dots = 0;
        for (String[] fold : folds) {
            switch (fold[0]) {
                case "y" -> sheet = foldHorizontal(Integer.parseInt(fold[1]), sheet);
                case "x" -> sheet = foldVertical(Integer.parseInt(fold[1]), sheet);
            }

            dots = countDots(sheet);
            System.out.println();
        }
        System.out.println(dots);
    }

    private static char[][] foldVertical(int col, char[][] sheet) {
        int colsFolded = sheet[0].length - col - 1;
        int colsOverlapping = Math.min(colsFolded, col);
        char[][] folded = new char[sheet.length][col];
        for (int r = 0; r < sheet.length; r++) {
            for (int c = 1; c <= colsOverlapping; c++) {
                char above = sheet[col - r][c];
                char below = sheet[col + r][c];
                folded[r][col - c] = getNewChar(above, below);
            }
        }
        if (col > colsOverlapping) {
            for (int r = 0; r < sheet.length; r++) {
                for (int c = 0; c < col - colsOverlapping; c++) {
                    folded[r][c] = sheet[r][c];
                }
            }
        }
        return folded;
    }


    private static char[][] foldHorizontal(int row, char[][] sheet) {
        int rowsFolded = sheet.length - row - 1;
        int rowsOverlapping = Math.min(rowsFolded, row);
        char[][] folded = new char[row][sheet[0].length];
        for (int r = 1; r <= rowsOverlapping; r++) {
            for (int c = 0; c < sheet[r].length; c++) {
                char above = sheet[row - r][c];
                char below = sheet[row + r][c];
                folded[row - r][c] = getNewChar(above, below);
            }
        }
        if (row > rowsOverlapping) {
            for (int r = 0; r < row - rowsOverlapping; r++) {
                for (int c = 0; c < sheet[r].length; c++) {
                    folded[r][c] = sheet[r][c];
                }
            }
        }
        return folded;
    }

    private static int countDots(char[][] sheet) {
        int dots = 0;
        for (int row = 0; row < sheet.length; row++) {
            for (int col = 0; col < sheet[row].length; col++) {
                if (sheet[row][col] == '#') {
                    dots++;
                }
            }
        }
        return dots;
    }

    private static char getNewChar(char above, char below) {
        if ((above == '.') && (below == '#')
                || (above == '#') && (below == '.')
                || (above == '#') && (below == '#')) {
            return '#';
        }
        return '.';
    }

    private static List<String[]> getFolds() throws IOException {
        List<String[]> folds = new ArrayList<>();
        readLines().stream()
                .filter(line -> line.contains("="))
                .forEach(line -> {
                    line = line.substring(line.indexOf("=") - 1);
                    String[] fold = line.split("=");
                    folds.add(fold);
                });
        return folds;
    }

    private static char[][] findDots() throws IOException {
        return getSheet(getDotsCoordinates(), getMaxCoordinates(getDotsCoordinates()));
    }

    private static char[][] getSheet(List<int[]> dots, int[] maxCoord) {
        char[][] sheet = new char[maxCoord[1] + 1][maxCoord[0] + 1];
        for (int row = 0; row < sheet.length; row++) {
            for (int col = 0; col < sheet[row].length; col++) {
                int[] currentCoords = new int[]{row, col};
                if (dots.stream().anyMatch(dot -> currentCoords[1] == dot[0] && currentCoords[0] == dot[1])) {
                    sheet[row][col] = '#';
                    dots.remove(currentCoords);
                } else {
                    sheet[row][col] = '.';
                }
            }
        }
        return sheet;
    }

    private static int[] getMaxCoordinates(List<int[]> dots) {
        int maxX = dots.stream().mapToInt(c -> c[0]).max().orElse(0);
        int maxY = dots.stream().mapToInt(c -> c[1]).max().orElse(0);
        return new int[]{maxX, maxY};
    }

    private static List<int[]> getDotsCoordinates() throws IOException {
        List<int[]> dots = new ArrayList<>();
        readLines().stream().filter(line -> line.contains(","))
                .forEach(line -> {
                    int[] coords = Arrays.stream(line.split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    dots.add(coords);
                });
        return dots;
    }

    private static List<String> readLines() throws IOException {
        return Files.readAllLines(Path.of(PATH));
    }
}
