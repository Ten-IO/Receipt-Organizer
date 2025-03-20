package recipe;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.Arrays;

class CsvHandlerTest {
    private Recipes recipes;
    private CsvHandler csvHandler;
    private final String testFilename = "test_recipes.csv";

    @BeforeEach
    void setUp() {
        recipes = new Recipes();
        csvHandler = new CsvHandler(recipes);
    }

    @Test
    void testWriteAndReadCsv() {
        Food sampleFood = new Food("Pasta", "Italian", Arrays.asList("Tomato", "Pasta", "Cheese"), Arrays.asList("Boil pasta", "Add sauce"));
        recipes.makeRecipe(sampleFood);
        
        // Write to file
        csvHandler.writeToCsv(testFilename);
        
        // Clear current recipes
        recipes.recipeList.clear();
        
        // Read from file
        csvHandler.readFromCsv(testFilename);
        
        // Verify data integrity
        assertEquals(2, recipes.getCount());
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
