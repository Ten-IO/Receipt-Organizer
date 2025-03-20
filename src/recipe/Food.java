package recipe;

import java.util.List;

public class Food {
    private String name;
    private List<String> ingredient;
    private String category;
    private List<String> instruction;

    // Constructor
    public Food(String name,  String category, List<String> ingredient, List<String> instruction) {
        this.name = name;
        this.ingredient = ingredient;
        this.category = category;
        this.instruction = instruction;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    void setIngredient(List<String> ingredient) {
        this.ingredient = ingredient;
    }

    void setCategory(String category) {
        this.category = category;
    }

    void setInstruction(List<String> instruction) {
        this.instruction = instruction;
    }

    // Getter
    public String getName() {
        return name;
    }

    public List<String> getIngredient() {
        return ingredient;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getInstruction() {
        return instruction;
    }

    public List<String> splitIngredient(String ingredientString) {
        if (!ingredientString.contains(",") && !ingredientString.contains(" ")) {
            ingredient.add(ingredientString.trim());
        } else {
            String[] ingredientArray = ingredientString.split(", ");
            for (String i : ingredientArray) {
                if (!i.trim().isEmpty()) {
                    ingredient.add(i.trim());
                }
            }
        }
        return ingredient;
    }

    public List<String> splitInstruction(String instructiontString) {
        if (!instructiontString.contains(",") && !instructiontString.contains(" ")) {
            ingredient.add(instructiontString.trim());
        } else {
            String[] instructionArray = instructiontString.split(", ");
            for (String i : instructionArray) {
                if (!i.trim().isEmpty()) {
                    instruction.add(i.trim());
                }
            }
        }
        return instruction;
    }

}
