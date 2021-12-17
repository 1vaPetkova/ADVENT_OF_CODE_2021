package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class D8SevenSegmentSearchPt2 {
    private static final String PATH = "Day8/input_day_8.txt";
    private static HashMap<Integer, String> positions = new HashMap<>();
    private static HashMap<Integer, String> map = new HashMap<>();
    private static HashMap<Integer, Set<String>> sets = new HashMap<>();

    public static void main(String[] args) throws IOException {
        long sum = 0;

        List<String> lines = readAllLines();
        for (String line : lines) {
            String[] tenDigits = readTenDigits(line);
            getDigits(tenDigits);
            splitDigits();
            String[] fourDigits = readFourDigits(line);
            sum+=getFinalNumber(fourDigits);
            map = new HashMap<>();
            sets = new HashMap<>();
            positions = new HashMap<>();
        }
        System.out.println(sum);
    }


    private static int getFinalNumber(String[] digits) {
        StringBuilder sb = new StringBuilder();
        outerLoop:
        for (String digit : digits) {
            Set<String> currentDigit = Arrays.stream(digit.split("")).collect(Collectors.toSet());
            for (Map.Entry<Integer, Set<String>> set : sets.entrySet()) {
                Set<String> value = set.getValue();
                if (value.size() != currentDigit.size()) {
                    continue;
                }

                if (currentDigit.containsAll(value)) {
                    sb.append(set.getKey());
                    continue outerLoop;
                }
            }
        }
        return Integer.parseInt(sb.toString());
    }


    private static void splitDigits() {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Set<String> set = Arrays.stream(entry.getValue().split("")).collect(Collectors.toSet());
            sets.put(entry.getKey(), set);
        }
    }

    private static void getDigits(String[] digits) {
        List<String> sixLettersDigits = new ArrayList<>();
        List<String> fiveLettersDigits = new ArrayList<>();
        for (String digit : digits) {

            switch (digit.length()) {
                case 2 -> map.put(1, digit);
                case 3 -> map.put(7, digit);
                case 4 -> map.put(4, digit);
                case 5 -> fiveLettersDigits.add(digit);
                case 6 -> sixLettersDigits.add(digit);
                case 7 -> map.put(8, digit);
            }
        }
        findPositionZero(map);
        findDigitSix(sixLettersDigits, map);
        findDigitNine(sixLettersDigits, map);
        findDigitZero(sixLettersDigits, map);
        findDigitFive(fiveLettersDigits, map);
        findDigitTwo(fiveLettersDigits, map);
        findDigitThree(fiveLettersDigits, map);
    }

    private static void findDigitThree(List<String> fiveLettersDigits, HashMap<Integer, String> map) {
        map.put(3, fiveLettersDigits.get(0));
    }

    private static void findDigitTwo(List<String> fiveLettersDigits, HashMap<Integer, String> map) {
        String two = fiveLettersDigits.stream().filter(str -> !str.contains(positions.get(6)))
                .findFirst().orElse(null);
        map.put(2, two);
        fiveLettersDigits.remove(two);
    }

    private static void findDigitFive(List<String> fiveLettersDigits, HashMap<Integer, String> map) {
        String five = fiveLettersDigits.stream().filter(str -> !str.contains(positions.get(5)))
                .findFirst().orElse(null);
        map.put(5, five);
        fiveLettersDigits.remove(five);
    }

    private static void findDigitZero(List<String> sixLettersDigits, HashMap<Integer, String> map) {
        map.put(0, sixLettersDigits.get(0));
        sixLettersDigits.remove(0);
        String nine = map.get(9);
        String zero = map.get(0);
        String intersection = zero;
        for (int i = 0; i < nine.length(); i++) {
            String current = String.valueOf(nine.charAt(i));
            if (!zero.contains(current)) {
                positions.put(1, current);
            }
            intersection = intersection.replace(current, "");
        }
        positions.put(4, intersection);
        String positionThree = map.get(4);
        positionThree = positionThree.replace(positions.get(1), "");
        positionThree = positionThree.replace(positions.get(5), "");
        positionThree = positionThree.replace(positions.get(6), "");
        positions.put(3, positionThree);
    }

    private static void findDigitNine(List<String> sixLettersDigits, HashMap<Integer, String> map) {
        String four = map.get(4);
        String one = map.get(1);
        String intersection = four.replace(String.valueOf(one.charAt(0)), "");
        intersection = intersection.replace(String.valueOf(one.charAt(1)), "");
        char fInt = intersection.charAt(0);
        char sInt = intersection.charAt(1);
        String nine = sixLettersDigits.stream()
                .filter(str -> str.contains(String.valueOf(fInt))
                        && str.contains(String.valueOf(sInt)))
                .findFirst().orElse(null);
        map.put(9, nine);
        sixLettersDigits.remove(nine);
        String positionTwo = nine;
        for (int i = 0; i < four.length(); i++) {
            positionTwo = positionTwo.replace(String.valueOf(four.charAt(i)), "");
        }
        positionTwo = positionTwo.replace(positions.get(0), "");
        positions.put(2, positionTwo);
    }

    private static void findDigitSix(List<String> sixLettersDigits, HashMap<Integer, String> map) {
        String one = map.get(1);
        String six = sixLettersDigits.stream()
                .filter(str -> !str.contains(String.valueOf(one.charAt(0)))
                        || !str.contains(String.valueOf(one.charAt(1)))).findFirst().orElse(null);
        if (six.contains(String.valueOf(one.charAt(0)))) {
            positions.put(6, String.valueOf(one.charAt(0)));
            positions.put(5, String.valueOf(one.charAt(1)));
        } else {
            positions.put(6, String.valueOf(one.charAt(1)));
            positions.put(5, String.valueOf(one.charAt(0)));
        }
        map.put(6, six);
        sixLettersDigits.remove(six);
    }

    private static void findPositionZero(HashMap<Integer, String> map) {
        String s = map.get(7);
        s = s.replace(map.get(1).charAt(0), ' ');
        s = s.replace(map.get(1).charAt(1), ' ');
        s = s.trim();
        positions.put(0, s);
    }

    private static HashMap<Integer, String> getPositions() {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i <= 6; i++) {
            map.put(i, "");
        }
        return map;
    }


    private static String[] readTenDigits(String line) {
        line = line.substring(0, line.indexOf("|") - 1);
        return line.split("\\s+");
    }

    private static String[] readFourDigits(String line) {
        line = line.substring(line.indexOf("|") + 2);
        return line.split("\\s+");
    }

    private static List<String> readAllLines() throws IOException {
        return Files.readAllLines(Path.of(PATH));
    }

}
