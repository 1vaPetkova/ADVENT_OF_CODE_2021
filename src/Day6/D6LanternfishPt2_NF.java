package Day6;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class D6LanternfishPt2_NF {
    private static final String PATH = "src/input/InputDay6.txt";
    private static final int DAYS = 256;

    public static void main(String[] args) throws IOException {
        List<Integer> initialState = getLanternfish();
        HashMap<Integer, Long> map = getStartMap(initialState);
        for (int day = 1; day <= DAYS; day++) {
            long babies = map.get(8);
            long temp = 0;
            for (int i = 7; i >= 0; i--) {
                temp = map.get(i);
                map.put(i, babies);
                babies = temp;
            }
            map.put(6, map.get(6) + babies);
            map.put(8, babies);
        }
        long sum = map.values().stream().mapToLong(e -> e).sum();
        System.out.println(sum);
    }


    private static HashMap<Integer, Long> getStartMap(List<Integer> initialState) {
        HashMap<Integer, Long> map = new HashMap<>();
        map.put(0, 0L);
        map.put(1, 0L);
        map.put(2, 0L);
        map.put(3, 0L);
        map.put(4, 0L);
        map.put(5, 0L);
        map.put(6, 0L);
        map.put(7, 0L);
        map.put(8, 0L);
        for (Integer num : initialState) {
            map.put(num, map.get(num) + 1);
        }
        return map;
    }


    private static List<Integer> getLanternfish() throws IOException {
        return Arrays
                .stream(Files.readString(Path.of(PATH)).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

    }
}

