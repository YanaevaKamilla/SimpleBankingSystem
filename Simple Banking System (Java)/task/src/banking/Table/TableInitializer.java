package banking.Table;

import banking.DataBase.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The TableInitializer class initializes the database table for storing card information.
 * It provides a method to create the "card" table if it does not already exist in the database.
 */
public class TableInitializer {

    /**
     * Creates the "card" table in the database if it does not exist.
     */
    public static void createTable() {

        // SQL statement to create the "card" table
        String sql = "CREATE TABLE IF NOT EXISTS card (" +
                "id INTEGER PRIMARY KEY," +
                "number TEXT (16)," +
                "pin TEXT (4)," +
                "balance INTEGER DEFAULT 0" +
                ");";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Execute the SQL statement to create the table
            statement.executeUpdate();
            // Close the database connection
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}