package banking.Account;

// Class representing the Luhn algorithm for card number validation
public class LuhnAlgo {
    static int checksum;

    // Method to calculate the last digit of a card number using the Luhn algorithm
    public static int calculateLastDigit(String incompleteCardNumber) {
        int[] calculatedNumArray = new int[15];
        int sumDigits = 0;

        // Iterate over each digit of the incomplete card number
        for (int i = 0; i < incompleteCardNumber.length(); i++) {
            calculatedNumArray[i] = Integer.parseInt(incompleteCardNumber.split("")[i]);

            // Apply Luhn algorithm rules
            if (i % 2 == 0) {
                if (calculatedNumArray[i] * 2 <= 9) {
                    calculatedNumArray[i] = calculatedNumArray[i] * 2;
                } else {
                    calculatedNumArray[i] = calculatedNumArray[i] * 2 - 9;
                }
            } else {
                if (calculatedNumArray[i] > 9) {
                    calculatedNumArray[i] = calculatedNumArray[i] - 9;
                }
            }
            sumDigits += calculatedNumArray[i];
        }

        // Calculate the checksum
        checksum = (sumDigits % 10 != 0) ? (10 - sumDigits % 10) : 0;
        return checksum;
    }

}