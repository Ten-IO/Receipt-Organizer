package recipe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TxtHandler {
    private Recipes recipes;

    public TxtHandler(Recipes recipes) {
        this.recipes = recipes;
    }

    public void writeToTxt(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Food food : recipes.recipeList) {
                writer.write(
                        food.getName() + "|" + food.getCategory() + "|" + String.join(",", food.getIngredient()) + "|"
                                + String.join(",", food.getInstruction()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Java cannot write files, permission restricted");
            e.printStackTrace();
        }
    }

    public void readFromTxt(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split("\\|");
                if (part.length == 4) {
                    String name = part[0];
                    String ingredient = part[1];
                    String category = part[2];
                    String instruction = part[3];
                    Food newRecipe = new Food(name, category, Splitter.chopInput(ingredient),
                            Splitter.chopInput(instruction));

                    recipes.makeRecipe(newRecipe);
                }
            }

        } catch (IOException e) {
            System.out.println("Java cannot read files");
            e.printStackTrace();
        }
    }

}
