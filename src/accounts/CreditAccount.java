package accounts;

import bank.Bank;
import transactions.Payment;
import transactions.Recompense;

public class CreditAccount extends Account implements Payment, Recompense {
    private double balance;

    /**
     * complete doc string
     * @param BANK
     * @param ACCOUNTNUMBER
     * @param OWNERFNAME
     * @param OWNERLNAME
     * @param OWNEREMAIL
     * @param pin
     * @param balance
     */
    public CreditAccount(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        super(BANK, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

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
