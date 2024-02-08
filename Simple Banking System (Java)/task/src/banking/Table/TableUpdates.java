package banking.Table;

import banking.DataBase.DBConnection;
import banking.ShowMenu.AccountMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The TableUpdates class manages database operations related to account management and transactions.
 * It includes methods for adding accounts, adding income, making transfers, deleting accounts, and authenticating users.
 */
public class TableUpdates {

    /** Stores the current card number for session management. */
    public static String currentCardNumber;

    /**
     * Adds a new account to the database.
     *
     * @param number The card number of the new account.
     * @param pin    The PIN associated with the new account.
     */
    public static void addAccountToDB(String number, String pin) {
        String sqlInsertCard = "INSERT INTO card (number, pin) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertCard)) {
            preparedStatement.setString(1, number);
            preparedStatement.setString(2, pin);
            preparedStatement.executeUpdate();
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds income to the balance of a specific account.
     *
     * @param currentCardNumber The card number of the account.
     * @param incomeValue       The amount of income to be added.
     */
    public static void addIncomeToBalance(String currentCardNumber, int incomeValue) {
        String sqlAddIncome = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAddIncome)) {
            preparedStatement.setInt(1, incomeValue);
            preparedStatement.setString(2, currentCardNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Income was added!");
            } else {
                System.out.println("Account not found.");
            }
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs a money transfer between two accounts.
     *
     * @param currentCardNumber    The card number of the sender's account.
     * @param cardNumberForTransfer The card number of the recipient's account.
     * @param transferAmount       The amount of money to transfer.
     * @return 0 if the transfer is successful, otherwise -1.
     */
    public static int makeTransfer(String currentCardNumber, String cardNumberForTransfer, int transferAmount) {
        String sqlWithdraw = "UPDATE card SET balance = balance - ? WHERE number = ?";
        String sqlAdd = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try (Connection connection = DBConnection.getConnection()) {
            try (PreparedStatement withdrawPreparedStatement = connection.prepareStatement(sqlWithdraw);
                 PreparedStatement addPreparedStatement = connection.prepareStatement(sqlAdd)) {
                if (connection.getAutoCommit()) {
                    connection.setAutoCommit(false);
                }
                withdrawPreparedStatement.setInt(1, transferAmount);
                withdrawPreparedStatement.setString(2, currentCardNumber);
                withdrawPreparedStatement.executeUpdate();
                addPreparedStatement.setInt(1, transferAmount);
                addPreparedStatement.setString(2, cardNumberForTransfer);
                addPreparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                return -1;
            }
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    /**
     * Deletes an account from the database.
     */
    public static void deleteAccount() {
        String sqlFDeleteAccount = "DELETE FROM card WHERE number = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFDeleteAccount)) {
            preparedStatement.setString(1, currentCardNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The account has been closed!");
            } else {
                System.out.println("Account not found.");
            }
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticates the provided card number and PIN.
     *
     * @param cardInput The card number provided by the user.
     * @param pinInput  The PIN provided by the user.
     * @return true if the authentication is successful, false otherwise.
     */
    public static boolean authenticateCardAndPin(String cardInput, String pinInput) {
        String sqlAuthenticateUser = "SELECT * FROM card WHERE number = ? AND pin = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlAuthenticateUser)) {
            statement.setString(1, cardInput);
            statement.setString(2, pinInput);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    currentCardNumber = cardInput;
                    DBConnection.closeConnection();
                    System.out.println("\nYou have successfully logged in!");
                    return AccountMenu.accountMenu();
                } else {
                    DBConnection.closeConnection();
                    System.out.println("\nWrong card number or PIN!");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the balance of the specified account.
     *
     * @param currentCardNumber The card number of the account.
     * @return The balance of the account.
     */
    public static int getBalance(String currentCardNumber) {
        String sqlRetrieveBalance = "SELECT balance FROM card WHERE number = ?";
        int balance = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRetrieveBalance)) {
            statement.setString(1, currentCardNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    balance = resultSet.getInt("balance");
                } else {
                    System.out.println("\nAccount not found");
                }
            }
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * Checks if a card number exists in the database.
     *
     * @param cardNumberForTransfer The card number to check.
     * @return true if the card number exists, false otherwise.
     */
    public static boolean isCardNumberExist(String cardNumberForTransfer) {
        String sql = "SELECT * FROM card WHERE number = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cardNumberForTransfer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    DBConnection.closeConnection();
                    return true;
                } else {
                    DBConnection.closeConnection();
                    System.out.println("Such a card does not exist.");
                    AccountMenu.accountMenu();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}