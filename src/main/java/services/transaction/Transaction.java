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

        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
    }

    public String toString() {
        return String.format("[%s - %s]  --  %s", transactionType, accountNumber, description);
    }


    public String csvString() {
        return String.format("('%s', '%s', '%s')", this.accountNumber, this.transactionType, this.description);
    }
}