package recipe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class FoodTest {

    @Test
    void testFoodConstructorAndGetters() {
        List<String> ingredients = Arrays.asList("Tomato", "Pasta", "Cheese");
        List<String> instructions = Arrays.asList("Boil pasta", "Add sauce");
        Food food = new Food("Pasta", "Italian", ingredients, instructions);
        
        assertEquals("Pasta", food.getName());
        assertEquals("Italian", food.getCategory());
        assertEquals(ingredients, food.getIngredient());
        assertEquals(instructions, food.getInstruction());
    }

    @Test
    void testSetters() {
        Food food = new Food("Pasta", "Italian", new ArrayList<>(), new ArrayList<>());
        
        food.setName("Spaghetti");
        food.setCategory("Italian Cuisine");
        food.setIngredient(Arrays.asList("Noodles", "Tomato Sauce"));
        food.setInstruction(Arrays.asList("Boil water", "Cook noodles"));
        
        assertEquals("Spaghetti", food.getName());
        assertEquals("Italian Cuisine", food.getCategory());
        assertEquals(Arrays.asList("Noodles", "Tomato Sauce"), food.getIngredient());
        assertEquals(Arrays.asList("Boil water", "Cook noodles"), food.getInstruction());
    }

    @Test
    void testSplitIngredient() {
        Food food = new Food("Salad", "Healthy", new ArrayList<>(), new ArrayList<>());
        List<String> ingredients = food.splitIngredient("Lettuce, Tomato, Cucumber");
        assertEquals(Arrays.asList("Lettuce", "Tomato", "Cucumber"), ingredients);
    }

    @Test
    void testSplitInstruction() {
        Food food = new Food("Salad", "Healthy", new ArrayList<>(), new ArrayList<>());
        List<String> instructions = food.splitInstruction("Chop vegetables, Mix ingredients");
        assertEquals(Arrays.asList("Chop vegetables", "Mix ingredients"), instructions);
    }
}