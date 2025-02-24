package recipe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Recipes extends Splitter {
    List<Food> recipeList;
    int count;

    public Recipes() {
        this.recipeList = new ArrayList<Food>();
        this.count = 0;

    }

    public void makeReceipt(String name, String ingredient, String category, String instruction) {
        if (findByName(name) >= 0) {
            System.out.println("Recipe already exists");
            return;
        }
        Food newRecipe = new Food(name, splitInput(ingredient), category, splitInput(instruction));
        recipeList.add(newRecipe);
        count++;
    }

    public void makeReceipt(Food recipe) {
        if (findByName(recipe.getName()) >= 0) {
            System.out.println("Recipe already exists");
            return;
        }
        recipeList.add(recipe);
        count++;
    }

    void updateReceipt(String name, String ingredient, String category, String instruction) {
        int index = findByName(name);
        if (index >= 0) {
            Food newRecipe = new Food(name, splitInput(ingredient), category, splitInput(instruction));
            recipeList.set(index, newRecipe);
        } else {
            System.out.println("RecipeList was not yet created");
        }
    }

    public void recipeInfo(int index) {
        Food food = recipeList.get(index);
        System.out.println("Category: " + food.getCategory());
        System.out.println("Food: " + food.getName());
        System.out.println("Ingredient: " + food.getIngredient());
        System.out.println("Instruction: " + food.getInstruction());

    }

    int findByName(String name) {
        for (Food food : recipeList) {
            if (food.getName().equalsIgnoreCase(name)) {
                return recipeList.indexOf(food);
            }
        }
        return -1;
    }

    int findByIngredient(String ingredient) {
        for (Food food : recipeList) {
            for (String element : food.getIngredient())
                if (element.equalsIgnoreCase(ingredient)) {
                    return recipeList.indexOf(food);
                }
        }
        return -1;
    }
    
    void findByIngredient(List<String> ingredients) {
        List<Food> foundRecipes = new ArrayList<>();
        for (Food food : recipeList) {
            int matchCount = 0;
            for (String ingredient:ingredients) {
            if (food.getIngredient().contains(ingredient)) {
                matchCount++;
            }
        }
        if (matchCount>0) {
            foundRecipes.add(food);
        }
        }
        foundRecipes.sort(new Comparator<Food>() {
            @Override
            public int compare(Food food1, Food food2) {
                int food1MatchCount = (int) food1.getIngredient().stream().filter(ingredients::contains).count();
                int food2MatchCount = (int) food2.getIngredient().stream().filter(ingredients::contains).count();
                return Integer.compare(food2MatchCount, food1MatchCount); // Sort by match count in descending order
            }
        });
        
    }

    public void sortRecipe() {
        int n = recipeList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (recipeList.get(j).getName().compareToIgnoreCase(recipeList.get(j + 1).getName()) > 0) {
                    Food temp = recipeList.get(j);
                    recipeList.set(j, recipeList.get(j + 1));
                    recipeList.set(j + 1, temp);
                }
            }
        }
    }

    public void writeToTxt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("localFood.txt"))) {
            for (Food food : recipeList) {
                writer.write(food.getName() + "|" + food.getIngredient() + "|" + food.getCategory() + "|"
                        + String.join(",", food.getInstruction()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Java cannot write files, permission restricted");
            e.printStackTrace();
        }
    }

    public void readFromTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("localFood.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split("\\|");
                String name = part[0];
                String ingredient = part[1];
                String category = part[2];
                String instruction = part[3];
                Food newRecipe = new Food(name, splitInput(ingredient), category, splitInput(instruction));

                recipeList.add(newRecipe);
                count++;
            }
        } catch (IOException e) {
            System.out.println("Java cannot read files");
            e.printStackTrace();
        }
    }

    public int getCount() {
        return count;
    }

    public int suggestRecipe() {
        Random random = new Random();
        int rand_food = random.nextInt(count);
        return rand_food;
    }

    protected void showAllRecipe() {
        recipeList.sort(Comparator.comparing(Food::getCategory).thenComparing((Food::getName)));

        String currentCategory = "";
        int i = 0;
        for (Food food : recipeList) {
            if (!food.getCategory().equals(currentCategory)) {
                currentCategory = food.getCategory();
                System.out.println("++ Category: " + currentCategory);
            }
            System.out.println((++i) + " " + food.getName());
        }
    }
}
