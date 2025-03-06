package frontDesign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ImageRender.ImageUtils;
import recipe.Food;
import recipe.Recipes;
import recipe.TxtHandler;

public class CreateMainContentPanel {
    private Recipes recipes;
    private int amount = 20;
    private String imgSrc = "src/images/";

    public CreateMainContentPanel(Recipes recipes) {
        TxtHandler txtHandler = new TxtHandler(recipes);
        txtHandler.readFromTxt("filterlocalFood.txt");
        this.recipes = recipes;
    }

    public JPanel createRecipePanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel recipeGrid = new JPanel(new GridLayout(1, amount, 15, 15));
        recipeGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        int count = 0;
        int recent = recipes.getCount();
        while (count < amount) {
            Food recipe;
            if (!title.equalsIgnoreCase("Today's Recipe")) {
                recipe = recipes.recipeInfoIndex(--recent);
            } else {
                int rand = recipes.suggestRecipe();
                recipe = recipes.recipeInfoIndex(rand);
            }
            if (count >= amount)
                break;

            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            ImageIcon imageIcon = ImageUtils.getScaledImageIcon(imgSrc, recipe.getName().replace(" ", ""), 200,
                    200);
            JLabel imageLabel = new JLabel(imageIcon);
            itemPanel.add(imageLabel, BorderLayout.CENTER);

            JLabel nameLabel = new JLabel(recipe.getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Roboto", Font.BOLD, 14));
            itemPanel.add(nameLabel, BorderLayout.SOUTH);

            recipeGrid.add(itemPanel);
            count++;

            // Mouse listener for info
            itemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    displayRecipeInfo(recipe);
                }
            });
        }

        panel.add(recipeGrid);
        return panel;
    }

    public void setAmountPanel(int amount) {
        this.amount = amount;
    }

    private void displayRecipeInfo(Food recipe) {
        JFrame infoFrame = new JFrame("Recipe Information");
        infoFrame.setSize(1024, 640);
        infoFrame.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String foodName = recipe.getName();
        String category = recipe.getCategory();
        List<String> ingredients = recipe.getIngredient();
        List<String> instructions = recipe.getInstruction();

        JLabel foodLabel = new JLabel("<html><h1>" + foodName + "</h1></html>");
        foodLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(foodLabel);

        ImageIcon imageIcon = ImageUtils.getScaledImageIcon(imgSrc, recipe.getName().replace(" ", ""), 200, 200);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(imageLabel);

        JLabel categoryLabel = new JLabel("<html><h2>Category: " + category + "</h2></html>");
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(categoryLabel);

        JLabel ingredientsLabel = new JLabel("<html><h2>Ingredients:</h2></html>");
        ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(ingredientsLabel);

        for (String ingredient : ingredients) {
            JCheckBox checkBox = new JCheckBox(ingredient);
            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.add(checkBox);
        }

        JLabel instructionsLabel = new JLabel("<html><h2>Instructions:</h2></html>");
        instructionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(instructionsLabel);

        for (String instruction : instructions) {
            JCheckBox checkBox = new JCheckBox(instruction);
            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.add(checkBox);
        }

        infoFrame.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        infoFrame.setVisible(true);
    }

}
