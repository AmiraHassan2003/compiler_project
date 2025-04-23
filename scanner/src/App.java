import java.util.*;

public class App {

    private static final Map<String, String> keywords = new HashMap<>();
    private static final Map<String, String> operators = new HashMap<>();
    private static final Map<String, String> comments = new HashMap<>();

    static {
        // Keywords
        keywords.put("Division", "Class");
        keywords.put("InferedFrom", "Inheritance");
        keywords.put("WhetherDo", "Condition");
        keywords.put("Ire", "Integer");
        keywords.put("Sire", "SInteger");
        keywords.put("Clo", "Character");
        keywords.put("SetOfClo", "String");
        keywords.put("FBU", "Float");
        keywords.put("SFBU", "SFloat");
        keywords.put("None", "Void");
        keywords.put("Logical", "Boolean");
        keywords.put("TerminateThis", "Break");
        keywords.put("RotateWhen", "Loop");
        keywords.put("ContinueWhen", "Loop");
        keywords.put("ReplyWith", "Return");
        keywords.put("Seop", "Struct");
        keywords.put("Check", "Switch");
        keywords.put("Program", "Start Statement");
        keywords.put("End", "End Statement");
        keywords.put("using", "Inclusion");
        keywords.put("read", "Read Statement");
        keywords.put("write", "Write Statement");

        // Operators
        operators.put("+", "Arithmetic Operator");
        operators.put("-", "Arithmetic Operator");
        operators.put("*", "Arithmetic Operator");
        operators.put("/", "Arithmetic Operator");

        operators.put("==", "Relational Operator");
        operators.put("!=", "Relational Operator");
        operators.put("<", "Relational Operator");
        operators.put(">", "Relational Operator");
        operators.put("<=", "Relational Operator");
        operators.put(">=", "Relational Operator");

        operators.put("&&", "Logical Operator");
        operators.put("||", "Logical Operator");
        operators.put("~", "Logical Operator");

        operators.put("=", "Assignment Operator");
        operators.put(".", "Access Operator");

        operators.put("{", "Brace");
        operators.put("}", "Brace");
        operators.put("[", "Bracket");
        operators.put("]", "Bracket");
        operators.put("(", "Brace");
        operators.put(")", "Brace");
        operators.put(";", "Line Delimiter");
        operators.put(",", "Comma");

        // Comments
        comments.put("/-", "comment");
        comments.put("/##", "comment");
        comments.put("##/", "comment");
    }

    public static String getTokenType(String token) {
        if (keywords.containsKey(token)) return keywords.get(token);
        else if (operators.containsKey(token)) return operators.get(token);
        else if (comments.containsKey(token)) return comments.get(token);
        else if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) return "Identifier"; 
        else if (token.matches("\\d+")) return "Constant";
        else return "Unknown";
    }

    public static String removeCommentsAndWhitespace(String code) {
        StringBuilder cleaned = new StringBuilder();
        String[] lines = code.split("\n");
        boolean insideMultiComment = false;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            if (trimmed.contains("/##")) {
                insideMultiComment = true;
                continue;
            }
            if (trimmed.contains("##/")) {
                insideMultiComment = false;
                continue;
            }
            if (insideMultiComment || trimmed.startsWith("/-")) continue;

            cleaned.append(trimmed).append("\n");
        }
        return cleaned.toString();
    }

    public static void main(String[] args) {
        Set<String> visitedFiles = new HashSet<>();
        String inputCode = FileLoader.readAndExpandFile("main.txt", visitedFiles);
        String cleanedCode = removeCommentsAndWhitespace(inputCode);
        System.out.println("Cleaned Code:\n" + cleanedCode);

        String[] lines = cleanedCode.split("\n");
        int lineNum = 1;

        for (String line : lines) {
            String[] tokens = line.split("\\s+|(?=[;{}()\\[\\],.=+*/<>!&|~])|(?<=[;{}()\\[\\],.=+*/<>!&|~])");
            for (String token : tokens) {
                token = token.trim();
                if (!token.isEmpty()) {
                    System.out.printf("Line: %-2d Token: %-12s => Type: %s\n", lineNum, token, getTokenType(token));
                }
            }
            lineNum++;
        }
    }
}
