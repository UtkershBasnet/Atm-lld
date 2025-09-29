import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    private String transactionId;
    private TransactionType type;
    private Status status;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;

    public enum TransactionType { DEPOSIT, WITHDRAWAL, BALANCE_INQUIRY, PIN_CHANGE }
    public enum Status { SUCCESS, FAILED, PENDING }

    public Transaction(TransactionType type, double amount, double balanceAfter) {
        this.transactionId = "T" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.status = Status.SUCCESS; // Default to success
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = timestamp.format(dtf);
        
        return String.format("%s | %-18s | Amount: $%-10.2f | Status: %-8s",
                formattedTime, type, amount, status);
    }
}