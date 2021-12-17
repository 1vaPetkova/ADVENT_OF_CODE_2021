package Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class D1SonarSweepPt2 {
    private static final String PATH = "src/input/InputDay1.txt";

    public static void main(String[] args) throws IOException {

        String input = Files.readString(Path.of(PATH));
        int[] depths = Arrays.stream(input.split(System.lineSeparator()))
                .mapToInt(Integer::parseInt)
                .toArray();
      //  int[] depths = {199, 200, 208, 210, 200, 207, 240, 269, 260, 263};


        int increases = 0;
        for (int i = 0; i < depths.length - 3; i++) {
            int fSum = depths[i] + depths[i + 1] + depths[i + 2];
            int sSum = depths[i + 1] + depths[i + 2] + depths[i + 3];
            if (sSum > fSum) {
                increases++;
            }
        }
        System.out.println(increases);
    }
}
