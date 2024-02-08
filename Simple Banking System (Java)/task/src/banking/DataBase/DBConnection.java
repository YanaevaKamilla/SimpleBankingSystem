package banking.DataBase;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The DBConnection class is responsible for establishing a connection to the database.
 * It provides methods to start and close the database connection, and retrieve the connection object.
 */
public class DBConnection {

    public static String URL;
    public static String fileName = "card.s3db";
    public static Connection connection = null;

    /**
     * Constructs and returns a SQLiteDataSource object with the database URL.
     *
     * @return The SQLiteDataSource object.
     */
    private static SQLiteDataSource getDataSource() {
        // Construct the URL for the SQLite database
        URL = "jdbc:sqlite:" + fileName;
        // Create a new SQLiteDataSource object
        SQLiteDataSource dataSource = new SQLiteDataSource();
        // Set the URL for the SQLiteDataSource object
        dataSource.setUrl(URL);
        return dataSource;
    }

    /**
     * Establishes a connection to the database.
     *
     * @return The Connection object representing the database connection.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        SQLiteDataSource dataSource = getDataSource();
        try {
            // Get a connection from the DataSource
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            // Print stack trace if an SQL exception occurs
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                // Close the connection if it's not null
                connection.close();
            } catch (SQLException e) {
                // Print stack trace if an SQL exception occurs
                e.printStackTrace();
            }
        }
    }
}