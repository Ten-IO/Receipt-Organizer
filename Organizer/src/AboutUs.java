package Food;


import javax.swing.*;
import java.awt.*;

public class AboutUs {
    private JPanel aboutPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public AboutUs(FrameFeature frame) {
        this.cardLayout = frame.getCardLayout(); // Get CardLayout from main frame
        this.mainPanel = frame.getMainPanel(); // Get main panel

        createAboutPanel();
        mainPanel.add(aboutPanel, "AboutUs"); // Add the About Us panel to CardLayout
    }

    private void createAboutPanel() {
        aboutPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("About Us", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JTextArea aboutText = new JTextArea();
        aboutText.setText("Welcome to FoodiO!\n\nWe are dedicated to sharing the best recipes with you. \n"
                + "Explore our collection and enjoy cooking!");
        aboutText.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "List")); // Go back to recipe list

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(aboutText, BorderLayout.CENTER);

        aboutPanel.add(textPanel, BorderLayout.CENTER);
        aboutPanel.add(backButton, BorderLayout.SOUTH);
    }
}
