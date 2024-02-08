package banking.Table;

import banking.DataBase.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DropTable class is responsible for dropping the "card" table from the database.
 * It provides a method to execute the SQL query to drop the table.
 */
public class DropTable {

    /**
     * Drops the "card" table from the database if it exists.
     */
    public static void dropTable() {
        try {
            // Establish a connection to the database
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            // SQL query to drop the "card" table if it exists
            String query = "DROP TABLE IF EXISTS card";
            // Execute the SQL query
            statement.executeUpdate(query);
            System.out.println("Table 'card' dropped successfully.");
            // Close the database connection
            DBConnection.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}