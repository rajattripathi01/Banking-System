import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class handles deposit and withdrawal operations for users.
 * It updates balances, validates inputs, logs transactions, and updates stored account data.
 */
public class DepositAndWithdrawServices {

    /** Reference to BankServices to access user accounts */
    private BankServices bankServices;

    /** File path to store transaction history */
    private static final String FILE_PATH = "src/Transactions/transactions.txt";

    /** File path to account storage file */
    private static final String ACCOUNT_FILE_PATH = "src/Accounts/accounts.txt";

    /** Stores transaction objects in memory during runtime */
    private static final List<Transaction> transactionHistoryList = new ArrayList<>();

    /**
     * Constructor initializes DepositAndWithdrawServices with bank service dependency.
     *
     * @param bankServices the BankServices instance managing accounts
     */
    public DepositAndWithdrawServices(BankServices bankServices) {
        this.bankServices = bankServices;
    }

    /**
     * Prompts user for account number and validates existence.
     * Used before deposit or withdrawal actions.
     *
     * @return entry containing account number and corresponding User object
     * @throws InvalidInputException if account number is invalid or does not exist
     */
    Map.Entry<Integer, User> requestForDepositOrWithdraw() throws InvalidInputException {
        int inputAccountNumberForDeposit = InputHandler.getIntInput("Enter your Account Number: ");

        if (inputAccountNumberForDeposit <= 0) {
            throw new InvalidInputException("Invalid input: Account number must be a positive integer. Please check your account number.");
        }

        User user = bankServices.getUser(inputAccountNumberForDeposit);

        if (user == null) {
            throw new InvalidInputException("Account not found. Please check your account number.");
        }

        return Map.entry(inputAccountNumberForDeposit, user);
    }

    /**
     * Processes amount deposit:
     * - Validates security PIN
     * - Validates minimum deposit amount
     * - Updates user balance
     * - Logs transaction to memory & file
     *
     * @param depositAmount amount to deposit
     * @param enteredSecurityPin user's PIN for authentication
     * @throws InvalidInputException when PIN mismatch or invalid amount
     */
    void amountDepositProcess(long depositAmount, int enteredSecurityPin) throws InvalidInputException {

        Map.Entry<Integer, User> userInfo = requestForDepositOrWithdraw();

        if (enteredSecurityPin == userInfo.getValue().getPin()) {

            if (depositAmount <= 499) {
                throw new InvalidInputException("Invalid input: Deposit amount must be at least ₹500. Please enter a higher amount.");
            } else {

                // Update balance
                userInfo.getValue().setAmount(userInfo.getValue().getAmount() + depositAmount);

                // Save updated user to file
                updateSingleUserInFile(userInfo.getValue());

                // Create transaction record
                Transaction txn = new Transaction(
                        "Deposit", userInfo.getKey(), userInfo.getValue().getName(), depositAmount,
                        userInfo.getValue().getAmount(), getLocalDateAndTime()
                );

                transactionHistoryList.add(txn);
                saveTransaction(txn);

                System.out.println("Amount is successfully deposited: ");
            }

        } else {
            throw new InvalidInputException("Entered wrong security pin: ");
        }

        // Print all deposit transactions so far (for display purposes)
        List<Transaction> withdraw = transactionHistoryList.stream().filter(data -> data.getTransactionType().equalsIgnoreCase("deposit")).toList();
        withdraw.forEach(System.out::println);
    }

    /**
     * Processes withdrawal:
     * - Validates PIN
     * - Checks sufficient balance
     * - Updates balance
     * - Saves transaction
     *
     * @param withdrawAmount amount to withdraw
     * @param EnteredSecurityPin user's PIN for authentication
     * @throws InvalidInputException when invalid PIN or insufficient balance
     */
    void amountTobeWithdraw(long withdrawAmount, int EnteredSecurityPin) throws InvalidInputException {
        Map.Entry<Integer, User> userInfo = requestForDepositOrWithdraw();

        if (EnteredSecurityPin == userInfo.getValue().getPin()) {

            if (withdrawAmount >= userInfo.getValue().getAmount()) {
                throw new InvalidInputException("Insufficient withdraw amount: ");
            } else {

                // Update balance
                userInfo.getValue().setAmount(userInfo.getValue().getAmount() - withdrawAmount);

                // Save updated user record
                updateSingleUserInFile(userInfo.getValue());

                // Create transaction object
                Transaction txn = new Transaction(
                        "Withdraw", userInfo.getKey(), userInfo.getValue().getName(), withdrawAmount,
                        userInfo.getValue().getAmount(), getLocalDateAndTime()
                );

                transactionHistoryList.add(txn);
                saveTransaction(txn);

                System.out.println("Amount is successfully withdrawn: ");
            }

        } else {
            throw new InvalidInputException("Withdraw Failed Re-enter: ");
        }

        // Print only withdraw transactions
        List<Transaction> withdraw = transactionHistoryList.stream().filter(data -> data.getTransactionType().equalsIgnoreCase("withdraw")).toList();
        withdraw.forEach(System.out::println);
    }

    /**
     * Updates the single user's record inside the accounts file.
     * Achieved via temporary file replacement.
     *
     * @param updatedUser the modified User object
     */
    private void updateSingleUserInFile(User updatedUser) {
        File file = new File(ACCOUNT_FILE_PATH);
        File tempFile = new File(ACCOUNT_FILE_PATH + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);

                if (user.getAccountNumber() == updatedUser.getAccountNumber()) {
                    writer.write(updatedUser.toString()); // Write updated record
                } else {
                    writer.write(line); // Keep original record
                }

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
            return;
        }

        // Replace original file with updated file
        if (!file.delete() || !tempFile.renameTo(file)) {
            System.out.println("Error replacing file");
        }
    }

    /**
     * Saves the transaction details to the transaction file.
     *
     * @param transaction the transaction object to log
     */
    public static void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(transaction.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing transaction to file: " + e.getMessage());
        }
    }

    /**
     * Generates formatted current date and time (yyyy-MM-dd HH:mm)
     *
     * @return formatted timestamp string
     */
    static String getLocalDateAndTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }

}
