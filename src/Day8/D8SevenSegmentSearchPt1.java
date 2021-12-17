package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class D8SevenSegmentSearchPt1 {
    private static final String PATH = "Day8/input_day_8.txt";

    public static void main(String[] args) throws IOException {
        List<String[]> inputs = readDigits();
        int count = 0;
        for (String[] input : inputs) {
            for (String digit : input) {
                switch (digit.length()) {
                    case 2, 3, 4, 7 -> count++;
                }
            }
        }
        System.out.println(count);
    }

    private static List<String[]> readDigits() throws IOException {
        List<String> inputs = new ArrayList<>();
        Files.readAllLines(Path.of(PATH)).forEach(s -> inputs.add(s.substring(s.indexOf("|") + 1)));
        List<String[]> digits = new ArrayList<>();
        inputs.forEach(line -> digits.add(line.split("\\s+")));
        return digits;
    }

}
