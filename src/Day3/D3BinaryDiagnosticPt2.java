package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class D3BinaryDiagnosticPt2 {
    private static final String PATH = "src/input/InputDay3.txt";

    public static void main(String[] args) throws IOException {

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

        //Oxygen generator rating
        List<int[]> oxygen = new ArrayList<>(binaries);
        for (int col = 0; col < maxLength; col++) {
            for (int[] currentRow : oxygen) {
                if (col < currentRow.length) {
                    if (currentRow[col] == 0) {
                        countZeros++;
                    } else {
                        countOnes++;
                    }
                }
            }

            int currColumn = col;
            if (oxygen.size() > 1) {
                if (countOnes >= countZeros) {
                    oxygen = oxygen.stream().filter(b -> b[currColumn] == 1).collect(Collectors.toList());
                } else {
                    oxygen = oxygen.stream().filter(b -> b[currColumn] == 0).collect(Collectors.toList());
                }
                countOnes = 0;
                countZeros = 0;
            } else {
                break;
            }
        }

        //CO2 scrubber rating
        List<int[]> carbonDioxide = new ArrayList<>(binaries);
        for (int col = 0; col < maxLength; col++) {
            for (int[] currentRow : carbonDioxide) {
                if (col < currentRow.length) {
                    if (currentRow[col] == 0) {
                        countZeros++;
                    } else {
                        countOnes++;
                    }
                }
            }
            int currColumn = col;
            if (carbonDioxide.size() > 1) {
                int leastCommonBit = countOnes >= countZeros ? 0 : 1;
                carbonDioxide = carbonDioxide.stream()
                        .filter(b -> b[currColumn] == leastCommonBit)
                        .collect(Collectors.toList());
                countOnes = 0;
                countZeros = 0;
            } else {
                break;
            }
        }

        String o2 = Arrays.stream(oxygen.get(0)).mapToObj(String::valueOf).collect(Collectors.joining());
        String co2 = Arrays.stream(carbonDioxide.get(0)).mapToObj(String::valueOf).collect(Collectors.joining());
        long oxy = Long.parseLong(o2, 2);
        long carbonRating = Long.parseLong(co2, 2);
        System.out.println(oxy * carbonRating);
    }
}

