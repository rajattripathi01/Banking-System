/**
 * Custom exception class for handling invalid inputs in the banking system.
 */
public class InvalidInputException extends Exception {

    /**
     * Constructs a new InvalidInputException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
