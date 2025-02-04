import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameFeature extends JFrame implements ActionListener {
    JButton menuButton1 = new JButton();
    JButton menuButton2 = new JButton();
    JButton menuButton3 = new JButton();
    JButton menuButton4 = new JButton();
    JButton menuButton5 = new JButton();
    JButton menuButton6 = new JButton();
    JButton menuButton7 = new JButton();
    JLabel testMain = new JLabel(); // Declare testMain as a member variable

    FrameFeature (){
        ImageIcon icon = new ImageIcon("alya.png");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setIconImage(icon.getImage());
        this.setTitle("Food Organizer");
        // this.getContentPane().setBackground(new Color(135,206,250));
    
        //Panel
        JPanel TopHomeLine = new JPanel();
        TopHomeLine.setBackground(new Color(255,127,127));
        TopHomeLine.setBounds(0,0,screenSize.width, 100);

        //label 
        JLabel label_Welcome = new JLabel();
        label_Welcome.setText("FoodiO");
        label_Welcome.setFont(new Font("MV Boli",Font.PLAIN, 40)); 
        label_Welcome.setBounds( 50, 25 , 300 ,50); 

        //Menu
        menuButton1.setBounds(250, 25, 120 , 50);
        menuButton1.setText("Main");
        menuButton1.addActionListener(this);

        menuButton2.setBounds(370, 25, 120 , 50); 
        menuButton2.setText("List");

        menuButton3.setText("New Recipe");
        menuButton3.setBounds(490, 25, 120 , 50); 

        menuButton4.setText("Update Recipe");
        menuButton4.setBounds(610, 25, 120 , 50); 

        menuButton5.setText("Delete Recipe");
        menuButton5.setBounds(730, 25, 120 , 50); 

        menuButton6.setText("Search Recipe");
        menuButton6.setBounds(850, 25, 120 , 50);

        menuButton7.setText("About us");
        menuButton7.setBounds(970, 25, 120 , 50); 

        this.add(menuButton1);
        this.add(menuButton2);
        this.add(menuButton3);
        this.add(menuButton4);
        this.add(menuButton5);
        this.add(menuButton6);
        this.add(menuButton7);

        this.add(label_Welcome);
        this.add(TopHomeLine); 

        // Initialize testMain
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        testMain.setText("Main Menu");
        testMain.setIcon(scaledIcon);
        testMain.setHorizontalTextPosition(JLabel.CENTER);
        testMain.setVerticalTextPosition(JLabel.BOTTOM);
        testMain.setBounds(0, 100, 100, 150); // Adjust bounds to fit the scaled image and text
        testMain.setVisible(false); // Initially set to invisible
        this.add(testMain);

        this.setSize(screenSize.width, screenSize.height);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuButton1){
            System.out.println("Main Menu");
            testMain.setVisible(true);
        }
    }
}
