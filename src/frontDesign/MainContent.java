package frontDesign;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import recipe.Recipes;

public class MainContent {
    private JPanel contentPanel;
    private Recipes recipes;

    public MainContent(JPanel contentPanel, Recipes recipes) {
        this.contentPanel = contentPanel;
        this.recipes = recipes;
    }

    public JPanel createPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        CreateMainContentPanel flowRecipePanel = new CreateMainContentPanel(recipes);

        // recent food
        JPanel recentPanel = flowRecipePanel.createRecipePanel("Recent Recipe");
        JScrollPane recentScrollPane = new JScrollPane(recentPanel);
        recentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        recentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        recentScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Recent"));

        // popular food
        JPanel popularPanel = flowRecipePanel.createRecipePanel("Today's Recipe");
        JScrollPane popularScrollPane = new JScrollPane(popularPanel);
        popularScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        popularScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        popularScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Suggestion"));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        mainPanel.add(recentScrollPane, gbc);

        gbc.gridy = 1;

        mainPanel.add(popularScrollPane, gbc);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(mainPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
        return contentPanel;
    }
}