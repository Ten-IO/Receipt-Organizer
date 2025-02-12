package recipe;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Recipes extends Food {
    ArrayList<Food> recipe = new ArrayList<Food>();
    int count = 0;

    public Recipes(String name, String ingredient, String category, String instruction) {
        super(name, ingredient, category, instruction);
    }

    public void makeReceipt(String name, String ingredient, String category, String instruction) {
        if (findByName(name) >= 0) {
            System.out.println("Recipe already exists");
            return;
        }
        recipe.add(new Food(name, ingredient, category, instruction));
        count ++;
    }
    public void makeReceipt() {
        if (findByName(this.getName()) >= 0) {
            System.out.println("Recipe already exists");
            return;
        }
        recipe.add(new Food(this.getName(), this.getIngredient(), this.getCategory(), this.getInstruction()));
        count ++;
    }

    void updateReceipt(String name, String ingredient, String category, String instruction) {
        int index = findByName(name);
        if (index >= 0) {
            recipe.set(index, new Food(name, ingredient, category, instruction));
        } else {
            System.out.println("Recipe was not yet created");
        }
    }

    void recipeInfo(int index) {
        Food food = recipe.get(index);
        System.out.println("Category: " + food.getCategory());
        System.out.println("Food: " + food.getName());
        System.out.println("Ingredient: " + food.getIngredient());
        System.out.println("Instruction: " + food.getInstruction());
        
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
        int n = recipe.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (recipe.get(j).getName().compareToIgnoreCase(recipe.get(j).getName()) > 0) {
                    Food temp = recipe.get(j);
                    recipe.set(i, recipe.get(i + 1));
                    recipe.set(i + 1, temp);
                }
            }
        }
    }

    void writeToTxt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("localFood.txt", true))) {
            for (Food food : recipe) {
                writer.write(food.getName() + "|" + food.getIngredient() + "|" + food.getCategory() + "|"
                        + String.join(",", food.getInstruction()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Java cannot write files, permission restricted");
            e.printStackTrace();
        }
    }

    void readFromTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("localFood.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split("\\|");
                String name = part[0];
                String ingredient = part[1];
                String category = part[2];
                String instruction = part[3];
                recipe.add(new Food(name, ingredient, category, instruction));
            }
        } catch (IOException e) {
            System.out.println("Java cannot read files");
            e.printStackTrace();
        }
    }
    
    public int getCount() {
        return count;
    }
}
