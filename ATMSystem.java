import java.time.LocalDate;

public class ATMSystem {

    public static void main(String[] args) {
        // 1. Setup the ATM
        ATM atmInstance = new ATM("ATM-BGL-01", 100000.0);

        // 2. Setup Accounts and Cards
        Account acc1 = new Account("00112233", "Alice Johnson", 7500.0, "1111");
        Account acc2 = new Account("00445566", "Bob Williams", 12000.0, "2222");
        ATMCard card1 = new ATMCard("1111222233334444", "00112233", LocalDate.of(2026, 8, 31));
        ATMCard card2 = new ATMCard("5555666677778888", "00445566", LocalDate.of(2025, 5, 31));

        atmInstance.addAccount(acc1, card1);
        atmInstance.addAccount(acc2, card2);

        // 3. Start the user interface manager
        ATMManager manager = new ATMManager(atmInstance);
        manager.start();
    }
}