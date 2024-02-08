package banking.Account;

import banking.DataBase.DBConnection;
import banking.ShowMenu.UserMenu;
import banking.Table.TableUpdates;

import java.util.Scanner;

public class AccountManager {

    // Scanner object for user input
    public static Scanner scanner = new Scanner(System.in);
    // Variable to store the card number for transfer
    public static String cardNumberForTransfer;

    // Method to create a new account
    public static void createAccount() {
        // Generate a new card number and PIN
        String number = CardNumber.generateCardNumber();
        String pin = String.valueOf(PIN.generatePIN());
        // Add the new account to the database
        TableUpdates.addAccountToDB(number, pin);
        // Display the details of the newly created account
        displayNewAccount(number, pin);
    }

    // Method to display the details of a newly created account
    public static void displayNewAccount(String number, String pin) {
        // Display the newly created account details
        System.out.printf(
                "\nYour card has been created\n" +
                        "Your card number:\n" +
                        "%s\n" +
                        "Your card PIN:\n" +
                        "%s\n\n", number, pin);
    }

    // Method to delete an account
    public static void deleteAccount() {
        // Delete the account from the database
        TableUpdates.deleteAccount();
        // Display the main user menu
        UserMenu.showUserMenu();
    }

    // Method to add income to an account
    public static void addIncome() {
        System.out.println("\nEnter income:\n");
        int incomeValue = scanner.nextInt();
        scanner.nextLine();
        TableUpdates.addIncomeToBalance(TableUpdates.currentCardNumber, incomeValue);
    }

    // Method to get the balance of an account
    public static void getBalance() {
        int balance = TableUpdates.getBalance(TableUpdates.currentCardNumber);
        System.out.printf("\nBalance: %d%n", balance);
    }

    // Method to print an exit message and close the database connection
    public static void printExitMessage() {
        DBConnection.closeConnection();
        System.out.println("Bye!");
        System.exit(0);
    }

    // Method to log into an account
    public static void loginAccount() {
        System.out.println("\nEnter your card number:");
        String cardInput = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pinInput = scanner.nextLine();
        TableUpdates.authenticateCardAndPin(cardInput, pinInput);
    }

    // Method to initiate a money transfer
    public static void makeTransfer() {
        System.out.println("\nTransfer\n" +
                "Enter card number:");
        cardNumberForTransfer = scanner.nextLine().trim();

        if (cardNumberForTransfer.equals(TableUpdates.currentCardNumber)) {
            System.out.println("You can't transfer money to the same account!");
        } else if (cardNumberForTransfer.length() != 16) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else if (!isLuhnValid(cardNumberForTransfer)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
        } else if (!TableUpdates.isCardNumberExist(cardNumberForTransfer)) {
            System.out.println("Such a card does not exist.");
        } else {
            transferMoney();
        }
    }

    // Method to perform the money transfer
    private static void transferMoney() {
        System.out.println("Enter how much money you want to transfer:");
        int transferAmount = scanner.nextInt();
        scanner.nextLine();

        int senderBalance = TableUpdates.getBalance(TableUpdates.currentCardNumber);
        if (transferAmount > senderBalance) {
            System.out.println("Not enough money!");
        } else {
            TableUpdates.makeTransfer(TableUpdates.currentCardNumber, cardNumberForTransfer, transferAmount);
            System.out.println("Success!");
        }
    }

    // Method to check if a card number is valid using the Luhn algorithm
    private static boolean isLuhnValid(String cardNumberForTransfer) {
        int result = 0;
        for (int i = 0; i < cardNumberForTransfer.length(); i++) {
            int digit = Character.getNumericValue(cardNumberForTransfer.charAt(i));
            if (i % 2 == 0) {
                int doubleDigit = digit * 2 > 9 ? digit * 2 - 9 : digit * 2;
                result += doubleDigit;
                continue;
            }
            result += digit;
        }
        return result % 10 == 0;
    }

}