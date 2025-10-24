/**
 * Entry point of SmartBank application.
 * Handles user menu, login, account creation, deposits, and withdrawals.
 */
public class Main {
    public static void main(String[] args) {

        BankServices bankServices = new BankServices();
        DepositAndWithdrawServices depositAndWithdrawServices = new DepositAndWithdrawServices(bankServices);

        System.out.println(" Welcome to SmartBank!");
        boolean isLoggedIn = false;
        int loginCount = 1;

        // Initial menu for new or existing user
        System.out.println("1. If You are new: Create Your Account : ");
        System.out.println("2. Login: ");
        int choiceForEntering = InputHandler.getIntInput("Please choose an option: ");

        switch (choiceForEntering) {
            case 1:
                bankServices.requestAccountCreation();
                isLoggedIn = true;
                break;
            case 2:
                // Attempt login up to 3 times
                while (loginCount < 4) {
                    int loginPin = InputHandler.getIntInput("Enter your PIN: ");
                    int loginAccountNumber = InputHandler.getIntInput("Enter your Account Number: ");
                    try {
                        isLoggedIn = bankServices.isUserLogin(loginAccountNumber, loginPin);
                        break;
                    } catch (InvalidInputException e) {
                        loginCount++;
                        System.out.println("Error: " + e.getMessage());
                    }
                }
        }

        boolean exit = false;

        // Main banking menu after login
        if (isLoggedIn) {
            while (!exit) {
                System.out.println("\n Please choose an option:");
                System.out.println("1. Deposit Amount");
                System.out.println("2. Withdraw Amount");
                System.out.println("3. Exit");

                int choice = InputHandler.getIntInput("Enter your choice (1-3): ");

                switch (choice) {
                    case 1:
                        long depositAmount = InputHandler.getLongInput("Enter your Amount (min ₹500) for deposit: ");
                        int depositPin = InputHandler.getIntInput("Enter your PIN: ");
                        try {
                            depositAndWithdrawServices.amountDepositProcess(depositAmount, depositPin);
                            System.out.println(" Deposit successful!");
                        } catch (InvalidInputException e) {
                            System.out.println(" Error: " + e.getMessage());
                        }
                        break;

                    case 2:
                        int errorCount = 0;
                        while (errorCount < 3) {
                            long withdrawAmount = InputHandler.getLongInput("Enter your Amount for withdrawal: ");
                            int withdrawPin = InputHandler.getIntInput("Enter your PIN for withdrawal: ");
                            try {
                                depositAndWithdrawServices.amountTobeWithdraw(withdrawAmount, withdrawPin);
                                System.out.println(" Withdrawal successful!");
                                break;
                            } catch (InvalidInputException e) {
                                errorCount++;
                                System.out.println(" Error: " + e.getMessage());
                                if (errorCount == 3) {
                                    System.out.println(" Your account is locked after 3 failed attempts.");
                                }
                            }
                        }
                        break;

                    case 3:
                        System.out.println(" Thank you for using SmartBank. Goodbye!");
                        exit = true;
                        break;

                    default:
                        System.out.println(" Invalid choice. Please select 1, 2, or 3.");
                }
            }
        } else {
            System.out.println("First Login Please: ");
        }
    }
}
