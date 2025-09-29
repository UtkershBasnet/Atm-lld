import java.time.LocalDate;

public class ATMCard {
    private String cardNumber;
    private String accountNumber;
    private LocalDate expiryDate;
    private boolean isBlocked;

    public ATMCard(String cardNumber, String accountNumber, LocalDate expiryDate) {
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.expiryDate = expiryDate;
        this.isBlocked = false;
    }

    public boolean isActive() {
        return !isBlocked && !isExpired();
    }

    private boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    // Getters and Setters
    public String getCardNumber() { return cardNumber; }
    public String getAccountNumber() { return accountNumber; }
    public boolean isBlocked() { return isBlocked; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }
}