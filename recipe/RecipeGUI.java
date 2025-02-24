package recipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class RecipeGUI {
    private Recipes recipes;
    private JPanel contentPanel;
    private JFrame frame;
    String imgSrc = "src/images/";
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecipeGUI().showGUI());
    }

    public void showGUI() {
        
        frame = new JFrame("Food IO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());
        try {
            frame.setIconImage(ImageIO.read(new File(imgSrc+"hot-pot.png")));
        } catch (IOException e) {
            System.out.println("Cannot load icon");
        }

        recipes = new Recipes();
        recipes.readFromTxt(); 

        // Navigation Panel
        JPanel navPanel = createNavPanel();
        frame.add(navPanel, BorderLayout.NORTH);

        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1, 20, 20));
        showMainContent(); // Show main content initially
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    public JPanel createNavPanel() {

        // get logo
        ImageIcon imageIcon = new ImageIcon(imgSrc+"resizeLogo.png");
        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel imageLabel = new JLabel(new ImageIcon(image));

        // create flow panel left-right
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(208, 74, 101));
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        navPanel.add(imageLabel);
        
        String[] navItems = {"Main", "List", "New Recipe", "Update Recipe", "Delete Recipe", "Tutorial", "About Us"};
        for (String item : navItems) {
            JButton button = new JButton(item);
            // default select on main
            button.setBackground(item.equals("Main") ? new Color(193, 75, 89) : new Color(208, 74, 101));
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setPreferredSize(new Dimension(150, 60));
            button.setFocusPainted(false);
            button.addActionListener(new NavButtonListener(item)); // Add action listener
            navPanel.add(button);
        }
        
        return navPanel;
    }
    

    public void showMainContent() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(2, 1, 10, 10));
        JPanel recentPanel = createRecipePanel("Recent Recipe");
        JPanel popularPanel = createRecipePanel("Popular Recipe"); // Consider making popular recipes dynamic
        contentPanel.add(recentPanel);
        contentPanel.add(popularPanel);
        // calculate layout
        contentPanel.revalidate();
        // paint again
        contentPanel.repaint();
    }

    public void showRecipeList() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        
        // Create the search field
        SearchField field = new SearchField();
        field.setBorder(new EmptyBorder(0, 0, 0, 0));
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(field, BorderLayout.CENTER);
        
        // Position the searchPanel at the bottom right corner
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(searchPanel, BorderLayout.CENTER);
        contentPanel.add(containerPanel, BorderLayout.CENTER);
        
        
        // list panel for food
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(1100, 700));
    
        // Add recipe items to the list panel
        for (Food recipe : recipes.recipeList) {
            JPanel recipeItemPanel = createListItemPanel(recipe);
            listPanel.add(recipeItemPanel);
        }
    
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    
     public JPanel createListItemPanel(Food recipe) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Separator

        ImageIcon imageIcon = new ImageIcon("src/images/" + recipe.getName().replace(" ", "_") + ".jpg");
        Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        itemPanel.add(imageLabel);

        JLabel nameLabel = new JLabel(recipe.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        itemPanel.add(nameLabel);

        return itemPanel;
    }


    public JPanel createRecipePanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Increased font size
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space after title

        JPanel recipeGrid = new JPanel(new GridLayout(1, 4, 15, 15)); // Increased spacing
        recipeGrid.setAlignmentX(Component.LEFT_ALIGNMENT); // Align grid to the left


        int count = 0;
        for (Food recipe : recipes.recipeList) {
            if (count >= 4) break; // Display only 4 recipes
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(Color.WHITE); // White background for recipe items
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Light border

            ImageIcon imageIcon = new ImageIcon("src/images/" + recipe.getName().replace(" ", "_") + ".jpg");
            Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Increased image size
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            itemPanel.add(imageLabel, BorderLayout.CENTER);

            JLabel nameLabel = new JLabel(recipe.getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Bold recipe names
            itemPanel.add(nameLabel, BorderLayout.SOUTH);

            recipeGrid.add(itemPanel);
            count++;
        }

        panel.add(recipeGrid);
        return panel;
    }

    public class NavButtonListener implements ActionListener {
        private String itemName;

        public NavButtonListener(String itemName) {
            this.itemName = itemName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset background color for all buttons
            Component[] components = ((JPanel)frame.getContentPane().getComponent(0)).getComponents(); // Navigation panel is the first component
            for (Component component : components) {
                if (component instanceof JButton) {
                    component.setBackground(new Color(208, 74, 101));
                }
            }
            // Highlight the selected button
            ((JButton)e.getSource()).setBackground(new Color(193, 75, 89));


            if (itemName.equals("Main")) {
                showMainContent();
            } else if (itemName.equals("List")) {
                showRecipeList();
            } else if (itemName.equals("New Recipe")) {
                showNewRecipePanel();
            } else if (itemName.equals("Update Recipe")) {
                showUpdateRecipePanel();
            } else if (itemName.equals("Delete Recipe")) {
                showDeleteRecipePanel();
            } else if (itemName.equals("Tutorial")) {
                showTutorialPanel();
            } else if (itemName.equals("About Us")) {
                showAboutUsPanel();
            }
        }
    }

    public void showNewRecipePanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        JPanel newRecipePanel = new JPanel();
        newRecipePanel.add(new JLabel("New Recipe Panel Content Here"));
        contentPanel.add(newRecipePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showUpdateRecipePanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        JPanel updateRecipePanel = new JPanel();
        updateRecipePanel.add(new JLabel("Update Recipe Panel Content Here"));
        contentPanel.add(updateRecipePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showDeleteRecipePanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        JPanel deleteRecipePanel = new JPanel();
        deleteRecipePanel.add(new JLabel("Delete Recipe Panel Content Here"));
        contentPanel.add(deleteRecipePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showTutorialPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        JPanel tutorialPanel = new JPanel();
        tutorialPanel.add(new JLabel("Tutorial Panel Content Here"));
        contentPanel.add(tutorialPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showAboutUsPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        JPanel aboutUsPanel = new JPanel();
        aboutUsPanel.add(new JLabel("About Us Panel Content Here"));
        contentPanel.add(aboutUsPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}