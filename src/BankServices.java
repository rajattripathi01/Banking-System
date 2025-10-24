import java.io.*;
import java.util.HashMap;

/**
 * This class provides core banking services such as creating accounts,
 * saving account details, and verifying login credentials.
 * Accounts are stored both in memory (HashMap) and in a text file for persistence.
 */
public class BankServices {

    /** Stores user account data in memory where key = account number, value = User object */
    static HashMap<Integer, User> userAccountDetailsMap = new HashMap<>();

    /** File path used to store account details permanently */
    protected static final String ACCOUNT_FILE_PATH = "src/Accounts/accounts.txt";

    /**
     * Retrieves a user object based on account number.
     *
     * @param accountNumber the account number to look up
     * @return the User object if present, otherwise null
     */
    public User getUser(int accountNumber) {
        return userAccountDetailsMap.get(accountNumber);
    }

    /**
     * Creates a new bank account and saves it to both memory and file.
     *
     * @param userName name of the user
     * @param accountNumberForCreation new account number
     * @param securityPinForCreation security pin for authentication
     * @param amountForFirstTime first deposit amount
     * @throws InvalidInputException if account already exists
     */
    void createAccount(String userName, int accountNumberForCreation, int securityPinForCreation, long amountForFirstTime) throws InvalidInputException {

        // Check if account number already exists
        if(!userAccountDetailsMap.containsKey(accountNumberForCreation)){
            User user = new User(userName, accountNumberForCreation, securityPinForCreation, amountForFirstTime);

            // Save user to memory
            userAccountDetailsMap.put(accountNumberForCreation, user);

            // Save user to persistent storage
            saveAccountToFile(user);

        } else {
            throw new InvalidInputException("Account is already exist");
        }

        System.out.println("Account created successfully!");
    }

    /**
     * Prompts user input to create a new account.
     * Validates input before account creation.
     */
    void requestAccountCreation() {
        boolean running = true;

        while (running){
            System.out.println("Hello in Bank");

            // Get user inputs
            String name = InputHandler.getStringInput("Enter your name: ");
            int accountNumber = InputHandler.getIntInput("Enter your Account Number: ");
            int securityPin = InputHandler.getIntInput("Enter your pin Number: ");
            long amount = InputHandler.getLongInput("Enter your Amount for you first deposit at least ₹1000: ");

            try {
                // Validate inputs
                InputValidator.validateAccountInputs(name, accountNumber, securityPin, amount);

                // Prevent duplicate account creation
                if (userAccountDetailsMap.containsKey(accountNumber)) {
                    throw new InvalidInputException("Account already exists with this number.");
                }

                // Create the account
                createAccount(name, accountNumber, securityPin, amount);
                running = false;

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Writes the user's account details to the accounts file.
     *
     * @param user the User object to save
     */
    private void saveAccountToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE_PATH, true))) {
            writer.write(user.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving account: " + e.getMessage());
        }
    }

    /**
     * Reads all stored accounts from file and loads them into the in-memory HashMap.
     *
     * @return map of accountNumber → User
     */
    private static HashMap<Integer, User> readAccountsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_FILE_PATH))) {
            String line;

            // Read file line-by-line and convert to User objects
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                userAccountDetailsMap.put(user.getAccountNumber(), user);
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts: " + e.getMessage());
        }
        return userAccountDetailsMap;
    }

    /**
     * Validates login by comparing input security PIN with stored PIN.
     *
     * @param accountNumberForLogin the entered account number
     * @param securityPinForLogin the entered security PIN
     * @return true if login successful
     * @throws InvalidInputException if PIN incorrect or account does not exist
     */
    boolean isUserLogin(int accountNumberForLogin , int securityPinForLogin) throws InvalidInputException {

        // Load accounts from file and get requested account
        User user = readAccountsFromFile().get(accountNumberForLogin);

        if(user != null){
            // Verify PIN
            if(user.getPin() == securityPinForLogin){
                System.out.println("Login Successfully Completed: ");
            } else {
                throw new InvalidInputException("Login failed Please try again: ");
            }
        } else {
            throw new InvalidInputException("Entered wrong Account Number: ");
        }

        return true;
    }
}
