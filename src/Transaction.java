import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single bank transaction (Deposit or Withdraw) with a timestamp and unique ID.
 */
public class Transaction {

    private final int transactionId;
    private final String transactionType;
    private final String userName;
    private final int accountNumber;
    private final long amount;
    private final long balanceAfterTransaction;
    private static int count = 100;
    private final String timestamp;

    /**
     * Constructs a new Transaction object.
     *
     * @param transactionType "Deposit" or "Withdraw"
     * @param accountNumber account number involved
     * @param userName name of the user
     * @param amount transaction amount
     * @param balanceAfterTransaction balance after transaction
     * @param timestamp timestamp of transaction
     */
    public Transaction(String transactionType, int accountNumber, String userName, long amount, long balanceAfterTransaction, String timestamp) {
        this.transactionId = getTransactionId();
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.timestamp = timestamp;
    }

    /**
     * @return type of transaction ("Deposit" or "Withdraw")
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Generates a unique transaction ID.
     *
     * @return transaction ID
     */
    private int getTransactionId() {
        count++;
        return count;
    }

    /**
     * Returns a formatted string describing the transaction.
     *
     * @return formatted transaction details
     */
    @Override
    public String toString() {
        return "\n======================================" +
                "\n        Transaction Details" +
                "\n======================================" +
                "\nTransaction ID        : " + transactionId +
                "\nAccount Number        : " + accountNumber +
                "\nTransaction Type      : " + transactionType +
                "\nAmount                : ₹" + amount +
                "\nBalance After Txn     : ₹" + balanceAfterTransaction +
                "\nDate & Time           : " + timestamp +
                "\n======================================\n";
    }
}
