import java.util.Scanner;

/**
 * Handles user input safely for different data types: String, int, and long.
 * Ensures validation and prompts repeatedly until valid input is provided.
 */
public class InputHandler {

    /** Single Scanner instance for all input operations */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Prompts user to input a non-empty String.
     *
     * @param message the prompt message to display
     * @return the validated non-empty string input
     */
    public static String getStringInput(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Invalid input: This field cannot be empty. Please try again.");
            }
        }
    }

    /**
     * Prompts user to input a positive integer.
     *
     * @param message the prompt message to display
     * @return the validated positive integer
     */
    public static int getIntInput(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Invalid input: Number must be positive. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a valid integer value.");
            }
        }
    }

    /**
     * Prompts user to input a positive long value (for amounts).
     *
     * @param message the prompt message to display
     * @return the validated positive long value
     */
    public static long getLongInput(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                long value = Long.parseLong(input);

                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Invalid input: Amount must be greater than 0. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a valid number.");
            }
        }
    }
}
