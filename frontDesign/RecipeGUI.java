package frontDesign;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import recipe.CsvHandler;
import recipe.Recipes;
import recipe.TxtHandler;

public class RecipeGui {
    private CsvHandler csvHandler;
    private TxtHandler txtHandler;
    private JPanel contentPanel;
    private JFrame frame;
    private Recipes recipes;
    private NavigationPanel navigation;
    private String imgSrc = "src/images/";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecipeGui().showUI());
    }

    public void showUI() {
        recipes = new Recipes();
        txtHandler = new TxtHandler(recipes);
        csvHandler = new CsvHandler(recipes);

        txtHandler.readFromTxt("filterlocalFood.txt");

        // App setting
        frame = new JFrame("Food IO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());
        try {
            frame.setIconImage(ImageIO.read(new File(imgSrc + "hot-pot.png")));
        } catch (IOException e) {
            System.out.println("Cannot load icon");
        }

        // Content Panel
        contentPanel = new JPanel();
        frame.add(contentPanel, BorderLayout.CENTER);

        // Navigation Panel
        navigation = new NavigationPanel(frame, contentPanel, recipes);
        JPanel navPanel = navigation.createPanel();
        frame.add(navPanel, BorderLayout.NORTH);

        // Show Main Content by default
        MainContent mainScene = new MainContent(contentPanel, recipes);
        mainScene.createPanel();

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                txtHandler.writeToTxt(imgSrc + "localFood.txt");
                System.out.println("Saving text");
                csvHandler.writeToCsv(imgSrc + "localFood.csv");
                System.out.println("Saving saving csv");
            }
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public Recipes getRecipes() {
        return recipes;
    }

    public String getImgSrc() {
        return imgSrc;
    }

}