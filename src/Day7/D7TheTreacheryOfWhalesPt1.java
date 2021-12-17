package Day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D7TheTreacheryOfWhalesPt1 {

    private static final String PATH = "src/input/InputDay7.txt";

    public static void main(String[] args) throws IOException {
        int[] positions = findStartPositions();
        int maxPosition = Arrays.stream(positions).max().orElse(0);

        List<Integer> fuelConsumed = new ArrayList<>();
        for (int i = 0; i < maxPosition; i++) {
            int fuel = 0;
            for (int position : positions) {
                fuel += Math.abs(position - i);
            }
            fuelConsumed.add(fuel);
        }
        int min = fuelConsumed.stream().mapToInt(e -> e).min().orElse(0);
        System.out.println(min);

    }

    private static int[] findStartPositions() throws IOException {
        return Arrays.stream(Files.readString(Path.of(PATH)).trim().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
