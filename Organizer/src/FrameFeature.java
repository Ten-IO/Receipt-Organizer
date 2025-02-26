package Food;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrameFeature extends JFrame implements ActionListener {
    private JButton menuButton1 = new JButton("Main");
    private JButton menuButton2 = new JButton("Food");
    private JButton menuButton3 = new JButton("Add Food");
    private JButton menuButton4 = new JButton("About Us");
    private JButton menuButton5 = new JButton("Delete Recipes");
    private JButton menuButton6 = new JButton("Update Recipes");

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public FrameFeature() {
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);

        // Default view with random food display
        JPanel mainMenuPanel = createDefaultView();
        mainPanel.add(mainMenuPanel, "Main");

        JPanel TopHomeLine = new JPanel();
        TopHomeLine.setBackground(new Color(255, 127, 127));
        TopHomeLine.setLayout(new FlowLayout(FlowLayout.LEFT));
        TopHomeLine.add(menuButton1);
        TopHomeLine.add(menuButton2);
        TopHomeLine.add(menuButton3);
        TopHomeLine.add(menuButton4);
        TopHomeLine.add(menuButton5);
        TopHomeLine.add(menuButton6);

        menuButton1.addActionListener(this);
        menuButton2.addActionListener(this);
        menuButton3.addActionListener(this);
        menuButton4.addActionListener(this);
        menuButton5.addActionListener(this);
        menuButton6.addActionListener(this);

        this.setLayout(new BorderLayout());
        this.add(TopHomeLine, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    private JPanel createDefaultView() {
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Welcome to Food Organizer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(400, 20, 400, 30);
        mainMenuPanel.add(titleLabel);

        // Fetch random food from the database
        List<String[]> randomRecipes = getRandomRecipes(6);

        int x = 100, y = 80;
        for (String[] recipe : randomRecipes) {
            String name = recipe[0];
            String imagePath = recipe[1];

            JPanel recipePanel = new JPanel();
            recipePanel.setLayout(null);
            recipePanel.setBounds(x, y, 250, 250);

            JLabel foodImage = new JLabel();
            foodImage.setBounds(70, 10, 200, 200);
            foodImage.setIcon(getScaledImage(imagePath, 200, 200));

            JLabel foodName = new JLabel(name, SwingConstants.CENTER);
            foodName.setBounds(70, 220, 200, 20);

            recipePanel.add(foodImage);
            recipePanel.add(foodName);

            mainMenuPanel.add(recipePanel);

            x += 300; 
            if (x > 800) {  
                x = 100;
                y += 280;
            }
        }

        return mainMenuPanel;
    }

    private List<String[]> getRandomRecipes(int limit) {
        List<String[]> recipes = new ArrayList<>();
        String sql = "SELECT name, picture FROM Recipes ORDER BY RAND() LIMIT ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String imagePath = rs.getString("picture");
                recipes.add(new String[]{name, imagePath});
            }
        } catch (SQLException e) {
            System.err.println("Error fetching random recipes: " + e.getMessage());
        }

        return recipes;
    }

    private ImageIcon getScaledImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton1) {
            cardLayout.show(mainPanel, "Main");
        } else if (e.getSource() == menuButton2) {
            cardLayout.show(mainPanel, "List");
        } else if (e.getSource() == menuButton3) {
            cardLayout.show(mainPanel, "editRecipes");
        } else if (e.getSource() == menuButton4) {
            cardLayout.show(mainPanel, "AboutUs");
        } else if (e.getSource() == menuButton5) {
            cardLayout.show(mainPanel, "RemovingRecipes");
        }else if (e.getSource() == menuButton6) {
            cardLayout.show(mainPanel, "UpdateRecipes");
        }
    }


}
