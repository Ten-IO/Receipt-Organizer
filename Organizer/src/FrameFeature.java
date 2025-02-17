package Food;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameFeature extends JFrame implements ActionListener {
    private JButton menuButton1 = new JButton("Main");
    private JButton menuButton2 = new JButton("Food");
    private JButton menuButton3 = new JButton("Edit recipe");
    private JButton menuButton4 = new JButton("about us");
    private JButton menuButton5 = new JButton("testing");
    
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public FrameFeature() {
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);

        // Default view
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.add(new JLabel("Welcome to Food Organizer!"));
        mainPanel.add(mainMenuPanel, "Main");  
        
        JPanel TopHomeLine = new JPanel();
        TopHomeLine.setBackground(new Color(255,127,127));
        TopHomeLine.setLayout(new FlowLayout(FlowLayout.LEFT));
        TopHomeLine.add(menuButton1);
        TopHomeLine.add(menuButton2);
        TopHomeLine.add(menuButton3);
        TopHomeLine.add(menuButton4);
        TopHomeLine.add(menuButton5);

        menuButton1.addActionListener(this);
        menuButton2.addActionListener(this);
        menuButton3.addActionListener(this);
        menuButton4.addActionListener(this);
        menuButton5.addActionListener(this);


        this.setLayout(new BorderLayout());
        this.add(TopHomeLine, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton1) {
            cardLayout.show(mainPanel, "Main");
        } else if (e.getSource() == menuButton2) {
            cardLayout.show(mainPanel, "List");
        } else if (e.getSource() == menuButton3) {
            cardLayout.show(mainPanel, "Edit");
        } else if (e.getSource() == menuButton4) {
            cardLayout.show(mainPanel, "AboutUs");
        } else if (e.getSource() == menuButton5) {
            cardLayout.show(mainPanel, "testing");
        } 
        
    }
}
