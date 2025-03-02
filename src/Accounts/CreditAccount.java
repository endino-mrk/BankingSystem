package Accounts;

import Bank.Bank;
import Transactions.Payment;
import Transactions.Recompense;

public class CreditAccount extends Account implements Payment, Recompense {
    private double loan;

    /**
     * complete doc string
     * @param BANK
     * @param ACCOUNTNUMBER
     * @param OWNERFNAME
     * @param OWNERLNAME
     * @param OWNEREMAIL
     * @param pin
     */
    public CreditAccount(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        super(BANK, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
    }

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

    public double getLoan() {
        return loan;
    }

    /**
     *
     * @param amount Amount of money to be recompensed.
     * @return
     */
    @Override
    public boolean recompense(double amount) {
        return false;
    }

    /**
     *
     * @param account Target account to pay money into.
     * @param amount
     * @return
     * @throws IllegalAccountType
     */
    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {
        return false;
    }
}
