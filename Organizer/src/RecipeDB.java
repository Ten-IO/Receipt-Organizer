package Food;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;

public class RecipeDB {
	
	public static List<String> getPic() throws SQLException {
		List <String> pic = new ArrayList<String>();
	    String sql = "SELECT picture FROM Recipes ORDER BY name"; // Get latest I
	    Connection conn = DatabaseConnector.getConnection();
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();

	    while(rs.next()) {
	        pic.add(rs.getString("picture"));
	    }

	    rs.close();
	    stmt.close();
	    conn.close();
	    
	    return pic;
	}
	public static List<String> getName() throws SQLException {
		List <String> name = new ArrayList<String>();
	    String sql = "SELECT name FROM Recipes ORDER BY name"; // Get latest ID
	    Connection conn = DatabaseConnector.getConnection();
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();

	    while(rs.next()) {
	        name.add(rs.getString("name"));
	    }

	    rs.close();
	    stmt.close();
	    conn.close();
	    
	    return name;
	}
	public static List<String> getIngredient() throws SQLException {
		List <String> ingredient = new ArrayList<String>();
	    String sql = "SELECT ingredient FROM Recipes ORDER BY name"; // Get latest ID
	    Connection conn = DatabaseConnector.getConnection();
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();

	    while(rs.next()) {
	        ingredient.add(rs.getString("ingredient"));
	    }

	    rs.close();
	    stmt.close();
	    conn.close();
	    
	    return ingredient;
	}

	public static void addRecipe(String name, String ingredients, String category, String picture) {
	    // Check if the recipe name already exists
	    String checkSql = "SELECT COUNT(*) FROM Recipes WHERE name = ?";
	    String insertSql = "INSERT INTO Recipes (name, ingredient, category, picture) VALUES (?, ?, ?, ?)";

	    try (Connection conn = DatabaseConnector.getConnection();
	         PreparedStatement checkStmt = conn.prepareStatement(checkSql);
	         PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

	        // Set parameter for checking existence
	        checkStmt.setString(1, name);
	        ResultSet rs = checkStmt.executeQuery();
	        rs.next(); 
	        int count = rs.getInt(1);

	        if (count > 0) {
	            System.out.println("Recipe with this name already exists!");
	            JOptionPane.showMessageDialog(null, "Recipe already existed.....", "Error", count);
	            return;
	        }

	        // Insert new recipe if the name does not exist
	        insertStmt.setString(1, name);
	        insertStmt.setString(2, ingredients);
	        insertStmt.setString(3, category);
	        insertStmt.setString(4, picture);

	        insertStmt.executeUpdate();
	        System.out.println("Recipe added successfully!");
	    } catch (SQLException e) {
	        System.out.println("Error adding recipe: " + e.getMessage());
	    }
	}
	public static void deleteRecipe(String name) {
	    // Check if the recipe name exists
	    String checkSql = "SELECT COUNT(*) FROM Recipes WHERE name = ?";
	    String deleteSql = "DELETE FROM Recipes WHERE name = ?";

	    try (Connection conn = DatabaseConnector.getConnection();
	         PreparedStatement checkStmt = conn.prepareStatement(checkSql);
	         PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

	        // Set parameter for checking existence
	        checkStmt.setString(1, name);
	        ResultSet rs = checkStmt.executeQuery();
	        rs.next(); 
	        int count = rs.getInt(1);

	        if (count == 0) {
	            System.out.println("Recipe does not exist.");
	            JOptionPane.showMessageDialog(null, "Recipe does not exist", "Error", JOptionPane.ERROR_MESSAGE);
	            return; // Exit the method if the recipe does not exist
	        }

	        // Set parameter for deletion and execute
	        deleteStmt.setString(1, name);
	        int rowsAffected = deleteStmt.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Recipe deleted successfully!");
	            JOptionPane.showMessageDialog(null, "Recipe deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            System.out.println("Error: Recipe could not be deleted.");
	            JOptionPane.showMessageDialog(null, "Error: Recipe could not be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
	        }

	    } catch (SQLException e) {
	        System.out.println("Error deleting recipe: " + e.getMessage());
	        JOptionPane.showMessageDialog(null, "Error deleting recipe: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	    
	}
	public static void updateRecipe(String name, String newCategory, String newIngredients, String newPicture) {
	    // Check if the recipe exists
	    String checkSql = "SELECT COUNT(*) FROM Recipes WHERE name = ?";
	    String updateSql = "UPDATE Recipes SET category = ?, ingredient = ?, picture = ? WHERE name = ?";

	    try (Connection conn = DatabaseConnector.getConnection();
	         PreparedStatement checkStmt = conn.prepareStatement(checkSql);
	         PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

	        // Check if recipe exists
	        checkStmt.setString(1, name);
	        ResultSet rs = checkStmt.executeQuery();
	        rs.next();
	        int count = rs.getInt(1);

	        if (count == 0) {
	            System.out.println("Recipe does not exist.");
	            JOptionPane.showMessageDialog(null, "Recipe does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
	            return; // Stop execution if the recipe does not exist
	        }

	        // Set update parameters
	        updateStmt.setString(1, newCategory);
	        updateStmt.setString(2, newIngredients);
	        updateStmt.setString(3, newPicture);
	        updateStmt.setString(4, name);

	        // Execute update
	        int rowsAffected = updateStmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Recipe updated successfully!");
	            JOptionPane.showMessageDialog(null, "Recipe updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            System.out.println("Failed to update recipe.");
	            JOptionPane.showMessageDialog(null, "Failed to update recipe.", "Error", JOptionPane.ERROR_MESSAGE);
	        }

	    } catch (SQLException e) {
	        System.out.println("Error updating recipe: " + e.getMessage());
	        JOptionPane.showMessageDialog(null, "Error updating recipe: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	}



}
