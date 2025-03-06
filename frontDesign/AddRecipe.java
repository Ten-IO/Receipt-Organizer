package frontDesign;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import recipe.ImageHandler;
import recipe.Recipes;

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
    }

    public void createPanel() {
        contentPanel.removeAll();
        contentRecipes = new JPanel(new BorderLayout());
        contentRecipes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Add Your Recipes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);

        // File chooser for surf through files for images, get Value to current imageSource
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton chooseFileButton = new JButton("Choose Photo");
        filePanel.add(chooseFileButton);
        textPanel.add(filePanel, BorderLayout.CENTER);

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
        JScrollPane IngredientPane = new JScrollPane(ingredientField);
        ingredientField.setFont(font);
        IngredientPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        IngredientPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        uploadInfo.add(Box.createVerticalStrut(10));
        uploadInfo.add(IngredientPane);

        HintTextArea instructionField = new HintTextArea("Instructions");
        instructionField.setFont(font);
        JScrollPane InstructionPane = new JScrollPane(instructionField);
        InstructionPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        InstructionPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        uploadInfo.add(Box.createVerticalStrut(10));
        uploadInfo.add(InstructionPane);

        // Add action listener to the choose file button
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, "Selected file: " + selectedFile.getPath());
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadInfo.add(Box.createVerticalStrut(10));
        uploadInfo.add(submitButton);

        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            String ingredient = ingredientField.getText().trim();
            String instruction = instructionField.getText().trim();

            // check for empty fields
            if (name.isEmpty() || category.isEmpty() || ingredient.isEmpty() || instruction.isEmpty()
                    || selectedFile == null) {
                JOptionPane.showMessageDialog(null, "All FIELDS must be filled and a PHOTO must be selected.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Rename and save the file
            String newFileName = name + img.getFileExt(selectedFile.getName());
            File destFile = new File(imgSrc + newFileName);
            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "File saved as: " + destFile.getPath());
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ioException.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Recipe: " + name +
                    "\nCategory: " + category + "\nIngredients:\n" + ingredient + "\nInstructions:\n" + instruction);

            recipes.makeRecipe(name, category, ingredient, instruction);
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
