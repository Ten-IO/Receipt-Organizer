package recipe;

public class Food {
    private String name;
    private String ingredient;
    private String category;
    private String instruction;

    // Constructor
    public Food(String name, String ingredient, String category, String instruction) {
        this.name = name;
        this.ingredient = ingredient;
        this.category = category;
        this.instruction = instruction;
    }

    Food(String name, String ingredient, String category) {
        this(name, ingredient, category, "none");
    }

    // Setter
    void setName(String name) {
        this.name = name;
    }

    void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    void setCategory(String category) {
        this.category = category;
    }

    void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    // Getter
    String getName() {
        return name;
    }

    String getIngredient() {
        return ingredient;
    }

    String getCategory() {
        return category;
    }

    String getInstruction() {
        return instruction;
    }

}
