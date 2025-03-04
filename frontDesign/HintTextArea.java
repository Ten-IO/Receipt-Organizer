package frontDesign;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HintTextArea extends JTextArea {
    private String labelText = "Search ...";
    private boolean mouseOver = false;

    public HintTextArea(int top, int bottom, int left, int right) {
        setBorder(new EmptyBorder(top, left, bottom, right));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                setCaretPosition(0);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }
        });
    }

    public HintTextArea(String labelText) {
        this(10, 10, 3, 5);
        this.labelText = labelText;
    }

    public HintTextArea(int top, int bottom, int left, int right, String labelText) {
        this(top, left, bottom, right);
        this.labelText = labelText;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        if (getText().isEmpty()) {
            createHintText(g2);
        }

        g2.dispose();
    }

    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(new Color(150, 150, 150));
        FontMetrics fnt = g2.getFontMetrics();
        g2.drawString(labelText, in.left, in.top + fnt.getAscent());
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
        if (mouseOver) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(new Color(76, 204, 255));
        }
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 5); // Adjust insets to control padding
    }
}
