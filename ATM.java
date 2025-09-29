import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ATM {
    private String machineId;
    private double machineBalance;
    private Map<String, Account> accountDatabase;
    private Map<String, ATMCard> cardDatabase;
    private int dailyWithdrawalLimit;
    private int pinRetryCount;
    private static final int MAX_PIN_RETRIES = 3;

    public ATM(String machineId, double initialCash) {
        this.machineId = machineId;
        this.machineBalance = initialCash;
        this.accountDatabase = new HashMap<>();
        this.cardDatabase = new HashMap<>();
        this.dailyWithdrawalLimit = 20000;
        this.pinRetryCount = 0;
    }

    public void addAccount(Account account, ATMCard card) {
        accountDatabase.put(account.getAccountNumber(), account);
        cardDatabase.put(card.getCardNumber(), card);
    }

    public boolean verifyCard(String cardNumber) {
        ATMCard card = cardDatabase.get(cardNumber);
        if (card == null || !card.isActive()) {
            System.out.println("Card Error: Card is blocked, expired, or does not exist.");
            return false;
        }
        return true;
    }

    public boolean authenticatePin(String cardNumber, String pin) {
        ATMCard card = cardDatabase.get(cardNumber);
        Account account = accountDatabase.get(card.getAccountNumber());

        if (account.verifyPin(pin)) {
            pinRetryCount = 0;
            return true;
        } else {
            pinRetryCount++;
            if (pinRetryCount >= MAX_PIN_RETRIES) {
                card.setBlocked(true);
                System.out.println("Security Alert: Card has been blocked due to too many failed PIN attempts.");
            }
            return false;
        }
    }

    public double fetchBalance(String cardNumber) {
        Account account = getAccountFromCard(cardNumber);
        if (account == null) return -1;
        
        account.recordTransaction(new Transaction(Transaction.TransactionType.BALANCE_INQUIRY, 0, account.getBalance()));
        return account.getBalance();
    }

    public void withdraw(String cardNumber, double amount) {
        if (amount > dailyWithdrawalLimit || amount > machineBalance) {
            System.out.println("Transaction Failed: Amount exceeds machine limit or available cash.");
            return;
        }

        Account account = getAccountFromCard(cardNumber);
        if (account != null && account.withdraw(amount)) {
            machineBalance -= amount;
            account.recordTransaction(new Transaction(Transaction.TransactionType.WITHDRAWAL, amount, account.getBalance()));
            System.out.println("Withdrawal successful. Please take your cash.");
        } else {
            System.out.println("Transaction Failed: Insufficient funds in your account.");
        }
    }

    public void deposit(String cardNumber, double amount) {
        Account account = getAccountFromCard(cardNumber);
        if (account != null && amount > 0) {
            account.deposit(amount);
            machineBalance += amount;
            account.recordTransaction(new Transaction(Transaction.TransactionType.DEPOSIT, amount, account.getBalance()));
            System.out.println("Deposit successful. Your new balance is available.");
        } else {
            System.out.println("Transaction Failed: Invalid deposit amount.");
        }
    }

    public void updatePin(String cardNumber, String oldPin, String newPin) {
        Account account = getAccountFromCard(cardNumber);
        if (account != null && account.verifyPin(oldPin)) {
            account.setPin(newPin);
            account.recordTransaction(new Transaction(Transaction.TransactionType.PIN_CHANGE, 0, account.getBalance()));
            System.out.println("PIN has been updated successfully.");
        } else {
            System.out.println("PIN update failed. The current PIN provided was incorrect.");
        }
    }

    public void printMiniStatement(String cardNumber) {
        Account account = getAccountFromCard(cardNumber);
        if (account != null) {
            System.out.println("\n--- Mini Statement for Account: " + account.getAccountNumber() + " ---");
            for (Transaction tx : account.getMiniStatement()) {
                System.out.println(tx);
            }
            System.out.println("------------------------------------------");
        }
    }
    
    private Account getAccountFromCard(String cardNumber) {
        ATMCard card = cardDatabase.get(cardNumber);
        return card != null ? accountDatabase.get(card.getAccountNumber()) : null;
    }

    public String getMachineId() {
        return machineId;
    }
}