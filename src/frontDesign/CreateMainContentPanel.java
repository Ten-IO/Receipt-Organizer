package frontDesign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class CreateMainContentPanel {
    private Recipes recipes;
    private int amount = 5;
    private String imgSrc = "src/images/";

    public CreateMainContentPanel(Recipes recipes) {
        this.recipes = recipes;
    }

    public JPanel createRecipePanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        CurvedPanel recipeGrid = new CurvedPanel(10, 10);
        recipeGrid.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
        recipeGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        recipeGrid.setBackground(Color.gray);

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

            CurvedPanel itemPanel = new CurvedPanel(15, 15);
            itemPanel.setLayout(new BorderLayout());
            itemPanel.setBackground(Color.WHITE);

            ImageIcon imageIcon = ImageUtils.getScaledImageIcon(imgSrc, recipe.getName().replaceAll("[,_ ]", ""), 200,
                    200);
            RoundedImagePanel roundedImagePanel = new RoundedImagePanel(imageIcon);

            itemPanel.add(roundedImagePanel, BorderLayout.CENTER);

            JLabel nameLabel = new JLabel(recipe.getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Roboto", Font.BOLD, 14));
            nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            itemPanel.add(nameLabel, BorderLayout.SOUTH);
            recipeGrid.add(itemPanel);
            count++;

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

        String foodName = recipe.getName();
        String category = recipe.getCategory();
        List<String> ingredients = recipe.getIngredient();
        List<String> instructions = recipe.getInstruction();

        JLabel foodLabel = new JLabel("<html><h1>" + foodName + "</h1></html>");
        foodLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(foodLabel);

        ImageIcon imageIcon = ImageUtils.getScaledImageIcon(imgSrc, recipe.getName().replaceAll("[,_ ]", ""), 200, 200);
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
            checkBox.setFocusable(false);
            infoPanel.add(checkBox);
        }

        JLabel instructionsLabel = new JLabel("<html><h2>Instructions:</h2></html>");
        instructionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(instructionsLabel);

        for (String instruction : instructions) {
            JCheckBox checkBox = new JCheckBox(instruction);
            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            checkBox.setFocusable(false);
            infoPanel.add(checkBox);
        }

        infoFrame.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        infoFrame.setLocationRelativeTo(null);

        infoFrame.setVisible(true);
    }

}
