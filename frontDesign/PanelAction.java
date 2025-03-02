package frontDesign;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PanelAction extends javax.swing.JPanel {
    private ActionButton viewButton;
    private ActionButton editButton;
    private ActionButton deleteButton;


    public PanelAction() {
        Image editIconImage = new ImageIcon("src/images/edit.png").getImage().getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);
        Image deleteIconImage = new ImageIcon("src/images/remove.png").getImage().getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);
        Image viewIconImage = new ImageIcon("src/images/view.png").getImage().getScaledInstance(20, 20,
                Image.SCALE_SMOOTH);

        // Create ImageIcons from the rescaled images
        ImageIcon editIcon = new ImageIcon(editIconImage);
        ImageIcon deleteIcon = new ImageIcon(deleteIconImage);
        ImageIcon viewIcon = new ImageIcon(viewIconImage);

        viewButton = new ActionButton();
        editButton = new ActionButton();
        deleteButton = new ActionButton();

        viewButton.setIcon(viewIcon);
        editButton.setIcon(editIcon);
        deleteButton.setIcon(deleteIcon);

        setPreferredSize(new Dimension(10, 10));
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(viewButton);
        add(editButton);
        add(deleteButton);
    }

    public ActionButton getEditButton() {
        return editButton;
    }

    public ActionButton getDeleteButton() {
        return deleteButton;
    }

    public ActionButton getViewButton() {
        return viewButton;
    }

    public void initEvent(TableActionEvent event, int row) {
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.onView(row);
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.onEdit(row);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.onDelete(row);
            }
        });
    }

    private void makeTransparentButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBackground(null);
    }
}
