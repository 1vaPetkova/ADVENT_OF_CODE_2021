package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class D6LanternfishPt1 {
    private static final String PATH = "src/input/InputDay6Ex.txt";

    public static void main(String[] args) throws IOException {
        List<Integer> state = getLanternfish();
        int days = 18;
        int finalCount = 0;
        for (int day = 0; day <= days; day++) {
           // System.out.println("day: " + day + " " + state.stream().map(String::valueOf).collect(Collectors.joining(",")));
            System.out.println("day: " + day + " size: " + state.size() + "----" + state.stream().map(String::valueOf).collect(Collectors.joining(",")));
            List<Integer> temp = new ArrayList<>();
            int zeros = 0;
            for (int value : state) {
                int nextValue = value - 1;
                if (value == 0) {
                    nextValue = 6;
                    zeros++;
                }
                temp.add(nextValue);
            }
            state = temp;
            IntStream.range(0, zeros).forEach(i -> temp.add(8));
            if (day == days - 1) {
                finalCount = state.size();
            }
        }
        System.out.println(finalCount);

    }

    private static List<Integer> getLanternfish() throws IOException {
        String s = Files.readString(Path.of(PATH));
        return Arrays
                .stream(s.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
