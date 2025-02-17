package Food;


import javax.swing.*;
import java.awt.*;

public class editRecipes {
    private JPanel contentRecipes;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public editRecipes(FrameFeature frame) {
        this.cardLayout = frame.getCardLayout(); // Get CardLayout from main frame
        this.mainPanel = frame.getMainPanel(); // Get main panel

        contentRecipe();
        mainPanel.add(contentRecipes, "editRecipes"); // Add the About Us panel to CardLayout
    }

    private void contentRecipe() {
        contentRecipes = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Edit your recipes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);
        JLabel text = new JLabel();
        text.setText("Let Ten Cook....");
        text.setFont(new Font("Arial", Font.BOLD, 80));
        textPanel.add(text,BorderLayout.CENTER);
        
   
        
        
 
        contentRecipes.add(textPanel, BorderLayout.CENTER);
    }
}
