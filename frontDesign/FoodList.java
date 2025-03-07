package frontDesign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.RowFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import recipe.Food;
import recipe.Recipes;
import recipe.Splitter;

public class FoodList {
    private JPanel contentPanel;
    private Recipes recipes;
    private String imgSrc;

    FoodList(JPanel contentPanel, Recipes recipes) {
        this.contentPanel = contentPanel;
        this.recipes = recipes;
    }

    public void createPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(1, 2));

        List<String> recipeNames = new ArrayList<>();
        for (Food food : recipes.recipeList) {
            recipeNames.add(food.getName());
        }

        // Search
        AutoSuggestSearchField field = new AutoSuggestSearchField(recipeNames);
        JPanel Panel1 = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setSize(new Dimension(Panel1.getWidth(), 100));
        JPanel underSearchPanel = new JPanel(new BorderLayout());

        // Create foodTableModel
        FoodTableModel foodTable = new FoodTableModel(recipes.recipeList);
        JTable table = new JTable(foodTable);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(5);
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
            public void onEdit(int editRow) {
                underSearchPanel.removeAll();
                int row = table.convertRowIndexToModel(editRow);

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
                gbc.weighty = 0.1; 
                editPanel.add(foodScrollPane, gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0; 
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
                        createPanel();
                    }
                });
                buttonPanel.add(save);

                JButton cancel = new JButton("Cancel");
                cancel.setFocusable(false);
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createPanel();
                    }
                });
                buttonPanel.add(cancel);

                editPanel.add(buttonPanel, gbc);

                underSearchPanel.add(editPanel);
                underSearchPanel.revalidate();
                underSearchPanel.repaint();
            }

            @Override
            public void onView(int viewRow) {
                underSearchPanel.removeAll();
                int row = table.convertRowIndexToModel(viewRow);
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

                JLabel foodLabel = new JLabel("Food");

                JTextArea foodArea = new JTextArea(food);
                foodArea.setBackground(Color.LIGHT_GRAY);
                foodArea.setEditable(false);
                viewPanel.add(foodLabel, gbc);
                gbc.gridy++;
                viewPanel.add(foodArea, gbc);
                gbc.gridy++;

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.5;
                gbc.weighty = 0.5;

                ImageIcon imageIcon = new ImageIcon(imgSrc+ food.replace(" ", "") + ".jpg");
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
            public void onDelete(int deleteRow) {
                int row = table.convertRowIndexToModel(deleteRow);

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
}