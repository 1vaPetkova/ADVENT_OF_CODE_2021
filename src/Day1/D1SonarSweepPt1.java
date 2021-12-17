package Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class D1SonarSweepPt1 {
    private static final String PATH = "src/input/InputDay1.txt";

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of(PATH));
        int[] depths = Arrays.stream(input.split(System.lineSeparator()))
                .mapToInt(Integer::parseInt)
                .toArray();
        int increases = 0;
        for (int i = 0; i < depths.length - 1; i++) {
            if (depths[i + 1] > depths[i]) {
                increases++;
            }
        }
        System.out.println(increases);
    }
}
