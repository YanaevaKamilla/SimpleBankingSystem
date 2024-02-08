package banking.Table;

import banking.DataBase.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The PrintTable class is responsible for printing the contents of the "card" table.
 * It provides a method to retrieve data from the database and print it in a formatted manner.
 */
public class PrintTable {

    /**
     * Prints the contents of the "card" table.
     */
    public static void printTable() {

        // SQL query to select all records from the "card" table
        String sql = "SELECT * FROM card";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Execute the SQL query
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Bank Table:");

            // Iterate through the result set and print each record
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String number = resultSet.getString("number");
                String pin = resultSet.getString("pin");
                int balance = resultSet.getInt("balance");

                System.out.printf("ID: %d, Number: %s, PIN: %s, Balance: %d\n", id, number, pin, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}