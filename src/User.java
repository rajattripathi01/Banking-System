public class User {
    private final String name;
    private final int accountNumber;
    private final int pin;
    private long amount;


    public User(String name, int accountNumber, int pin, long amount) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.amount = amount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: "+name + ","+" Account-Number: " + accountNumber + ","+" Security-Pin: " + pin + ","+" Amount: " + amount;
    }


    public static User fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) throw new IllegalArgumentException("Invalid data line: " + line);

        String name = parts[0].split(":")[1].trim();
        int accountNumber = Integer.parseInt(parts[1].split(":")[1].trim());
        int pin = Integer.parseInt(parts[2].split(":")[1].trim());
        long amount = Long.parseLong(parts[3].split(":")[1].trim());

        return new User(name, accountNumber, pin, amount);
    }




}
