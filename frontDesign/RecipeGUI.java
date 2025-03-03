package frontDesign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;


import recipe.CsvHandler;
import recipe.Food;
import recipe.Recipes;
import recipe.TxtHandler;
import recipe.Splitter;

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
    }

    public JPanel createNavPanel() {

        // get logo
        ImageIcon imageIcon = new ImageIcon(imgSrc + "Food4.png");
        // Image image = imageIcon.getImage().getScaledInstance(100,
        // 75,Image.SCALE_AREA_AVERAGING);
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
            button.setBackground(item.equals("Main") ? new Color(193, 75, 89) : new Color(208, 74, 101)); // rgb(231,
                                                                                                          // 162, 176)
                                                                                                          // #D04A65
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
        recentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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
        JPanel searchPanel = new JPanel(new BorderLayout());
        

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
                searchPanel.removeAll();
                JPanel editPanel = new JPanel(new GridLayout(5, 1, 5, 20));
                searchPanel.add(editPanel);

                JLabel foodLabel = new JLabel("Food");
                CustomField foodField = new CustomField(5, 5, 10, 10);
                foodLabel.setBorder(new EmptyBorder(10,10,10,10));
                foodLabel.add(foodField);
                foodField.setBorder(new EmptyBorder(20, 20, 20, 20));
                CustomField categoryField = new CustomField(5, 5, 10, 10);
                CustomField ingredientField = new CustomField(5, 5, 10, 10);
                CustomField instructionField = new CustomField(5, 5, 10, 10, "write instruction...");

                editPanel.add(foodLabel);
                editPanel.add(categoryField);
                editPanel.add(ingredientField);
                editPanel.add(instructionField);

                JButton save = new JButton();
                save.setText("save");
                save.setFocusable(false);
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        recipes.updateRecipe(foodField.getText(), categoryField.getText(), ingredientField.getText(),
                                instructionField.getText());
                    }

                });
                editPanel.add(save);

                searchPanel.add(editPanel);
                searchPanel.revalidate();
                searchPanel.repaint();
            }

            @Override
            public void onView(int row) {
                searchPanel.removeAll();
                JPanel viewPanel = new JPanel(new GridLayout(4, 1, 5, 20));
                String food = recipes.recipeList.get(row).getName();
                String category = recipes.recipeList.get(row).getCategory();
                List<String> ingredients = recipes.recipeList.get(row).getIngredient();
                List<String> instructions = recipes.recipeList.get(row).getInstruction();

                CustomTextArea ingredientsArea = new CustomTextArea(
                        "Ingredient:\n" + Splitter.listToString(ingredients));
                CustomTextArea instructionsArea = new CustomTextArea(
                        "Instruction:\n" + Splitter.listToString(instructions));
                viewPanel.add(new CustomTextArea("Food: " + food));
                viewPanel.add(new CustomTextArea("Category: " + category));

                viewPanel.add(ingredientsArea);
                viewPanel.add(instructionsArea);
                searchPanel.add(viewPanel);
                searchPanel.revalidate();
                searchPanel.repaint();
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
                System.out.println("onSuggestionSelected called with: " + suggestion); // Debugging
                int recipeIndex = recipes.findByName(suggestion);
                if (recipeIndex >= 0) {
                    event.onView(recipeIndex);
                }
            }
        });
        actionColumn.setCellEditor(new TableActionCellEditor(event));
        actionColumn.setCellRenderer(new TableActionCellRenderer());

        // list cell for food
        scrollTable.setPreferredSize(new Dimension(1100, 700));
        searchPanel.add(field, BorderLayout.NORTH);
        contentPanel.add(searchPanel, BorderLayout.CENTER);
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

        JPanel recipeGrid = new JPanel(new GridLayout(1, 4, 15, 15)); // Increased spacing
        recipeGrid.setAlignmentX(Component.LEFT_ALIGNMENT); // Align grid to the left

        int count = 0;
        for (Food recipe : recipes.recipeList) {
            if (count >= 5)
                break; // Display only 4 recipes
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(Color.WHITE); // White background for recipe items
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Light border

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

        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset background color for all buttons
            Component[] components = ((JPanel) frame.getContentPane().getComponent(0)).getComponents(); // Navigation
                                                                                                        // panel is the
                                                                                                        // first
                                                                                                        // component
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