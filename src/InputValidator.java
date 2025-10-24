/**
 * Validates input data for creating bank accounts.
 * Throws InvalidInputException if any input is invalid.
 */
public class InputValidator {

    /**
     * Validates user account inputs.
     *
     * @param userName the name of the user
     * @param accountNumber the account number
     * @param securityPin the security PIN
     * @param amount initial deposit amount
     * @throws InvalidInputException if any validation rule is violated
     */
    static void validateAccountInputs(String userName, int accountNumber, int securityPin, long amount) throws InvalidInputException {

        if (userName == null || userName.isEmpty()) {
            throw new InvalidInputException("Invalid input: Name cannot be empty. Please enter a valid name.");
        }

        if (accountNumber <= 0) {
            throw new InvalidInputException("Invalid input: Account number must be a positive integer. Please check your account number.");
        }

        if (securityPin <= 0) {
            throw new InvalidInputException("Invalid input: Security PIN must be a positive number. Please enter a valid PIN.");
        }

        if (amount <= 999) {
            throw new InvalidInputException("Invalid input: Deposit amount must be at least ₹1000. Please enter a higher amount.");
        }
    }
}
