package recipe;

//
public class Food {
    private String name;
    private String ingredient;
    private String category;

    // Constructor
    public Food(String name, String ingredient, String category) {
        this.name = name;
        this.ingredient = ingredient;
        this.category = category;
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

}
