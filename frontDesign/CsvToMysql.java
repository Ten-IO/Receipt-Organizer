package frontDesign;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class CsvToMysql {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/recipe_db";
        String username = "root"; 
        String password = "admin123"; 
        String csvFilePath = "src/files/localFood.csv"; 

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

            String line;
            // for skipping header row
            reader.readLine(); 

            int o=0;
            String sql = "INSERT INTO recipes (Name, Category, Ingredients, Instructions) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                while ((line = reader.readLine()) != null) {
                    String[] values = line.split("\",\"");
                    if (values.length == 4) {

                        // remove trailing quote from csv
                        values[0] = values[0].replace("\"", "");
                        values[3] = values[3].replace("\"", "");

                        preparedStatement.setString(1, values[0]);
                        preparedStatement.setString(2, values[1]);
                        preparedStatement.setString(3, values[2]);
                        preparedStatement.setString(4, values[3]);
                        preparedStatement.executeUpdate();
                        System.out.println(o++);
                    } else{
                        System.err.println("Skipping malformed line: " + line);
                    }
                }
            }

            System.out.println("Data imported successfully.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}