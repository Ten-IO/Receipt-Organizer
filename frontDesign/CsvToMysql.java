package frontDesign;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class CsvToMysql {

    public static void toMysql(String csvFilePaths) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/myDB";
        String username = "root";
        String password = "admin123";
        String csvFilePath = csvFilePaths;

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

            // Delete all existing data before inserting new data
            String deleteSql = "DELETE FROM recipe2";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.executeUpdate();
                System.out.println("All existing data deleted.");
            }

            // Skip header row
            reader.readLine();

            String sql = "INSERT INTO recipe2 (Name, Category, Ingredient, Instructions) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String line;
                int count = 0;

                while ((line = reader.readLine()) != null) {
                    String[] values = line.split("\",\"");
                    if (values.length == 4) {
                        values[0] = values[0].replace("\"", ""); // Remove leading quote
                        values[3] = values[3].replace("\"", ""); // Remove trailing quote

                        preparedStatement.setString(1, values[0]);
                        preparedStatement.setString(2, values[1]);
                        preparedStatement.setString(3, values[2]);
                        preparedStatement.setString(4, values[3]);
                        preparedStatement.executeUpdate();

                        System.out.println("Inserted row: " + ++count);
                    } else {
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
