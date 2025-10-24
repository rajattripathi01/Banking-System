import java.io.*;
import java.util.HashMap;

public class BankServices {
    static HashMap<Integer, User> userAccountDetailsMap = new HashMap<>();
    protected static final String ACCOUNT_FILE_PATH = "src/Accounts/accounts.txt";

    public User getUser(int accountNumber) {
        return userAccountDetailsMap.get(accountNumber);
    }


    void createAccount(String userName, int accountNumberForCreation, int securityPinForCreation, long amountForFirstTime) throws InvalidInputException {

        if(!userAccountDetailsMap.containsKey(accountNumberForCreation)){
            User user = new User(userName, accountNumberForCreation, securityPinForCreation, amountForFirstTime);
            userAccountDetailsMap.put(accountNumberForCreation, user);
            saveAccountToFile(user);
        }else {
            throw new InvalidInputException("Account is already exist");
        }
        System.out.println("Account created successfully!");
    }

    void requestAccountCreation() {
        boolean running = true;
        while (running){
            System.out.println("Hello in Bank");
            String name = InputHandler.getStringInput("Enter your name: ");

            int accountNumber = InputHandler.getIntInput("Enter your Account Number: ");

            int securityPin = InputHandler.getIntInput("Enter your pin Number: ");

            long amount = InputHandler.getLongInput("Enter your Amount for you first deposit at least ₹1000: ");

            try {
                InputValidator.validateAccountInputs(name, accountNumber, securityPin, amount);
                if (userAccountDetailsMap.containsKey(accountNumber)) {
                    throw new InvalidInputException("Account already exists with this number.");
                }
                createAccount(name, accountNumber, securityPin, amount);
                running = false;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void saveAccountToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE_PATH, true))) {
            writer.write(user.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving account: " + e.getMessage());
        }
    }

    private static HashMap<Integer, User> readAccountsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                userAccountDetailsMap.put(user.getAccountNumber(), user);
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts: " + e.getMessage());
        }
        return userAccountDetailsMap;
    }

    boolean isUserLogin(int accountNumberForLogin , int securityPinForLogin) throws InvalidInputException {
        User user = readAccountsFromFile().get(accountNumberForLogin);
        if(user != null){
            if(user.getPin() == securityPinForLogin){
                System.out.println("Login Successfully Completed: ");
            }else {
                throw new InvalidInputException("Login failed Please try again: ");
            }
        }else {
            throw new InvalidInputException("Entered wrong Account Number: ");
        }
        return true;
    }
}
