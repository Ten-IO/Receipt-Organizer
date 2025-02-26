package Food;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        FrameFeature frame = new FrameFeature(); // Main window
        new ListFood(frame); 
        new AboutUs(frame);
        new testing(frame);
        new editRecipes(frame);
        new RemovingRecipes(frame);
        new UpdateRecipes(frame);
    }
}
