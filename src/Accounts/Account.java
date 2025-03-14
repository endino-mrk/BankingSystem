package accounts;

import bank.Bank;

import java.util.ArrayList;

import accounts.transactions.Transaction;

/**
 * An abstract account class that has comparators to compare itself with different account objects.
 */
public abstract class Account {
    // class attributes, create docstring for each

    private final Bank Bank;

    private final String accountNumber;

    private final String ownerFName, ownerLName, ownerEmail;

    private String pin;

    private final ArrayList<Transaction> Transactions;

    /**
     *
     * @param bank
     * @param accountnumber
     * @param ownerfname
     * @param ownerlname
     * @param owneremail
     * @param pin
     */
    public Account(Bank bank, String accountnumber, String ownerfname, String ownerlname, String owneremail, String pin) {
        this.Bank = bank;
        this.accountNumber = accountnumber;
        this.ownerFName = ownerfname;
        this.ownerLName = ownerlname;
        this.ownerEmail = owneremail;
        this.pin = pin;
        this.Transactions = new ArrayList<>();
    }

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerFName() {
        return ownerFName;
    }

    public String getOwnerLName() {
        return ownerLName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getOwnerFullName() {
        return ownerFName + ownerLName;
    }

    public Bank getBank() {
        return Bank;
    }

    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {

    }

    public String getTransactionsInfo() {
        return "";
    }
}
