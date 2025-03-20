package recipe;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.Arrays;

class TxtHandlerTest {
    private Recipes recipes;
    private TxtHandler txtHandler;
    private final String testFilename = "test_recipes.txt";

    @BeforeEach
    void setUp() {
        recipes = new Recipes();
        txtHandler = new TxtHandler(recipes);
    }

    @Test
    void testWriteAndReadTxt() {
        Food sampleFood = new Food("Pasta", "Italian", Arrays.asList("Tomato", "Pasta", "Cheese"), Arrays.asList("Boil pasta", "Add sauce"));
        recipes.makeRecipe(sampleFood);
        
        // Write to file
        txtHandler.writeToTxt(testFilename);
        
        // Clear current recipes
        recipes.recipeList.clear();
        
        // Read from file
        txtHandler.readFromTxt(testFilename);
        
        // Verify data integrity
        assertEquals(1, recipes.getCount());
        Food loadedFood = recipes.recipeList.get(0);
        assertEquals("Pasta", loadedFood.getName());
        assertEquals("Italian", loadedFood.getCategory());
        assertTrue(loadedFood.getIngredient().contains("Tomato"));
        assertTrue(loadedFood.getInstruction().contains("Boil pasta"));
    }

    @AfterEach
    void cleanUp() {
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }
}