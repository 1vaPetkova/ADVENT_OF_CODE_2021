package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class D10SyntaxScoringPt2 {

    private static final String PATH = "Day10/input_day_10.txt";

    public static void main(String[] args) throws IOException {
        List<String> incomplete = getIncompleteLines();

        List<Long> scores = new ArrayList<>();

        for (String line : incomplete) {
            List<Character> closingBrackets = getClosingBrackets(line);
            scores.add(getScore(closingBrackets));
        }
        Collections.sort(scores);
        System.out.println(scores.get(scores.size() / 2));
    }

    private static long getScore(List<Character> closedBrackets) {
        List<Integer> values = closedBrackets.stream()
                .map(D10SyntaxScoringPt2::getPoints)
                .toList();
        long score = 0;
        for (Integer value : values) {
            score = score * 5 + value;
        }
        return score;
    }

    private static int getPoints(char corrupted) {
        return switch (corrupted) {
            case ')' -> 1;
            case ']' -> 2;
            case '}' -> 3;
            case '>' -> 4;
            default -> 0;
        };
    }

    private static List<Character> getClosingBrackets(String line) {
        ArrayDeque<Character> openBrackets = new ArrayDeque<>();
        for (char currentBracket : line.toCharArray()) {
            if (isOpen(currentBracket)) {
                openBrackets.push(currentBracket);
            } else if (openBrackets.peek() == opposite(currentBracket)) {
                openBrackets.pop();
            }
        }
        return openBrackets
                .stream()
                .map(D10SyntaxScoringPt2::opposite)
                .collect(Collectors.toList());
    }

    private static List<String> getIncompleteLines() throws IOException {
        return Files.readAllLines(Path.of(PATH)).stream()
                .filter(D10SyntaxScoringPt2::isBalanced)
                .toList();
    }

    private static boolean isBalanced(String line) {
        ArrayDeque<Character> openBrackets = new ArrayDeque<>();
        for (char currentBracket : line.toCharArray()) {
            if (isOpen(currentBracket)) {
                openBrackets.push(currentBracket);
            } else if (isClosed(currentBracket)) {
                if (openBrackets.isEmpty()) {
                    return false;
                } else if (openBrackets.pop() != opposite(currentBracket)) {
                    return false;
                }
            }
        }
        return true;
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
            case '{' -> '}';
            case '[' -> ']';
            case '(' -> ')';
            case '<' -> '>';
            default -> ' ';
        };
    }
}
