package frontDesign;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import recipe.Recipes;

public class NavigationListener implements ActionListener {
    private String itemName;
    private JFrame mainFrame;
    private JPanel contentPanel;
    private Recipes recipes;

    public NavigationListener(JFrame mainFrame, JPanel contentPanel, Recipes recipes, String itemName) {
        this.mainFrame = mainFrame;
        this.contentPanel = contentPanel;
        this.recipes = recipes;
        this.itemName = itemName;
    }

    // action from navigation panel to switch to each sections
    @Override
    public void actionPerformed(ActionEvent e) {
        Component[] components = ((JPanel) mainFrame.getContentPane().getComponent(0)).getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                component.setBackground(new Color(208, 74, 101));
            }
        }
        // Highlight the selected button when click
        ((JButton) e.getSource()).setBackground(new Color(193, 75, 89));

        if (itemName.equals("Main")) {
            MainContent mainScene = new MainContent(contentPanel, recipes);
            mainScene.createPanel();
        } else if (itemName.equals("List")) {
            FoodList listScene = new FoodList(contentPanel, recipes);
            listScene.createPanel();
        } else if (itemName.equals("New Recipe")) {
            AddRecipe RecipePanel = new AddRecipe(contentPanel, recipes);
            RecipePanel.createPanel();
        } else if (itemName.equals("Tutorial")) {
            Tutorial tutorialPanel = new Tutorial(contentPanel);
            tutorialPanel.createPanel();
        } else if (itemName.equals("About Us")) {
            AboutUs infoPanel = new AboutUs(contentPanel);
            infoPanel.createPanel();
        }
    }
}
