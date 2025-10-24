import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DepositAndWithdrawServices {
    private BankServices bankServices;
    private static final String FILE_PATH = "src/Transactions/transactions.txt";
    private static final String ACCOUNT_FILE_PATH = "src/Accounts/accounts.txt";
    private static final List<Transaction> transactionHistoryList = new ArrayList<>();

    public DepositAndWithdrawServices(BankServices bankServices) {
        this.bankServices = bankServices;
    }


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

    void amountDepositProcess(long depositAmount, int enteredSecurityPin) throws InvalidInputException {
        Map.Entry<Integer, User> userInfo = requestForDepositOrWithdraw();
        if (enteredSecurityPin == userInfo.getValue().getPin()) {
            if (depositAmount <= 499) {
                throw new InvalidInputException("Invalid input: Deposit amount must be at least ₹500. Please enter a higher amount.");
            } else {
                userInfo.getValue().setAmount(userInfo.getValue().getAmount() + depositAmount);
                updateSingleUserInFile(userInfo.getValue());
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
        List<Transaction> withdraw = transactionHistoryList.stream().filter(data -> data.getTransactionType().equalsIgnoreCase("deposit")).toList();
        withdraw.forEach(System.out::println);

    }

    void amountTobeWithdraw(long withdrawAmount, int EnteredSecurityPin) throws InvalidInputException {
        Map.Entry<Integer, User> userInfo = requestForDepositOrWithdraw();
        if (EnteredSecurityPin == userInfo.getValue().getPin()) {
            if (withdrawAmount >= userInfo.getValue().getAmount()) {
                throw new InvalidInputException("Insufficient withdraw amount: ");
            } else {
                userInfo.getValue().setAmount(userInfo.getValue().getAmount() - withdrawAmount);
                updateSingleUserInFile(userInfo.getValue());
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
        List<Transaction> withdraw = transactionHistoryList.stream().filter(data -> data.getTransactionType().equalsIgnoreCase("withdraw")).toList();
        withdraw.forEach(System.out::println);

    }

    private void updateSingleUserInFile(User updatedUser) {
        File file = new File(ACCOUNT_FILE_PATH);
        File tempFile = new File(ACCOUNT_FILE_PATH + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user.getAccountNumber() == updatedUser.getAccountNumber()) {
                    writer.write(updatedUser.toString());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
            return;
        }

        if (!file.delete() || !tempFile.renameTo(file)) {
            System.out.println("Error replacing file");
        }
    }


    public static void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(transaction.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing transaction to file: " + e.getMessage());
        }
    }


    static String getLocalDateAndTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }

}
