package frontDesign;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import recipe.ImageHandler;
import recipe.Recipes;
import recipe.Splitter;

public class AddRecipe {
    private JPanel contentRecipes;
    private JPanel contentPanel;
    private ImageHandler img;
    private Recipes recipes;
    private String imgSrc = "src/images/";
    private Font font = new Font("Roboto", Font.PLAIN, 14);
    private File selectedFile;

    public AddRecipe(JPanel contentPanel, Recipes recipes) {
        this.recipes = recipes;
        this.contentPanel = contentPanel;
        // Instantiate the ImageHandler so it is ready when copying file
        this.img = new ImageHandler();  
    }

    public void createPanel() {
        contentPanel.removeAll();
        contentRecipes = new JPanel(new BorderLayout());
        contentRecipes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title section
        JLabel titleLabel = new JLabel("Add Your Recipes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);

        // File chooser panel
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton chooseFileButton = new JButton("Choose Photo");
        filePanel.add(chooseFileButton);
        textPanel.add(filePanel, BorderLayout.CENTER);

        // Upload input fields panel
        JPanel uploadInfo = new JPanel();
        uploadInfo.setLayout(new BoxLayout(uploadInfo, BoxLayout.Y_AXIS));

        HintTextArea nameField = new HintTextArea("Food's name");
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setFont(font);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        uploadInfo.add(Box.createVerticalStrut(10));
        uploadInfo.add(nameField);

        HintTextArea categoryField = new HintTextArea("Food's Category");
        categoryField.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryField.setFont(font);
        categoryField.setMaximumSize(new Dimension(Integer.MAX_VALUE, categoryField.getPreferredSize().height));
        uploadInfo.add(Box.createVerticalStrut(10));
        uploadInfo.add(categoryField);

        HintTextArea ingredientField = new HintTextArea("Ingredients");
        JScrollPane ingredientPane = new JScrollPane(ingredientField);
        ingredientField.setFont(font);
        ingredientPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        ingredientPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        uploadInfo.add(Box.createVerticalStrut(10));
        uploadInfo.add(ingredientPane);

        HintTextArea instructionField = new HintTextArea("Instructions");
        JScrollPane instructionPane = new JScrollPane(instructionField);
        instructionField.setFont(font);
        instructionPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        uploadInfo.add(Box.createVerticalStrut(10));
        uploadInfo.add(instructionPane);

        // File chooser action
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, "Selected file: " + selectedFile.getPath());
            }
        });

        // Submit recipe button
        JButton submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadInfo.add(Box.createVerticalStrut(10));
        uploadInfo.add(submitButton);

        // Submit action: filtering input via Splitter and handling exceptions
        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String category = categoryField.getText().trim();
                String ingredient = ingredientField.getText().trim();
                String instruction = instructionField.getText().trim();

                // Check for empty fields
                if (name.isEmpty() || category.isEmpty() || ingredient.isEmpty() 
                        || instruction.isEmpty() || selectedFile == null) {
                    JOptionPane.showMessageDialog(null, 
                            "All FIELDS must be filled and a PHOTO must be selected.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Filter input: if ingredients or instructions are in list format using newlines and "- "
                if (ingredient.contains("\n") && ingredient.contains("- ")) {
                    ingredient = Splitter.stringToCommaString(ingredient);
                }
                if (instruction.contains("\n") && instruction.contains("- ")) {
                    instruction = Splitter.stringToCommaString(instruction);
                }

                // Rename and save the file
                // Retrieve the file extension using ImageHandler
String extension = img.getFileExt(selectedFile.getName());
// Construct the new file name, adding a dot if the extension is not empty
String newFileName = extension.isEmpty() ? name : name + "." + extension;

File destFile = new File(imgSrc + newFileName);


    Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    JOptionPane.showMessageDialog(null, "File saved as: " + destFile.getPath());


                // Confirmation dialog with the final recipe details
                JOptionPane.showMessageDialog(null, "Recipe: " + name +
                        "\nCategory: " + category + "\nIngredients:\n" + ingredient +
                        "\nInstructions:\n" + instruction);

                // Create recipe using filtered input
                recipes.makeRecipe(name, category, ingredient, instruction);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ioException.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
            }
        });

        contentRecipes.add(textPanel, BorderLayout.NORTH);
        contentRecipes.add(uploadInfo);
        contentPanel.add(contentRecipes);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JPanel getContentPanel() {
        return contentRecipes;
    }
}
