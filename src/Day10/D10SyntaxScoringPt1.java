package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class D10SyntaxScoringPt1 {

    private static final String PATH = "Day10/input_day_10_ex.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readInputs();

        List<Character> corrupted = new ArrayList<>();
        for (String line : lines) {
            checkIfBalanced(line, corrupted);
        }
        int sum = corrupted.stream().mapToInt(D10SyntaxScoringPt1::getPoints).sum();
        System.out.println(sum);
    }


    private static void checkIfBalanced(String line, List<Character> corrupted) {
        ArrayDeque<Character> openBrackets = new ArrayDeque<>();
        for (int i = 0; i < line.length(); i++) {
            char currentBracket = line.charAt(i);
            if (isOpen(currentBracket)) {
                openBrackets.push(currentBracket);
            } else if (isClosed(currentBracket)) {
                if (openBrackets.isEmpty()) {
                    corrupted.add(currentBracket);
                    break;
                } else if (openBrackets.pop() != opposite(currentBracket)) {
                    corrupted.add(currentBracket);
                    break;
                }
            }
        }
    }

    private static int getPoints(char corrupted) {
        return switch (corrupted) {
            case ')' -> 3;
            case ']' -> 57;
            case '}' -> 1197;
            case '>' -> 25137;
            default -> 0;
        };
    }

    private static boolean isClosed(char currentBracket) {
        return switch (currentBracket) {
            case '}', ')', ']', '>' -> true;
            default -> false;
        };
    }


    private static boolean isOpen(char currentBracket) {
        return switch (currentBracket) {
            case '{', '(', '[', '<' -> true;
            default -> false;
        };
    }

    private static Character opposite(Character ch) {
        return switch (ch) {
            case '}' -> '{';
            case ']' -> '[';
            case ')' -> '(';
            case '>' -> '<';
            default -> ' ';
        };
    }


    private static List<String> readInputs() throws IOException {
        return Files.readAllLines(Path.of(PATH));
    }
}
