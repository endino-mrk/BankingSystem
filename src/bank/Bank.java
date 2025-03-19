package bank;

import account.Account;
import java.util.HashMap;

public class Bank {
    private String ID;

    private String name, passcode;

    /**
     * depositLimit - The amount of money each Savings Account registered to this bank can deposit at every transaction. Defaults to 50,000.0
     * <p>
     * withdrawLimit - The amount of money withdrawable / transferrable at once, restricted to every Savings Account registered to this bank. Defaults to 50,000.0
     * <p>
     * creditLimit - Limits the amount of credit or loan that all Credit Accounts, registered on this bank, can handle all at once. Defaults to 100,000.0
     */
    private final double depositLimit, withdrawLimit, creditLimit;

    /**
     * Processing fee added when some transaction is involved with another bank. Cannot be lower than 0.0. Defaults to 10.00
     */
    private double processingFee;

    public final HashMap<String, Account> BANKACCOUNTS;

    /**
     * @param ID
     * @param name
     * @param passcode
     */
    public Bank(String ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = 50000;
        this.withdrawLimit = 50000;
        this.creditLimit = 100000;
        this.processingFee = 10;
        this.BANKACCOUNTS = new HashMap<>();
    }

    /**
     * @param ID
     * @param name
     * @param passcode
     * @param depositLimit
     * @param withdrawLimit
     * @param creditLimit
     */
    public Bank(String ID, String name, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.processingFee = processingFee;
        this.BANKACCOUNTS = new HashMap<>();
    }

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

    public String getName() {
        return this.name;
    }

    public String getID() {
        return this.ID;
    }

    public String getPasscode() {
        return passcode;
    }

    public double getDepositLimit() {
        return depositLimit;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public String toString() {
        return String.format("%s - %s", ID, name);
    }

    public String csvString() {
        return "('" + this.ID + "', '" + this.name + "', '" + this.passcode + "', " + this.depositLimit + ", " + this.withdrawLimit + ", " + this.creditLimit + ", " + this.processingFee + ")";
    }
}