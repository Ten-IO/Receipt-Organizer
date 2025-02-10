package recipe;

import java.util.ArrayList;

public class MakeReceipt extends Food {
    ArrayList<Food> recipe = new ArrayList<Food>();

    MakeReceipt(String name, String ingredient, String category, String instruction) {
        super(name, ingredient, category, instruction);
        if (findByName(name) >= 0) {
            System.out.println("Recipe already exists");
            return;
        }
        recipe.add(new Food(name, ingredient, category, instruction));
    }

    void UpdateReceipt(String name, String ingredient, String category, String instruction) {
        int index = findByName(name);
        if (index != -1) {
            recipe.add(index, new Food(name, ingredient, category, instruction));
        } else {
            System.out.println("Recipe was not yet created");
        }
    }

    void recipeInfo() {
        System.out.println("Category: " + getCategory());
        System.out.println("Food: " + getName());
        System.out.println("Ingredient: " + getIngredient());
        System.out.println("Instruction: " + getInstruction());
    }

    int findByName(String name) {
        for (Food food : recipe) {
            if (food.getName().equalsIgnoreCase(name)) {
                return recipe.indexOf(food);
            }
        }
        return -1;
    }

    int findByIngredient(String ingredient) {
        for (Food food : recipe) {
            if (food.getIngredient().equalsIgnoreCase(ingredient)) {
                return recipe.indexOf(food);
            }
        }
        return -1;
    }

    void sortRecipe() {
        for (Food food : recipe) {
            int n = recipe.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    Food temp = recipe.get(j);
                    recipe.add(i, recipe.get(i + 1));
                    recipe.add(i + 1, temp);
                }
            }
        }
    }

}
