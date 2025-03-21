package services.transaction;

import account.Account;
import services.IDGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class containing Transaction enums.
 */
public class Transaction {

    public enum Transactions {
        Deposit,
        Withdraw,
        FundTransfer,
        Payment,
        Recompense
    }
//    private String timestamp;

//    private String referenceID;
    /**
     * Account number that triggered this transaction.
     */
    public String accountNumber;
    /**
     * Type of transaction that was triggered.
     */
    public Transactions transactionType;
    /**
     * Description of the transaction.
     */
    public String description;

    public Transaction(String accountNumber, Transactions transactionType, String description) {
//        this.timestamp = getTimeStamp();
//        this.referenceID = IDGenerator.transactionIDGenerator(accountNumber);
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
    }

//    private String getTimeStamp() {
//        LocalDateTime timestamp = LocalDateTime.now(); // Get current date and time
//
//        // Format the timestamp
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        return timestamp.format(formatter); // String representation of timestamp in specified format
//    }

    public String toString() {
        return String.format("[%s - %s]  --  %s", transactionType, accountNumber, description);
    }

//    public String toString() {
//        return String.format("Reference No. %s | %s \n[%s]: %s", this.referenceID, this.accountNumber, this.transactionType, this.description);
//    }

    public String csvString() {
        return String.format("('%s', '%s', '%s')", this.accountNumber, this.transactionType, this.description);
    }
}