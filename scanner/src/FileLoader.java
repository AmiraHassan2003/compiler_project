import java.io.*;
import java.util.*;

public class FileLoader {

    public static String readAndExpandFile(String filePath, Set<String> visited) {
        StringBuilder result = new StringBuilder();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return "";
        }

        if (visited.contains(filePath)) {
            return "";
        }

        visited.add(filePath);

        // Check on file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("using(") && line.endsWith(");")) {
                    String included = line.substring(6, line.length() - 2).trim();//to use the text file (text.txt)
                    System.out.println("Including: " + included);
                    result.append(readAndExpandFile(included, visited)).append("\n");//to replace the contect of text.txt to main.txt
                } else {
                    result.append(line).append("\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
