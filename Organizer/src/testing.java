package Food;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class testing {
	private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel detailMainJPanel;

    public testing(FrameFeature frame) {
        this.cardLayout = frame.getCardLayout(); 
        this.mainPanel = frame.getMainPanel(); 
        
        createMain();
        mainPanel.add(detailMainJPanel, "testing"); 
    }
    private void createMain() {
    	mainPanel = new JPanel(new BorderLayout());
    	JLabel titleLabel = new JLabel("Testing ", SwingConstants.CENTER);
    	JTextArea aboutText = new JTextArea();
        aboutText.setText("Welcome to FoodiO!\n\nWe are dedicated to sharing the best recipes with you. \n"
                + "Explore our collection and enjoy cooking!");
        aboutText.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "List"));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(aboutText, BorderLayout.CENTER);
        
        detailMainJPanel.add(textPanel, BorderLayout.CENTER);
    	detailMainJPanel.add(titleLabel,BorderLayout.NORTH);
    	
    }
	
}
