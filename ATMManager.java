import java.util.Scanner;

public class ATMManager {
    private ATM atm;
    private Scanner scanner;

    public ATMManager(ATM atm) {
        this.atm = atm;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Welcome to " + atm.getMachineId() + " ===");
        System.out.print("Please enter your card number: ");
        String cardNumber = scanner.nextLine();

        if (!atm.verifyCard(cardNumber)) {
            System.out.println("Sorry, this card is not valid or could not be read. Exiting.");
            return;
        }

        System.out.print("Please enter your PIN: ");
        String pin = scanner.nextLine();

        if (!atm.authenticatePin(cardNumber, pin)) {
            System.out.println("Incorrect PIN. The transaction has been cancelled for security reasons.");
            return;
        }

        System.out.println("Authentication Successful!");
        showMenu(cardNumber);

        scanner.close();
    }

    private void showMenu(String cardNumber) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1) Check Balance");
            System.out.println("2) Withdraw");
            System.out.println("3) Deposit");
            System.out.println("4) Change PIN");
            System.out.println("5) View Mini Statement");
            System.out.println("6) Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: handleBalanceCheck(cardNumber); break;
                case 2: handleWithdrawal(cardNumber); break;
                case 3: handleDeposit(cardNumber); break;
                case 4: handleChangePin(cardNumber); break;
                case 5: handleStatement(cardNumber); break;
                case 6:
                    System.out.println("Thank you for banking with us. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void handleBalanceCheck(String cardNumber) {
        double balance = atm.fetchBalance(cardNumber);
        System.out.printf("Your available account balance is: $%.2f%n", balance);
    }

    private void handleWithdrawal(String cardNumber) {
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        atm.withdraw(cardNumber, amount);
    }

    private void handleDeposit(String cardNumber) {
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        atm.deposit(cardNumber, amount);
    }

    private void handleChangePin(String cardNumber) {
        System.out.print("Enter your current PIN: ");
        String oldPin = scanner.nextLine();
        System.out.print("Enter your new PIN: ");
        String newPin = scanner.nextLine();
        atm.updatePin(cardNumber, oldPin, newPin);
    }

    private void handleStatement(String cardNumber) {
        atm.printMiniStatement(cardNumber);
    }
}