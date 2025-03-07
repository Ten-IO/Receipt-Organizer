package ImageRender;

import javax.swing.*;

public class YourClass {

    public void displayImage(JPanel panel) {
        ImageIcon imageIcon = ImageUtils.getScaledImageIcon("src/images/", "BunCha", 735, 490);
        JLabel label = new JLabel(imageIcon);
        panel.add(label);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);

            JPanel panel = new JPanel();
            frame.add(panel);

            YourClass yourClass = new YourClass();
            yourClass.displayImage(panel);

            frame.setVisible(true);
        });
    }
}
