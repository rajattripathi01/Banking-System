import java.util.Scanner;

public class InputHandler {
    private static final Scanner sc = new Scanner(System.in);

    // Get string input (like name)
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

    // Get integer input (like account number or security pin)
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

    // Get long input (like amount)
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
