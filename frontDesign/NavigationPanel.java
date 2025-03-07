package frontDesign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import recipe.Recipes;

public class NavigationPanel {
    private String imgSrc = "src/images/";
    private JFrame mainFrame;
    private JPanel contentPanel;
    private Recipes recipes;
    private JButton selectedButton; // Track the selected button

    public NavigationPanel(JFrame mainFrame, JPanel contentPanel, Recipes recipes) {
        this.mainFrame = mainFrame;
        this.contentPanel = contentPanel;
        this.recipes = recipes;
    }

    public JPanel createPanel() {
        ImageIcon imageIcon = new ImageIcon(imgSrc + "Food4.png");
        Image image = imageIcon.getImage();
        JLabel logoLabel = new JLabel(new ImageIcon(image));

        // Navigation Flow Panel left-right
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(208, 74, 101));
        navPanel.setPreferredSize(new Dimension(1024, 150));
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        navPanel.add(logoLabel);

        String[] navItems = { "Main", "List", "New Recipe", "Tutorial", "Chat Bot", "About Us" };
        for (String item : navItems) {
            JButton button = new JButton(item);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Roboto", Font.PLAIN, 18));
            button.setPreferredSize(new Dimension(150, 60));
            button.setFocusPainted(false);

            // Add mouse listener for hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(193, 75, 89));
                    button.setOpaque(true);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (button != selectedButton) {
                        button.setBackground(navPanel.getBackground());
                        button.setOpaque(false);
                    }
                }
            });

            // Add action listener with animation and selection tracking
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedButton != null) {
                        selectedButton.setBackground(navPanel.getBackground());
                        selectedButton.setOpaque(false);
                    }
                    selectedButton = button;
                    button.setBackground(new Color(193, 75, 89));
                    button.setOpaque(true);
                    new NavigationListener(mainFrame, contentPanel, recipes, item).actionPerformed(e);
                }
            });

            if (item.equals("Main")) {
                button.setBackground(new Color(193, 75, 89));
                button.setOpaque(true);
                selectedButton = button;
            }

            navPanel.add(button, BorderLayout.CENTER);
        }

        return navPanel;
    }
}
