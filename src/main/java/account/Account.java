package account;

import bank.Bank;

import java.util.ArrayList;
import java.util.HashMap;

import services.transaction.Transaction;

/**
 * An abstract account class that has comparators to compare itself with different account objects.
 */
public abstract class Account {
    private final String bankID;
    private final String accountNumber;
    private final String ownerFName, ownerLName, ownerEmail;
    private String pin;

    /**
     *
     * @param bankID
     * @param accountnumber
     * @param ownerfname
     * @param ownerlname
     * @param owneremail
     * @param pin
     */
    public Account(String bankID, String accountnumber, String ownerfname, String ownerlname, String owneremail, String pin) {
        this.bankID = bankID;
        this.accountNumber = accountnumber;
        this.ownerFName = ownerfname;
        this.ownerLName = ownerlname;
        this.ownerEmail = owneremail;
        this.pin = pin;
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

    public String getOwnerFullName() {
        return ownerFName + " " + ownerLName;
    }

    public String getBankID() {
        return bankID;
    }

    public String getPin() {
        return pin;
    }

    public String toString() {
        return String.format("%s - %s", accountNumber, getOwnerFullName());
    }

    public abstract void init();
}
