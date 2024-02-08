package banking.Account;

import java.util.Random;

// Class representing the generation of card numbers
public class CardNumber {

    // Variable to store generated card numbers
    public static String cardNumber;

    // Method to generate a random nine-digit number
    private static StringBuilder generateRandomNineDigits() {
        Random random = new Random();
        StringBuilder generateNum = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            generateNum.append(random.nextInt(9));
        }
        return generateNum;
    }

    // Method to combine the bank's BIN with a generated nine-digit number
    public static String combineWithBIN() {
        return BIN.getBankBin() + generateRandomNineDigits();
    }

    // Method to generate a complete card number
    public static String generateCardNumber() {
        String incompleteCardNumber = combineWithBIN();
        String lastDigit = String.valueOf(LuhnAlgo.calculateLastDigit(incompleteCardNumber));
        cardNumber = incompleteCardNumber + lastDigit;
        return cardNumber;
    }

}