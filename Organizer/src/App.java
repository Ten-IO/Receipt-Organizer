package Food;

public class App {
    public static void main(String[] args) {
        FrameFeature frame = new FrameFeature(); // Main window
        new ListFood(frame); 
        new AboutUs(frame);
        new testing(frame);
    }
}
