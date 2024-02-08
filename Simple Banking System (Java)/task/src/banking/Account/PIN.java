package banking.Account;

import java.util.Random;

// Class representing the generation of PIN (Personal Identification Number)
public class PIN {

    // Method to generate a random 4-digit PIN
    public static StringBuilder generatePIN() {
        Random random = new Random();
        final StringBuilder pin = new StringBuilder();

        // Generate each digit of the PIN randomly
        for (int i = 0; i < 4; i++) {
            pin.append(random.nextInt(9));
        }
        return pin;
    }

}