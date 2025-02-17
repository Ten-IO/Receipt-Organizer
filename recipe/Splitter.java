package recipe;

import java.util.List;
import java.util.ArrayList;

public class Splitter {
    public static List<String> splitInput(String input) {
        List<String> result = new ArrayList<>();
        // if empty array put in "none"
        if (!input.contains(",") && !input.contains(" ")) {
            result.add("none");
        } else {
            // input turn to array with "," as a delimiter
            String[] inputArray = input.split("[,]");
            // loop that array divide by delimiter and fill
            for (String i : inputArray) {
                if (!i.trim().isEmpty()) {
                    result.add(i.trim());
                }
            }
        }
        return result;
    }
}
