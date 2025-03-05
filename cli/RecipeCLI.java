package cli;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import recipe.CsvHandler;
import recipe.Food;
import recipe.Recipes;
import recipe.Splitter;
import recipe.TxtHandler;

public class RecipeCLI {

    private static Scanner scanner = new Scanner(System.in);
    private static Recipes recipes = new Recipes();

    public static void main(String[] args) {

        CsvHandler csvHandler = new CsvHandler(recipes);
        csvHandler.readFromCsv("localfood.csv");
        TxtHandler txtHandler = new TxtHandler(recipes);
        txtHandler.readFromTxt("src/files/localFood.txt");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            csvHandler.writeToCsv("localfood.csv");
            txtHandler.writeToTxt("localFood.txt");
            System.out.println("Auto-saving to localfood.csv...");
        }));

        while (true) {
            System.out.println("\n=== Recipe Management CLI ===");
            System.out.println("1. Add Recipe");
            System.out.println("2. Update Recipe");
            System.out.println("3. Delete Recipe");
            System.out.println("4. View Recipe Info");
            System.out.println("5. Find Recipe by Name");
            System.out.println("6. Find Recipe by Ingredient");
            System.out.println("7. Sort Recipes by Name");
            System.out.println("8. Suggest a Recipe");
            System.out.println("9. Show current recipes");
            System.out.println("10. Exit");
            System.out.print("\n=> Type your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addRecipe();
                    break;
                case 2:
                    updateRecipe();
                    break;
                case 3:
                    deleteRecipe();
                    break;
                case 4:
                    clearScreen();
                    viewRecipeInfo();
                    break;
                case 5:
                    clearScreen();
                    findRecipeByName();
                    break;
                case 6:
                    clearScreen();
                    findRecipeByIngredient();
                    break;
                case 7:
                    sortRecipes();
                    clearScreen();
                    break;
                case 8:
                    clearScreen();
                    suggestRecipe();
                    break;
                case 9:
                    clearScreen();
                    showAllRecipe();
                    break;
                case 10:
                    saveRecipes();
                    clearScreen();
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    clearScreen();
            }
        }
    }

    private static void addRecipe() {
        System.out.println("== Recipe Construction mode == ");
        System.out.print("Food name: ");
        String name = scanner.nextLine();
        System.out.print("Enter ingredients (comma-separated): ");
        String ingredients = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter instructions (comma-separated): ");
        String instructions = scanner.nextLine();
        System.out.print("Confirm ? y (accept): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            recipes.makeRecipe(name, category, ingredients, instructions);
            System.out.println("Recipe added successfully.");
        } else {
            System.out.print("Cancelled ...");
            clearScreen();
            return;
        }
    }

    private static void updateRecipe() {
        System.out.println("== Updating Mode ==");
        System.out.print("Enter the name of the recipe to update: ");
        String name = scanner.nextLine();
        int index = recipes.findByName(name);

        if (index >= 0) {
            System.out.print("New ingredients (comma-separated): ");
            String ingredients = scanner.nextLine();
            System.out.print("Enter new category: ");
            String category = scanner.nextLine();
            System.out.print("New instructions (comma-separated): ");
            String instructions = scanner.nextLine();
            System.out.print("Confirm ? y (accept): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                recipes.updateRecipe(name, category, ingredients, instructions);
                System.out.println("Recipe updated successfully.");
            } else {
                System.out.print("Cancelled ...");
                clearScreen();
                return;
            }

        } else {
            System.out.println("Recipe not found.");
        }
    }

    private static void deleteRecipe() {

        System.out.println("== Delete Food ==");
        System.out.print("Enter the name of the recipe to delete: ");
        String name = scanner.nextLine();
        recipes.deleteRecipe(name);
        System.out.println("deletion executed...");
    }

    private static void viewRecipeInfo() {
        System.out.println("== Checking recipe ==");
        System.out.print("Enter the index of the recipe to view: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 0 && index < recipes.getCount()) {
            recipes.recipeInfoIndex(index);
        } else {
            System.out.println("Invalid recipe index.");
        }
    }

    private static void showAllRecipe() {
        System.out.println("== Listing current recipes ==");
        recipes.showAllRecipe();
    }

    private static void findRecipeByName() {
        System.out.println("== Food name finder ==");
        System.out.print("Enter the name of the recipe to find: ");
        String name = scanner.nextLine();
        int index = recipes.findByName(name);
        if (index >= 0) {
            Food food = recipes.recipeInfoIndex(index);
            System.out.println("Food: " + food.getName());
            System.out.println("Category: " + food.getCategory());
            System.out.println("Ingredients: " + Splitter.listToString(food.getIngredient()));
            System.out.println("Instructions: " + Splitter.listToString(food.getInstruction()));
        } else {
            System.out.println("Recipe not found.");
        }
    }

    private static void findRecipeByIngredient() {
        System.out.println("== Ingredient searcher ==");
        System.out.print("Enter the ingredient to search for (comma separated): ");
        String ingredient = scanner.nextLine();
        List<String> ingredientsList = Arrays.asList(ingredient.split(","));
        List<Food> foundRecipes = recipes.findByIngredient(ingredientsList);
        if (!foundRecipes.isEmpty()) {
            for (Food food : foundRecipes) {
                showFood(food);
            }
        } else {
            System.out.println("Recipe with that ingredient not found.");
        }

    }

    private static void sortRecipes() {
        recipes.sortRecipe();
        System.out.println("Recipes sorted by name.");
    }

    private static void suggestRecipe() {
        System.out.println("== Food picker ==");
        if (recipes.getCount() > 0) {
            int suggestionIndex = recipes.suggestRecipe();
            System.out.println("Suggested Recipe:");
            Food food = recipes.recipeInfoIndex(suggestionIndex);
            showFood(food);
        } else {
            System.out.println("No recipes available to suggest.");
        }
    }

    private static void saveRecipes() {
        CsvHandler csvHandler = new CsvHandler(recipes);
        csvHandler.writeToCsv("localfood.csv");
        System.out.println("Recipes saved to file.");

    }

    public static void clearScreen() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    private static void showFood(Food food) {
        System.out.println("Food: " + food.getName());
        System.out.println("Category: " + food.getCategory());
        System.out.println("Ingredients: " + Splitter.listToString(food.getIngredient()));
        System.out.println("Instructions: " + Splitter.listToString(food.getInstruction()));
    }
}