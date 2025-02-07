package recipe;

public class UpdateReceipt extends MakeReceipt{

    UpdateReceipt(String name, String ingredient, String category) {
        super(name, ingredient, category);
        findByName(name);
    }
}
