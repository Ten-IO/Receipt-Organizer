package frontDesign;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;

public class CustomTextArea extends JTextArea {

    public CustomTextArea(String text) {
        super(text);
        initialize();
    }

    // Constructor for text, rows, and columns
    public CustomTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
        initialize();
    }

    private void initialize() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBackground(Color.LIGHT_GRAY);
        setForeground(Color.BLACK);
        setEditable(false);
    }

    public void setCustomFont(Font font) {
        setFont(font);
    }

    public void setCustomBackground(Color color) {
        setBackground(color);
    }

    public void setCustomForeground(Color color) {
        setForeground(color);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
    }
}
