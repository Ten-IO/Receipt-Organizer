package frontDesign;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;


public class  test {
    JFrame frame = new JFrame();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new test().initComponent());
    
    }
    public void initComponent() {
        CustomField field = new CustomField();
        CustomField field2 = new CustomField();
        JPanel testPanel = new JPanel(new BorderLayout());
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1024,640);
        frame.setVisible(true);
        
        testPanel.setBounds(20,20,20,20);
        testPanel.add(field, BorderLayout.NORTH);
        frame.add(testPanel);
        testPanel.add(field2, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
        // frame.add(new JScrollPane(foodTable()), BorderLayout.CENTER);
    
    }
    
    /*
    public JTable foodTable() {
        Recipes recipes = new Recipes();
        recipes.makeRecipe("a","b","c","d");
        recipes.makeRecipe("b","c","d","e");
        recipes.makeRecipe("c","d","e","f");
        
        FoodTableModel tableModel = new FoodTableModel(recipes.recipeList);
        JTable table = new JTable(tableModel);
        table.setRowHeight(40);
        
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onView(int row) {
                JLabel label = new JLabel();
                label.setText("Helllo");
                label.setBorder(new EmptyBorder(50,50,50,50));
                frame.add(label, BorderLayout.CENTER);
            }
            
            @Override
            public void onEdit(int row) {
                System.out.println("edit" + row);
            }

            @Override
            public void onDelete(int row) {
                System.out.println("delete" + row);
            }
        };
        table.getColumnModel().getColumn(2).setPreferredWidth(10);
        table.getColumnModel().getColumn(2).setCellRenderer(new TableActionCellRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new TableActionCellEditor(event));
        return table;
    }
        */

}
