package Day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class D13TransparentOrigami_v2 {

    private static final String PATH = "Day13/input_day_13.txt";

    public static void main(String[] args) throws IOException {
        Set<Dot> dots = getDotsCoordinates();
        List<String[]> folds = getFolds();
        for (String[] fold : folds) {
            switch (fold[0]) {
                case "y" -> dots = foldHorizontal(Integer.parseInt(fold[1]), dots);
                case "x" -> dots = foldVertical(Integer.parseInt(fold[1]), dots);
            }
      //      System.out.println(dots.size());
        }
        printDots(dots);
    }

    private static void printDots(Set<Dot> dots) {
        int maxX = dots.stream().mapToInt(Dot::getX).max().orElse(0);
        int maxY = dots.stream().mapToInt(Dot::getY).max().orElse(0);
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                Dot dot = new Dot(x, y);
                if (dots.contains(dot)) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static Set<Dot> foldVertical(int col, Set<Dot> dots) {
        Set<Dot> temp = new LinkedHashSet<>();
        for (Dot dot : dots) {
            Dot tmp = new Dot(0, 0);
            tmp.y = dot.y;
            if (dot.x > col) {
                int delta = dot.x - col;
                tmp.x = col - delta;
            } else {
                tmp.x = dot.x;
            }
            temp.add(tmp);
        }
        return temp;
    }

    private static Set<Dot> foldHorizontal(int row, Set<Dot> dots) {
        Set<Dot> temp = new LinkedHashSet<>();
        for (Dot dot : dots) {
            Dot tmp = new Dot(0, 0);
            tmp.x = dot.x;
            if (dot.y > row) {
                int delta = dot.y - row;
                tmp.y = row - delta;
            } else {
                tmp.y = dot.y;
            }
            temp.add(tmp);
        }
        return temp;
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

    private static Set<Dot> getDotsCoordinates() throws IOException {
        Set<Dot> dots = new LinkedHashSet<>();
        readLines().stream().filter(line -> line.contains(","))
                .forEach(line -> {
                    int[] coords = Arrays.stream(line.split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    dots.add(new Dot(coords[0], coords[1]));
                });
        return dots;
    }

    private static List<String> readLines() throws IOException {
        return Files.readAllLines(Path.of(PATH));
    }

    public static class Dot {
        int x;
        int y;

        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dot dot = (Dot) o;
            return x == dot.x && y == dot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }


}
