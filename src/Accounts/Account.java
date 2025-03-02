package Accounts;

import Bank.Bank;
import Transactions.Transaction;

import java.util.ArrayList;

/**
 * An abstract account class that has comparators to compare itself with different account objects.
 */
public abstract class Account {
    // class attributes, create docstring for each

    private final Bank Bank;

    private final String accountNumber;

    private final String ownerFName, ownerLName, ownerEmail;

    private String pin;

    private final ArrayList<Transaction> TRANSACTIONS;

    public Bank getBank() {
        return Bank;
    }

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

    public String getPin() {
        return pin;
    }

    /**
     *
     * @param BANK
     * @param ACCOUNTNUMBER
     * @param OWNERFNAME
     * @param OWNERLNAME
     * @param OWNEREMAIL
     * @param pin
     */
    public Account(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        this.Bank = BANK;
        this.accountNumber = ACCOUNTNUMBER;
        this.ownerFName = OWNERFNAME;
        this.ownerLName = OWNERLNAME;
        this.ownerEmail = OWNEREMAIL;
        this.pin = pin;
        this.TRANSACTIONS = new ArrayList<>();
    }

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS


}
