import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private String accountNumber;
    private String customerName;
    private double balance;
    private String pin;
    private List<Transaction> transactionLog;

    public Account(String accountNumber, String customerName, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.balance = initialBalance;
        this.pin = pin;
        this.transactionLog = new ArrayList<>();
    }

    public boolean verifyPin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void recordTransaction(Transaction transaction) {
        transactionLog.add(transaction);
    }

    public List<Transaction> getMiniStatement() {
        // Return the last 5 transactions
        int logSize = transactionLog.size();
        if (logSize <= 5) {
            return Collections.unmodifiableList(transactionLog);
        }
        return Collections.unmodifiableList(transactionLog.subList(logSize - 5, logSize));
    }

    // Standard Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public String getCustomerName() { return customerName; }
    public double getBalance() { return balance; }
    public void setPin(String newPin) { this.pin = newPin; }
}