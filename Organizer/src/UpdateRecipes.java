package Food;


import javax.swing.*;
import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;
import java.awt.*;
import java.awt.event.ActionListener;

public class UpdateRecipes  {
    private JPanel contentRecipes;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public UpdateRecipes(FrameFeature frame) {
        this.cardLayout = frame.getCardLayout(); // Get CardLayout from main frame
        this.mainPanel = frame.getMainPanel(); // Get main panel
        contentRecipe();
        mainPanel.add(contentRecipes, "UpdateRecipes"); // Add the About Us panel to CardLayout
    }

    private void contentRecipe() {
        contentRecipes = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Edit Your Recipes\n ************************", SwingConstants.CENTER);
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
        
        JTextField inputText = new JTextField();
        inputText.setText("Please input new Food's Category");
       inputText.setBounds(400,150,500,50);
        uploadInfo.add(inputText);
        
        
        JTextField inputText2 = new JTextField();
        inputText2.setText("Please input new Food's Photo");
       inputText2.setBounds(400,250,500,50);
        uploadInfo.add(inputText2);
        
        JTextArea inputText3 = new JTextArea();
        JScrollPane pane = new JScrollPane(inputText3);
        inputText3.setText("Please input new Food's Ingredient");
        pane.setBounds(400,350,500,200);
        uploadInfo.add(pane);
        
        
        
        JButton submitButton = new JButton();
        submitButton.setText("Submit");
        submitButton.setBounds(400,600, 100, 50);
        uploadInfo.add(submitButton);
        submitButton.addActionListener(e -> {
            String name = inputText0.getText();
            String category = inputText.getText();
            String photo = inputText2.getText();
            String ingredient = inputText3.getText();

            JOptionPane.showMessageDialog(null, "Recipe:\nName: " + name + 
                "\nCategory: " + category + "\nPhoto: " + photo + "\nIngredients: " + ingredient);
            RecipeDB.updateRecipe(name, category, ingredient, ingredient);
            
            // Here, you can also call a database function to insert the recipe into MySQL.
        });
        
        contentRecipes.add(textPanel, BorderLayout.NORTH);
        contentRecipes.add(uploadInfo);
        
    }
   
}
