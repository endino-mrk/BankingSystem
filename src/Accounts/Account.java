package accounts;

import bank.Bank;
import transactions.Transaction;

import java.util.ArrayList;

/**
 * An abstract account class that has comparators to compare itself with different account objects.
 */
public abstract class Account {
    // class attributes, create docstring for each

    private final Bank BANK;

    private final String ACCOUNTNUMBER;

    private final String OWNERFNAME, OWNERLNAME, OWNEREMAIL;

    private String pin;

    private final ArrayList<Transaction> TRANSACTIONS;

    /**
     * complete doc string
     * @param BANK
     * @param ACCOUNTNUMBER
     * @param OWNERFNAME
     * @param OWNERLNAME
     * @param OWNEREMAIL
     * @param pin
     */
    public Account(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        this.BANK = BANK;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.OWNERFNAME = OWNERFNAME;
        this.OWNERLNAME = OWNERLNAME;
        this.OWNEREMAIL = OWNEREMAIL;
        this.pin = pin;
        this.TRANSACTIONS = new ArrayList<>();
    }

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

    public String getAccountNumber(){
        return this.ACCOUNTNUMBER;
    }

}
