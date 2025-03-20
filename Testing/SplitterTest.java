package recipe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

class SplitterTest {

    @Test
    void testChopInput() {
        assertEquals(Arrays.asList("Apple", "Banana", "Orange"), Splitter.chopInput("Apple, Banana, Orange"));
        assertEquals(Arrays.asList("Tomato"), Splitter.chopInput("Tomato"));
        assertEquals(Collections.emptyList(), Splitter.chopInput(" "));
        assertEquals(Collections.emptyList(), Splitter.chopInput(null));
    }

    @Test
    void testListToString() {
        List<String> items = Arrays.asList("Milk", "Eggs", "Butter");
        String expected = "- Milk\n- Eggs\n- Butter\n";
        assertEquals(expected, Splitter.listToString(items));
        assertEquals("empty", Splitter.listToString(Collections.emptyList()));
    }

    @Test
    void testStringToCommaString() {
        String input = "- Sugar\n- Flour\n- Cocoa\n";
        assertEquals("Sugar,Flour,Cocoa", Splitter.stringToCommaString(input));
        assertEquals("", Splitter.stringToCommaString("empty"));
        assertEquals("", Splitter.stringToCommaString(" "));
    }
}