package recipe;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.geom.Rectangle2D;

public class SearchField extends JTextField {
    private float location;
    private boolean show;
    private String labelText = "Search ...";
    private Color lineColor = new Color(33, 155, 216);
    private boolean mouseOver = false;
   
    public SearchField(int top, int bottom, int left,int right) {
        setBorder(new EmptyBorder(top,left,bottom, right));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                show = true;
                animateHint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                show = false;
                animateHint();
            }
        });
    }
    public SearchField() {
        this(10,10,3,5);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width = getWidth();
        int height = getHeight();
        if (mouseOver) {
            g2.setColor(Color.yellow);
        } else {
            g2.setColor(new Color(76, 204, 255));
        }
        g2.fillRect(2, height - 2, width - 4, 1);
        if(getText().isEmpty()){
        createHintText(g2);
        }
        
        createLineStyle(g2);
        g2.dispose();
    }

    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(new Color(150, 150, 150));
        FontMetrics fnt = g2.getFontMetrics();
        Rectangle2D r2 = fnt.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom;
        double textY = (height - r2.getHeight() / 2);
        g2.drawString(labelText, in.right, (int) (in.top + textY + fnt.getAscent() * (1 - location)));
    }

    private void createLineStyle(Graphics2D g2) {
        if (isFocusOwner()) {
            double width = getWidth() - 4;
            int height = getHeight();
            g2.setColor(lineColor);
            double size = 1, x = (width - size) - 2;
            g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
        }
    }

    private void animateHint() {
        Timer timer = new Timer(10, e -> {
            if (show) {
                location += 0.05f;
                if (location > 1) {
                    location = 1;
                    ((Timer) e.getSource()).stop();
                }
            } else {
                location -= 0.05f;
                if (location < 0) {
                    location = 0;
                    ((Timer) e.getSource()).stop();
                }
            }
            repaint();
        });
        timer.start();
    }
}
