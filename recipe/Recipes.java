package recipe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Recipes {
    public List<Food> recipeList;
    int count;
    public Recipes() {
        this.recipeList = new ArrayList<Food>();
        this.count = 0;

    }

    public void makeRecipe(String name, String ingredient, String category, String instruction) {
        if (findByName(name) >= 0) {
            System.out.println("Recipe already exists");
            return;
        }
        Food newRecipe = new Food(name, Splitter.chopInput(ingredient), category, Splitter.chopInput(instruction));
        recipeList.add(newRecipe);
        count++;
    }

    public void makeRecipe(Food recipe) {
        if (findByName(recipe.getName()) >= 0) {
            System.out.println("Recipe already exists");
            return;
        }
        recipeList.add(recipe);
        count++;
    }

    public void updateRecipe(String name, String ingredient, String category, String instruction) {
        int index = findByName(name);
        if (index >= 0) {
            Food newRecipe = new Food(name, Splitter.chopInput(ingredient), category, Splitter.chopInput(instruction));
            recipeList.set(index, newRecipe);
        } else {
            System.out.println("Recipe was not yet created");
        }
    }

    public void deleteRecipe(String name) {
        int index = findByName(name);
        System.out.println(index);
        if(index >=0) {
            recipeList.remove(index);
            count --;
        } else {
            System.out.println("recipe not found");
        }
    }

    public Food recipeInfoIndex(int index) {
        return recipeList.get(index);
    }

    public int findByName(String name) {
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
    
    public List<Food> findByIngredient(List<String> ingredients) {
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
                return Integer.compare(food2MatchCount, food1MatchCount); // descending order
            }
        });
        return foundRecipes;
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

    public void showAllRecipe() {
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
    public int getCount() {
        return count;
    }

    public int suggestRecipe() {
        Random random = new Random();
        int rand_food = random.nextInt(count);
        return rand_food;
    }
}
