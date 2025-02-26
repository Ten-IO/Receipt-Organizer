package Food;


import javax.swing.*;
import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;
import java.awt.*;
import java.awt.event.ActionListener;

public class RemovingRecipes  {
    private JPanel contentRecipes;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public RemovingRecipes(FrameFeature frame) {
        this.cardLayout = frame.getCardLayout(); // Get CardLayout from main frame
        this.mainPanel = frame.getMainPanel(); // Get main panel
        contentRecipe();
        mainPanel.add(contentRecipes, "RemovingRecipes"); // Add the About Us panel to CardLayout
    }

    private void contentRecipe() {
        contentRecipes = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Delete Your Recipes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);
        JLabel text = new JLabel();
        text.setText("Please input Food Information");
        text.setFont(new Font("Arial", Font.BOLD, 25));
        textPanel.add(text,BorderLayout.CENTER);
        JPanel uploadInfo = new JPanel();
        uploadInfo.setLayout(null);
        
        JTextField inputText0 = new JTextField();
        inputText0.setText("Please input Food's Name");
       inputText0.setBounds(400,50,500,50);
        uploadInfo.add(inputText0);
        
        
        JButton submitButton = new JButton();
        submitButton.setText("Submit");
        submitButton.setBounds(400,100, 100, 50);
        uploadInfo.add(submitButton);
        submitButton.addActionListener(e -> {
            String name = inputText0.getText();
            JOptionPane.showMessageDialog(null, "Recipe:\nName: " + name);
            RecipeDB.deleteRecipe(name);
            // Here, you can also call a database function to insert the recipe into MySQL.
        });
        
        contentRecipes.add(textPanel, BorderLayout.NORTH);
        contentRecipes.add(uploadInfo);
        
    }
   
}
