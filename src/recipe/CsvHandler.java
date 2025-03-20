package recipe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvHandler {
    private Recipes recipes;

    public CsvHandler(Recipes recipes) {
        this.recipes = recipes;
    }

    public void writeToCsv(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("\"Name\",\"Category\",\"Ingredients\",\"Instructions\"\n");
            for (Food food : recipes.recipeList) {
                String ingredients = String.join(",", food.getIngredient());
                String instructions = String.join(",", food.getInstruction());
                writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n", food.getName(), food.getCategory(),
                        ingredients, instructions));
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
        ;
    }

    public void readFromCsv(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Skip header
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\",\"");
                if (parts.length == 4) {
                    String name = parts[0].replace("\"", "");
                    String category = parts[1];
                    String ingredientsString = parts[2];
                    String instructionsString = parts[3].replace("\"", "");

                    List<String> ingredients = new ArrayList<>(Arrays.asList(ingredientsString.split(",")));
                    List<String> instructions = new ArrayList<>(Arrays.asList(instructionsString.split(",")));

                    Food food = new Food(name, category,ingredients, instructions);
                    recipes.makeRecipe(food);

                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from CSV: ");
        }
    }
}
