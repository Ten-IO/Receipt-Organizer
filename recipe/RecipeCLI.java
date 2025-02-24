package recipe;

import java.util.Scanner;

public class RecipeCLI {

    private static Recipes recipes = new Recipes();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        recipes.readFromTxt(); // Load recipes from file on startup

        while (true) {
            System.out.println("\n=== Recipe Management CLI ===");
            System.out.println("1. Add Recipe");
            System.out.println("2. Update Recipe");
            System.out.println("3. View Recipe Info");
            System.out.println("4. Find Recipe by Name");
            System.out.println("5. Find Recipe by Ingredient");
            System.out.println("6. Sort Recipes by Name");
            System.out.println("7. Suggest a Recipe");
            System.out.println("8. Show current recipes");
            System.out.println("9. Exit");
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
                clearScreen();
                    viewRecipeInfo();
                    break;
                case 4:
                clearScreen();
                    findRecipeByName();
                    break;
                case 5:
                clearScreen();
                    findRecipeByIngredient();
                    break;
                case 6:
                    sortRecipes();
                    clearScreen();
                    break;
                case 7:
                clearScreen();
                    suggestRecipe();
                    break;
                case 8:
                clearScreen();
                    showAllRecipe();
                    break;
                case 9:
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
        if(confirm.equalsIgnoreCase("y")) {
        recipes.makeReceipt(name, ingredients, category, instructions);
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
        if(confirm.equalsIgnoreCase("y")) {
        recipes.updateReceipt(name, ingredients, category, instructions);
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

    private static void viewRecipeInfo() {
        System.out.println("== Checking recipe ==");
        System.out.print("Enter the index of the recipe to view: ");
        int index = scanner.nextInt();
        scanner.nextLine(); 
        if (index >= 0 && index < recipes.getCount()) {
            recipes.recipeInfo(index);
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
            recipes.recipeInfo(index);
        } else {
            System.out.println("Recipe not found.");
        }
    }

    private static void findRecipeByIngredient() {
        System.out.println("== Ingredient searcher ==");
        System.out.print("Enter the ingredient to search for: ");
        String ingredient = scanner.nextLine();
        int index = recipes.findByIngredient(ingredient);
        if (index >= 0) {
            recipes.recipeInfo(index);
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
            recipes.recipeInfo(suggestionIndex);
        } else {
            System.out.println("No recipes available to suggest.");
        }
    }

    private static void saveRecipes() {
        recipes.writeToTxt();
        System.out.println("Recipes saved to file.");
        
    }

    public static void clearScreen() {
        try{
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
}