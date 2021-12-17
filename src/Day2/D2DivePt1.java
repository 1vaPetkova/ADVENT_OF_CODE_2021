package Day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class D2DivePt1 {
    private static final String PATH = "src/input/InputDay2.txt";

    public static void main(String[] args) throws IOException {
        long horizontalPosition = 0;
        long depth = 0;

       List<String> commands = Files.readAllLines(Path.of(PATH));

//        List<String> commands = Arrays.asList(
//                "forward 5",
//                "down 5",
//                "forward 8",
//                "up 3",
//                "down 8",
//                "forward 2"
//        );
        for (String command : commands) {
            String direction = command.split("\\s+")[0];
            int value = Integer.parseInt(command.split("\\s+")[1]);

            switch (direction) {
                case "forward" -> horizontalPosition += value;
                case "up" -> depth -= value;
                case "down" -> depth += value;
            }
        }
        System.out.println(horizontalPosition * depth);

    }
}
