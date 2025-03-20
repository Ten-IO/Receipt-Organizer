package recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

class RecipesTest {
    private Recipes recipes;
    private Food sampleFood;

    @BeforeEach
    void setUp() {
        recipes = new Recipes();
        sampleFood = new Food("Pasta", "Italian", Arrays.asList("Tomato", "Pasta", "Cheese"), Arrays.asList("Boil pasta", "Add sauce"));
    }

    @Test
    void testMakeRecipe() {
        recipes.makeRecipe(sampleFood);
        assertEquals(1, recipes.getCount());
        assertEquals("Pasta", recipes.recipeList.get(0).getName());
    }

    @Test
    void testDuplicateRecipe() {
        recipes.makeRecipe(sampleFood);
        recipes.makeRecipe(sampleFood);
        assertEquals(1, recipes.getCount(), "Duplicate recipes should not be added");
    }

    @Test
    void testUpdateRecipe() {
        recipes.makeRecipe(sampleFood);
        recipes.updateRecipe("Pasta", "Italian", "Tomato, Pasta, Cheese, Basil", "Boil pasta, Add sauce, Add basil");
        Food updatedFood = recipes.recipeList.get(0);
        assertTrue(updatedFood.getIngredient().contains("Basil"));
    }

    @Test
    void testDeleteRecipe() {
        recipes.makeRecipe(sampleFood);
        recipes.deleteRecipe("Pasta");
        assertEquals(0, recipes.getCount());
    }

    @Test
    void testFindByName() {
        recipes.makeRecipe(sampleFood);
        int index = recipes.findByName("Pasta");
        assertEquals(0, index);
    }

    @Test
    void testFindByIngredient() {
        recipes.makeRecipe(sampleFood);
        List<Food> result = recipes.findByIngredient(Arrays.asList("Tomato"));
        assertFalse(result.isEmpty());
    }

    @Test
    void testSuggestRecipe() {
        recipes.makeRecipe(sampleFood);
        int index = recipes.suggestRecipe();
        assertTrue(index >= 0 && index < recipes.getCount());
    }
}
