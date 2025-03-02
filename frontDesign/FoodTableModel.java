package frontDesign;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import recipe.Food;

public class FoodTableModel extends AbstractTableModel {
    private List<Food> foodList;
    private String[] columnNames = { "Name", "Category", "Choice"};

    public FoodTableModel(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public int getRowCount() {
        return foodList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Food food = foodList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return food.getName();
            case 1:
                return food.getCategory();
            default:
                return null;

        }
    }

    // forget this, table does not return cell for edit
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        
        return columnIndex == 2;
    }

}
