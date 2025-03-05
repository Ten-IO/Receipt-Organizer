package frontDesign;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import recipe.CsvHandler;
import recipe.Food;
import recipe.Recipes;
import recipe.TxtHandler;
import recipe.Splitter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class RecipeGUI {
    private Recipes recipes;
    private CsvHandler csvHandler;
    private TxtHandler txtHandler;
    private JPanel contentPanel;
    private JFrame frame;
    
    String imgSrc = "src/images/";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecipeGUI().showGUI());
    }

    public void showGUI() {

        // app setting
        frame = new JFrame("Food IO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());
        try {
            frame.setIconImage(ImageIO.read(new File(imgSrc + "hot-pot.png")));
        } catch (IOException e) {
            System.out.println("Cannot load icon");
        }

        recipes = new Recipes();
        txtHandler = new TxtHandler(recipes);
        txtHandler.readFromTxt("src/files/localFood.txt");

        // Navigation Panel
        JPanel navPanel = createNavPanel();
        frame.add(navPanel, BorderLayout.NORTH);

        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1, 10, 20));
        showMainContent();

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                txtHandler.writeToTxt("src/files/localFood.txt");
            }});
    }

    public JPanel createNavPanel() {

        // get logo
        ImageIcon imageIcon = new ImageIcon(imgSrc + "Food4.png");
        Image image = imageIcon.getImage();
        JLabel logoLabel = new JLabel(new ImageIcon(image));

        // navigation flow panel left-right
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(208, 74, 101));
        navPanel.setPreferredSize(new Dimension(frame.getWidth(), 150));
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        navPanel.add(logoLabel);

        String[] navItems = { "Main", "List", "New Recipe", "Tutorial", "About Us" };
        for (String item : navItems) {
            JButton button = new JButton(item);
            // default select on main
            button.setBackground(item.equals("Main") ? new Color(193, 75, 89) : new Color(208, 74, 101));
            // rgb(231, 162, 176) #D04A65
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setFont(new Font("Roboto", Font.PLAIN, 18));
            button.setPreferredSize(new Dimension(150, 60));
            button.setFocusPainted(false);
            button.addActionListener(new NavButtonListener(item));
            navPanel.add(button, BorderLayout.CENTER);
        }
        return navPanel;
    }

    public void showMainContent() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        // recent food
        JPanel recentPanel = createRecipePanel("Recent Recipe");
        JScrollPane recentScrollPane = new JScrollPane(recentPanel);
        // mini scroll
        recentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        recentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // popular food
        JPanel popularPanel = createRecipePanel("Popular Recipe");
        // mini scroll
        JScrollPane popularScrollPane = new JScrollPane(popularPanel);
        popularScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        popularScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(recentScrollPane);
        mainPanel.add(popularScrollPane);

        // Lock Horizon
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        // mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(mainScrollPane, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showRecipeList() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(1, 2));

        List<String> recipeNames = new ArrayList<>();
        for (Food food : recipes.recipeList) {
            recipeNames.add(food.getName());
        }
        // search
        AutoSuggestSearchField field = new AutoSuggestSearchField(recipeNames);
        JPanel Panel1 = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setSize(new Dimension(Panel1.getWidth(), 100));
        JPanel underSearchPanel = new JPanel(new BorderLayout());

        // Create foodTableModel
        FoodTableModel foodTable = new FoodTableModel(recipes.recipeList);
        JTable table = new JTable(foodTable);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.setRowHeight(40);
        FoodTableModel obj = (FoodTableModel) table.getModel();
        TableRowSorter<FoodTableModel> objNew = new TableRowSorter<>(obj);
        table.setRowSorter(objNew);
        objNew.setRowFilter(RowFilter.regexFilter(field.getText()));

        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateFilter();
            }

            private void updateFilter() {
                String text = field.getText();
                if (text == null || text.trim().isEmpty()) {
                    objNew.setRowFilter(null);
                } else {
                    // check sensitivity match (?i)
                    objNew.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        TableColumn actionColumn = table.getColumnModel().getColumn(2);
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.getVerticalScrollBar().setUnitIncrement(5);
        scrollTable.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                underSearchPanel.removeAll();
                JPanel editPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

                JLabel foodLabel = new JLabel("Food");
                JTextArea foodField = new JTextArea(recipes.recipeList.get(row).getName());
                JScrollPane foodScrollPane = new JScrollPane(foodField);
                foodField.setLineWrap(true);
                foodField.setWrapStyleWord(true);
                foodField.setPreferredSize(new Dimension(400, 50));
                editPanel.add(foodLabel, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.BOTH; // Allow vertical fill
                gbc.weighty = 0.1; // Give it some vertical weight
                editPanel.add(foodScrollPane, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0; // Reset vertical weight
                JLabel categoryLabel = new JLabel("Category");
                JTextArea categoryField = new JTextArea(recipes.recipeList.get(row).getCategory());
                JScrollPane categoryScrollPane = new JScrollPane(categoryField);
                categoryField.setLineWrap(true);
                categoryField.setWrapStyleWord(true);
                categoryField.setPreferredSize(new Dimension(400, 50));
                editPanel.add(categoryLabel, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 0.1;
                editPanel.add(categoryScrollPane, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0;
                JLabel ingredientLabel = new JLabel("Ingredients");
                JTextArea ingredientField = new JTextArea(
                        Splitter.listToString(recipes.recipeList.get(row).getIngredient()));
                JScrollPane ingredientScrollPane = new JScrollPane(ingredientField);
                ingredientField.setLineWrap(true);
                ingredientField.setWrapStyleWord(true);
                ingredientField.setPreferredSize(new Dimension(400, 100));
                editPanel.add(ingredientLabel, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 0.2;
                editPanel.add(ingredientScrollPane, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0;
                JLabel instructionLabel = new JLabel("Instructions");
                JTextArea instructionField = new JTextArea(
                        Splitter.listToString(recipes.recipeList.get(row).getInstruction()));
                JScrollPane instructionScrollPane = new JScrollPane(instructionField);
                instructionField.setLineWrap(true);
                instructionField.setWrapStyleWord(true);
                instructionField.setPreferredSize(new Dimension(400, 150));
                editPanel.add(instructionLabel, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 0.3;
                editPanel.add(instructionScrollPane, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0;
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

                JButton save = new JButton("Save");
                save.setFocusable(false);
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        recipes.updateRecipe(row, foodField.getText(), categoryField.getText(),
                                Splitter.stringToCommaString(ingredientField.getText()),
                                Splitter.stringToCommaString(instructionField.getText()));
                        showRecipeList();
                    }
                });
                buttonPanel.add(save);

                JButton cancel = new JButton("Cancel");
                cancel.setFocusable(false);
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showRecipeList();
                    }
                });
                buttonPanel.add(cancel);

                editPanel.add(buttonPanel, gbc);

                underSearchPanel.add(editPanel);
                underSearchPanel.revalidate();
                underSearchPanel.repaint();
            }

            @Override
            public void onView(int row) {
                underSearchPanel.removeAll();
                JPanel viewPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(0, 5, 10, 10);

                String food = recipes.recipeList.get(row).getName();
                String category = recipes.recipeList.get(row).getCategory();
                List<String> ingredients = recipes.recipeList.get(row).getIngredient();
                List<String> instructions = recipes.recipeList.get(row).getInstruction();

                // Food Label
                JLabel foodLabel = new JLabel("Food");

                // Food Area
                JTextArea foodArea = new JTextArea(food);
                foodArea.setEditable(false);
                viewPanel.add(foodLabel, gbc);
                gbc.gridy++;
                viewPanel.add(foodArea, gbc);
                gbc.gridy++;

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridheight = 1; // Span two rows for the photo
                gbc.fill = GridBagConstraints.BOTH; // Fill both directions
                gbc.weightx = 0.5; // Give some horizontal weight
                gbc.weighty = 0.5; // Give some vertical weight

                ImageIcon imageIcon = new ImageIcon("src/images/" + food.replace(" ", "") + ".jpg");
                Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size
                JLabel photoLabel = new JLabel(new ImageIcon(image));
                viewPanel.add(photoLabel, gbc);
                gbc.gridx++;

                // Reset GridBagConstraints for other components
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 0;
                gbc.weighty = 0;

                // Category
                JLabel categoryLabel = new JLabel("<html><div style='text-align: center;'>"
                        + "<div style='height: 100px;'></div>"
                        + "Category:</div></html>");
                JTextArea categoryArea = new JTextArea(category);
                categoryArea.setEditable(false);
                viewPanel.add(categoryLabel, gbc);
                gbc.gridy++;
                viewPanel.add(categoryArea, gbc);

                // Ingredients
                gbc.gridy++;
                JLabel ingredientsLabel = new JLabel("Ingredients:");
                JTextArea ingredientsArea = new JTextArea(Splitter.listToString(ingredients));
                ingredientsArea.setEditable(false);
                ingredientsArea.setLineWrap(true);
                ingredientsArea.setWrapStyleWord(true);
                JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsArea);
                ingredientsScrollPane.setPreferredSize(new Dimension(400, 100));
                viewPanel.add(ingredientsLabel, gbc);
                gbc.gridy++;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 0.5;
                viewPanel.add(ingredientsScrollPane, gbc);

                // Instructions
                gbc.gridy++;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0;
                JLabel instructionsLabel = new JLabel("Instructions:");
                JTextArea instructionsArea = new JTextArea(Splitter.listToString(instructions));
                instructionsArea.setEditable(false);
                instructionsArea.setLineWrap(true);
                JScrollPane instructionsScrollPane = new JScrollPane(instructionsArea);
                instructionsScrollPane.setPreferredSize(new Dimension(400, 150));
                viewPanel.add(instructionsLabel, gbc);
                gbc.gridy++;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 0.5;
                viewPanel.add(instructionsScrollPane, gbc);

                underSearchPanel.add(viewPanel);
                underSearchPanel.revalidate();
                underSearchPanel.repaint();
            }

            @Override
            public void onDelete(int row) {
                if (row > 0) {
                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }
                    FoodTableModel model = (FoodTableModel) table.getModel();
                    model.fireTableRowsDeleted(row, row);
                    recipes.deleteRecipe(recipes.recipeList.get(row).getName());
                } else {
                    JOptionPane.showMessageDialog(table, "Please select a row to delete.");
                }
            }
        };

        field.setSearchFieldListener(new SearchFieldListener() {
            @Override
            public void onSuggestionSelected(String suggestion) {
                int recipeIndex = recipes.findByName(suggestion);
                if (recipeIndex >= 0) {
                    int viewIndex = table.convertRowIndexToView(recipeIndex);

                    table.setRowSelectionInterval(viewIndex, viewIndex);

                    event.onView(recipeIndex);
                }
            }
        });
        actionColumn.setCellEditor(new TableActionCellEditor(event));
        actionColumn.setCellRenderer(new TableActionCellRenderer());

        // list cell for food
        scrollTable.setPreferredSize(new Dimension(1100, 700));
        searchPanel.add(field, BorderLayout.NORTH);
        Panel1.add(searchPanel, BorderLayout.NORTH);
        Panel1.add(underSearchPanel, BorderLayout.SOUTH);
        contentPanel.add(Panel1, BorderLayout.WEST);
        contentPanel.add(scrollTable, BorderLayout.EAST);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JPanel createListItemPanel(Food recipe) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Separator

        ImageIcon imageIcon = new ImageIcon("src/images/" + recipe.getName().replace(" ", "") + ".png");
        Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        itemPanel.add(imageLabel);

        JLabel nameLabel = new JLabel(recipe.getName());
        nameLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        itemPanel.add(nameLabel);

        return itemPanel;
    }

    public JPanel createRecipePanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 20)); // Increased font size
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space after title

        JPanel recipeGrid = new JPanel(new GridLayout(1, 4, 15, 15));
        recipeGrid.setAlignmentX(Component.LEFT_ALIGNMENT); // Align grid to the left

        int count = 0;
        while (count < 5) {
            int rand = recipes.suggestRecipe();
            Food recipe = recipes.recipeInfoIndex(rand);
            if (count >= 5)
                break; // Display only 4 recipes
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(Color.WHITE); // White background for recipe items
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); 

            ImageIcon imageIcon = new ImageIcon(imgSrc + recipe.getName().replace(" ", "") + ".jpg");
            Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            itemPanel.add(imageLabel, BorderLayout.CENTER);

            JLabel nameLabel = new JLabel(recipe.getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Roboto", Font.BOLD, 14)); // Bold recipe names
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

        // action from navigation panel to switch to each sections
        @Override
        public void actionPerformed(ActionEvent e) {
            Component[] components = ((JPanel) frame.getContentPane().getComponent(0)).getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    component.setBackground(new Color(208, 74, 101));
                }
            }
            // Highlight the selected button
            ((JButton) e.getSource()).setBackground(new Color(193, 75, 89));

            if (itemName.equals("Main")) {
                showMainContent();
            } else if (itemName.equals("List")) {
                showRecipeList();
            } else if (itemName.equals("New Recipe")) {
                AddRecipe addRecipePanel = new AddRecipe(contentPanel, recipes);
            } else if (itemName.equals("Tutorial")) {
                showTutorialPanel();
            } else if (itemName.equals("About Us")) {
                AboutUs infoPanel = new AboutUs(contentPanel);
            }
        }
    }

    public void showTutorialPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        String htmlContent = "<html>\n" +
                     "<head>\n" +
                     "<title>FoodIO Tutorial</title>\n" +
                     "<style>\n" +
                     "body {\n" +
                     "    font-family: sans-serif;\n" +
                     "    margin: 20px;\n" +
                     "}\n" +
                     "h1, h2 {\n" +
                     "    color: #336699;\n" +
                     "}\n" +
                     "ol {\n" +
                     "    line-height: 1.6;\n" +
                     "}\n" +
                     ".step {\n" +
                     "    margin-bottom: 20px;\n" +
                     "}\n" +
                     ".tip {\n" +
                     "    background-color: #f0f0f0;\n" +
                     "    padding: 10px;\n" +
                     "    border-radius: 5px;\n" +
                     "    margin-top: 10px;\n" +
                     "}\n" +
                     "</style>\n" +
                     "</head>\n" +
                     "<body>\n" +
                     "\n" +
                     "<h1>FoodIO Tutorial</h1>\n" +
                     "\n" +
                     "<p>Welcome to FoodIO! This app simplifies food suggestion and management.</p>\n" +
                     "\n" +
                     "<div class=\"step\">\n" +
                     "    <h2>Step 1: Create Recipes</h2>\n" +
                     "    <ol>\n" +
                     "        <li>Navigate to \"New Recipe\".</li>\n" +
                     "        <li>Enter food name, category, ingredients, and instructions.</li>\n" +
                     "        <li>Add a photo.</li>\n" +
                     "        <li>Click \"Submit\".</li>\n" +
                     "    </ol>\n" +
                     "</div>\n" +
                     "\n" +
                     "<div class=\"step\">\n" +
                     "    <h2>Step 2: Search Recipes</h2>\n" +
                     "    <ol>\n" +
                     "        <li>Use the search bar.</li>\n" +
                     "        <li>Enter ingredients.</li>\n" +
                     "        <li>View suggested recipes.</li>\n" +
                     "    </ol>\n" +
                     "</div>\n" +
                     "\n" +
                     "<div class=\"tip\">\n" +
                     "    <h3>Tips:</h3>\n" +
                     "    <ul>\n" +
                     "        <li>Categorize recipes for easy access.</li>\n" +
                     "        <li>Use specific ingredients for better search results.</li>\n" +
                     "    </ul>\n" +
                     "</div>\n" +
                     "\n" +
                     "</body>\n" +
                     "</html>";
        editorPane.setText(htmlContent);
        editorPane.setEditable(false);
        JScrollPane tutorialPanel = new JScrollPane(editorPane);
        
        contentPanel.add(tutorialPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}