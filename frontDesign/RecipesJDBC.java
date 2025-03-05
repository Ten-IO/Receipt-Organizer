package frontDesign;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecipesJDBC {

    public static class DatabaseConnector {
        private static final String URL = "jdbc:mysql://localhost:3306/myDB";
        private static final String USER = "root";
        private static final String PASSWORD = "admin123";

        public static Connection getConnection() {
            try {
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Database connection failed: " + e.getMessage());
                return null;
            }
        }
    }

    public void loadCSV(String filepath) throws SQLException {
        String sql = "LOAD DATA INFILE '" + filepath + "' INTO TABLE recipes " +
                     "FIELDS TERMINATED BY ',' ENCLOSED BY '\"' " +
                     "LINES TERMINATED BY '\\n' " +
                     "IGNORE 1 ROWS;";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RecipesJDBC db = new RecipesJDBC();
        try {
            db.loadCSV("localfood.csv");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
