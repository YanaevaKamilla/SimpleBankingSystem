package banking.DataBase;

import banking.Table.TableInitializer;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Database class is responsible for connecting to the database and initializing the banking system.
 */
public class Database {

    /**
     * Initializes the database and starts the banking system.
     */
    public static void initializeDatabase() {
        try (Connection connection = DBConnection.getConnection()) {
            // Check if the connection is established
            if (connection != null) {
                // Create necessary tables
                TableInitializer.createTable();
            }
        } catch (SQLException e) {
            // Print stack trace if there is an SQL exception
            e.printStackTrace();
        } finally {
            // Close the database connection
            DBConnection.closeConnection();
        }
    }

}