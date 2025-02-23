package bank;

import accounts.Account;

import java.util.ArrayList;

public class Bank {
    private int ID;

    private String name, passcode;

    private final double depositLimit, withdrawLimit, creditLimit;

    private double processingFee;

    private final ArrayList<Account> BANKACCOUNTS;

    /**
     *
     * @param ID
     * @param name
     * @param passcode
     */
    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = 50000;
        this.withdrawLimit = 50000;
        this.creditLimit = 100000;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    /**
     *
     * @param ID
     * @param name
     * @param passcode
     * @param depositLimit
     * @param withdrawLimit
     * @param creditLimit
     */
    public Bank(int ID, String name, String passcode, double depositLimit, double withdrawLimit, double creditLimit) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS
}
