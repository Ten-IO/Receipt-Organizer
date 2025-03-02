import recipe.*;

public class App {
    public static void main(String[] args) throws Exception {
        Recipes recipe = new Recipes();
        recipe.makeReceipt("Cheese cake", "flour, cheese, sugar, salt", "Western",
                "Heat oven, put flour and other ingredient, cook");
        recipe.makeReceipt("Chocolate cake", "flour, cheese, sugar, salt", "Western",
                "Heat oven, put flour and other ingredient, cook");
        recipe.makeReceipt("Chocolate cake", "flour, cheese, sugar, salt", "Western",
                "Heat oven, put flour and other ingredient, cook");
        recipe.makeReceipt("Roman cake", "flour, cheese, sugar, salt", "Western",
                "Heat oven, put flour and other ingredient, cook");
        recipe.makeReceipt("Aspirant cake", "flour, cheese, sugar, salt", "Western",
                "Heat oven, put flour and other ingredient, cook");

        System.out.println("recipe added");
        System.out.println("Current Recipes number: " + recipe.getCount());
        recipe.writeToTxt();
        
    }
}
