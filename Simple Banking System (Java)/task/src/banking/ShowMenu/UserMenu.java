package banking.ShowMenu;

import banking.Account.AccountManager;
import banking.DataBase.DBConnection;

import java.util.Scanner;

/**
 * The UserMenu class provides methods to display the main menu for user interaction.
 */
public class UserMenu {

    /**
     * Displays the main menu for user interaction.
     */
    public static void showUserMenu() {
        Scanner scanner = new Scanner(System.in);
        // Continue displaying the menu until the user chooses to exit
        while (true) {
            System.out.println(
                    "1. Create an account\n" +
                            "2. Log into account\n" +
                            "0. Exit");
            // Take user input
            String input = scanner.nextLine();
            // Switch based on user input
            switch (input) {
                case "1" -> AccountManager.createAccount(); // Create a new account
                case "2" -> AccountManager.loginAccount(); // Log into an existing account
                case "0" -> {
                    AccountManager.printExitMessage(); // Exit the program
                    if (DBConnection.connection != null) {
                        DBConnection.closeConnection();
                        scanner.close();
                        System.exit(0);
                    }
                    return; // Exit the loop when the user chooses to exit
                }
                default -> System.out.println("Invalid Input"); // Display message for invalid input
            }
        }
    }

}