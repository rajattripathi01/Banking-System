
public class Transaction {
    private final int transactionId;
    private final String transactionType;
    private final String userName;
    private final int accountNumber;
    private final long amount;
    private final long balanceAfterTransaction;
    private static int count = 100;
    private final String timestamp;

    public Transaction(String transactionType, int accountNumber, String userName, long amount, long balanceAfterTransaction, String timestamp) {
        this.transactionId = getTransactionId();
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.timestamp = timestamp;
    }

    public String getTransactionType() {
        return transactionType;
    }

    private int getTransactionId() {
        count++;
        return count;
    }

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
