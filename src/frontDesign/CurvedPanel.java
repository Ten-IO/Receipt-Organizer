package frontDesign;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class CurvedPanel extends JPanel {

    private int arcWidth;
    private int arcHeight;

    public CurvedPanel(int arcWidth, int arcHeight) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle
        Shape roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        g2.setColor(getBackground());
        g2.fill(roundedRectangle);

        g2.dispose();
    }
}
