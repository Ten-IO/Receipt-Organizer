package frontDesign;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class HoverListCellRenderer extends DefaultListCellRenderer {
    private int hoverIndex = -1;

    public void setHoverIndex(int index) {
        this.hoverIndex = index;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (index == hoverIndex) {
            label.setBackground(Color.WHITE);
        } else {
            label.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        }
        return label;
    }
}
