package Food;


import javax.swing.*;
import java.awt.*;

public class testing {
    private JPanel aboutTesting;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public testing(FrameFeature frame) {
        this.cardLayout = frame.getCardLayout(); // Get CardLayout from main frame
        this.mainPanel = frame.getMainPanel(); // Get main panel

        createaboutTesting();
        mainPanel.add(aboutTesting, "testing"); // Add the About Us panel to CardLayout
    }

    private void createaboutTesting() {
        aboutTesting = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Editing food", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);
 

        aboutTesting.add(textPanel, BorderLayout.CENTER);
    }
}
