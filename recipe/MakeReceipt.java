package recipe;

import java.util.ArrayList;

public class MakeReceipt extends Food {
    ArrayList<Food> recipe = new ArrayList<Food>();

    MakeReceipt(String name, String ingredient, String category) {
        super(name, ingredient, category);
        recipe.add(new Food(name, ingredient, category));
    }

    void receiptInfo() {
        System.out.println("Category: " + getCategory());
        System.out.println("Food: " + getName());
        System.out.println("Ingredient: " + getIngredient());
    }

    Food findByName(String name) {
        for (Food food : recipe) {
            if (food.getName().equalsIgnoreCase(name));
            return food;
        }
        return null;
    }

}
