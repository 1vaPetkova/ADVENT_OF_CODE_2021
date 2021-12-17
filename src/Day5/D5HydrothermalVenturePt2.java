package Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class D5HydrothermalVenturePt2 {
    private static final String PATH = "src/input/InputDay5.txt";

    public static void main(String[] args) throws IOException {

        List<Line> lines = readInput();

        HashMap<Point, Integer> crosses = new HashMap<>();
        for (Line line : lines) {
            List<Point> points = line.allPoints();
            points.forEach(point -> {
                crosses.putIfAbsent(point, 0);
                crosses.put(point, crosses.get(point) + 1);
            });
        }
        System.out.println(findCrosses(crosses));
    }

    private static long findCrosses(HashMap<Point, Integer> crosses) {
        List<Integer> list = crosses.values().stream().filter(v -> v > 1).toList();
        return list.size();
    }

    private static List<Line> readInput() throws IOException {
        List<Line> lines = new ArrayList<>();
        Files.readAllLines(Path.of(PATH)).forEach(input -> {
            input = input.replaceAll("\\s+->\\s+", ",");
            int[] ints = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
            Point start = new Point(ints[0], ints[1]);
            Point end = new Point(ints[2], ints[3]);
            lines.add(new Line(start, end));
        });
        return lines;
    }

}


