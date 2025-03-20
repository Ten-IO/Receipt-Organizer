package frontDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedImagePanel extends JPanel {
    private Image image;

    public RoundedImagePanel(ImageIcon imageIcon) {
        this.image = imageIcon.getImage();
        setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
        setOpaque(false); // Ensure transparency
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a rounded rectangle clip
        Shape clipShape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15); // Corner arcs: 30px
        g2d.setClip(clipShape);

        // Draw the image inside the rounded rectangle
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        g2d.dispose();
    }
}
