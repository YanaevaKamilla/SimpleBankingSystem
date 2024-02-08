package banking.ShowMenu;

import banking.Account.AccountManager;
import banking.DataBase.DBConnection;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * The AccountMenu class is responsible for displaying the account menu and handling user interactions.
 */
public class AccountMenu {

    /**
     * Displays the account menu and handles user interactions.
     *
     * @return false when the user chooses to log out or exit, true otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean accountMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        // Continue displaying the account menu until the user chooses to exit or log out
        while (true) {
            System.out.println("\n" +
                    "1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");
            // Take user input
            String input = scanner.nextLine();
            // Switch based on user input
            switch (input) {
                case "1" -> AccountManager.getBalance(); // Display account balance
                case "2" -> AccountManager.addIncome(); // Add income
                case "3" -> AccountManager.makeTransfer(); // Perform money transfer
                case "4" -> AccountManager.deleteAccount(); // Close account
                case "5" -> {
                    // Log out and return to the main menu
                    System.out.println("\nYou have successfully logged out!\n");
                    return false;
                }
                case "0" -> {
                    AccountManager.printExitMessage(); // Exit the program
                    if (DBConnection.connection != null) {
                        DBConnection.closeConnection();
                        scanner.close();
                        System.exit(0);
                    }
                    return false; // Exit the loop when the user chooses to exit
                }
                default -> System.out.println("\nInvalid Input"); // Display message for invalid input
            }
        }
    }

}