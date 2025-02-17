package Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;

public class RecipeDB {
	public static List<Integer> getID() throws SQLException {
		List <Integer> ID = new ArrayList<Integer>();
	    String sql = "SELECT id FROM Recipes "; // Get latest ID
	    int id = -1; // Default if no ID exists
	    Connection conn = DatabaseConnector.getConnection();
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();

	    while(rs.next()) {
	        ID.add(rs.getInt("id"));
	    }

	    rs.close();
	    stmt.close();
	    conn.close();
	    
	    return ID;
	}
	public static List<String> getPic() throws SQLException {
		List <String> pic = new ArrayList<String>();
	    String sql = "SELECT picture FROM Recipes "; // Get latest I
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
	    String sql = "SELECT name FROM Recipes "; // Get latest ID
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
	    String sql = "SELECT ingredient FROM Recipes "; // Get latest ID
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

    public static void addRecipe(int id, String name, String ingredients, String category) {
        String sql = "INSERT INTO Recipes (id,name, ingredient, category) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, ingredients);
            stmt.setString(4, category);
         
            
            stmt.executeUpdate();
            System.out.println("Recipe added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding recipe: " + e.getMessage());
        }
    }
}
