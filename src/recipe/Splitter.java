package recipe;

import java.util.List;
import java.util.ArrayList;

public class Splitter {
    public static List<String> chopInput(String input) {
        List<String> result = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            return result;
        }
        if (!input.contains(",") && !input.contains(" ")) {
            result.add(input.trim());
        } else {
            // array created using "," as a delimiter
            String[] inputArray = input.split(",");
            for (String i : inputArray) {
                if (!i.trim().isEmpty()) {
                    result.add(i.trim());
                }
            }
        }
        return result;
    }

    public static String listToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append("- ").append(item).append("\n");
        }
        return sb.toString();
    }

    public static String stringToCommaString(String input) {
        if (input == null || input.trim().isEmpty() || input.equals("empty")) {
            return "";
        }

        String[] lines = input.split("\n");
        StringBuilder result = new StringBuilder();
        boolean firstItem = true;

        for (String line : lines) {
            if (line.startsWith("- ")) {
                String item = line.substring(2).trim();
                if (!item.isEmpty()) {
                    if (!firstItem) {
                        result.append(",");
                    }
                    result.append(item);
                    firstItem = false;
                }
            }
        }
        return result.toString();
    }
    
}
