package Food;

import javax.swing.*;
import java.sql.Connection;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListFood {
    private JPanel recipeListPanel, recipeDetailPanel;
    private JLabel detailLabel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public ListFood(FrameFeature frame) throws SQLException {
        this.cardLayout = frame.getCardLayout();
        this.mainPanel = frame.getMainPanel();

        createRecipeListPanel();
        createRecipeDetailPanel();

        mainPanel.add(new JScrollPane(recipeListPanel), "List"); // Add scroll
        mainPanel.add(recipeDetailPanel, "Details");
    }

    private void createRecipeListPanel() throws SQLException {
        recipeListPanel = new JPanel(new GridLayout(0, 4, 10, 10)); 
        recipeListPanel.setBackground(Color.WHITE);
        recipeListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Load recipes from file
        List<String[]> recipes = new ArrayList<>();
        List<String> picture = RecipeDB.getPic();
    	List<String> names = RecipeDB.getName();
    	List<String> ingredients = RecipeDB.getIngredient();
    	List<Integer> idIntegers = RecipeDB.getID();
    	

    	// Ensure all lists have the same size
    	for (int i = 0; i < idIntegers.size(); i++) {
    	    String[] recipe = new String[3]; 
    	    recipe[0] = names.get(i); // Name
    	    recipe[1] = picture.get(i); //get pic
    	    recipe[2] = ingredients.get(i); // Ingredients
    	    recipes.add(recipe);
    	}

        for (String[] recipe : recipes) {
            recipeListPanel.add(createRecipePanel(recipe[0], recipe[1], recipe[2]));
            System.out.println("Name: " + recipe[0]);
    	    System.out.println("Picture: " + recipe[1]);
    	    System.out.println("Ingredients: " + recipe[2]);
        }
        
        // Wrap in JScrollPane
        JScrollPane scrollPane = new JScrollPane(recipeListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(100);  

        mainPanel.add(scrollPane, "List");
    }

    private JPanel createRecipePanel(String title, String imagePath, String ingredients) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 250)); // Fixed size for grid layout
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel imageLabel = new JLabel();
        panel.setLayout(null);
        imageLabel.setIcon(getScaledImage(imagePath, 180, 180));
        imageLabel.setBounds(50,40, 180 , 180);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBounds(85, 185,100, 100);
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateDetailPanel(title, ingredients);
                cardLayout.show(mainPanel, "Details");
            }
        });

        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(titleLabel, BorderLayout.SOUTH);
        return panel;
    }

    private void createRecipeDetailPanel() {
        recipeDetailPanel = new JPanel(new BorderLayout());

        detailLabel = new JLabel("", SwingConstants.CENTER);
        detailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        recipeDetailPanel.add(detailLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e ->  cardLayout.show(mainPanel, "List"));
        recipeDetailPanel.add(backButton, BorderLayout.SOUTH);
    }

    private void updateDetailPanel(String title, String ingredients) {
        detailLabel.setText("<html><h2>" + title + "</h2><br><b>Ingredients:</b><br>" + ingredients.replace(",", "<br>") + "</html>");
    }


    public ImageIcon getScaledImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
