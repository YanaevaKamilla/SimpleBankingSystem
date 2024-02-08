package banking;

import banking.DataBase.Database;
import banking.ShowMenu.UserMenu;

/**
 * The SimpleBankingSystem class is the entry point for the banking application.
 * It initializes the database and displays the main menu for user interaction.
 */
public class SimpleBankingSystem {

    /**
     * The main method initializes the database and displays the main menu for user interaction.
     *
     * @param args The command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Database.initializeDatabase(); // Initialize the database
        UserMenu.showUserMenu(); // Display the main menu for user interaction
    }

}