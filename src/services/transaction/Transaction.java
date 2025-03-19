package services.transaction;

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
    private String referenceID;
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
//        this.referenceID = IDGenerator();
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
    }

    public String toString() {
        return String.format("[%s - %s]\n-- %s", transactionType, accountNumber, description);
    }

    public String csvString() {
        return "('" + this.referenceID + "', '" + this.accountNumber + "', '" + this.transactionType + "', '" + this.description + "')";
    }
}