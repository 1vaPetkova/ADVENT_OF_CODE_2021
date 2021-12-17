package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class D3BinaryDiagnosticPt1 {
    private static final String PATH = "src/input/InputDay3.txt";

    public static void main(String[] args) throws IOException {
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();

        List<int[]> binaries = new ArrayList<>();
        Files.readAllLines(Path.of(PATH))
                .forEach(input -> {
                    int[] nums = new int[input.length()];
                    for (int i = 0; i < input.length(); i++) {
                        nums[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
                    }
                    binaries.add(nums);
                });

        int maxLength = binaries.stream().mapToInt(e -> e.length).max().orElse(0);
        int countOnes = 0;
        int countZeros = 0;

        for (int col = 0; col < maxLength; col++) {
            for (int row = 0; row < binaries.size(); row++) {
                int[] currentRow = binaries.get(row);
                if (col < currentRow.length) {
                    if (currentRow[col] == 0) {
                        countZeros++;
                    } else {
                        countOnes++;
                    }
                }
            }
            if (countOnes > countZeros) {
                gamma.append(1);
                epsilon.append(0);
            } else {
                gamma.append(0);
                epsilon.append(1);
            }
            countOnes = 0;
            countZeros = 0;

        }
        long gammaDecimal = Long.parseLong(gamma.toString(), 2);
        long epsilonDecimal = Long.parseLong(epsilon.toString(), 2);
        System.out.println(gammaDecimal * epsilonDecimal);
    }
}

