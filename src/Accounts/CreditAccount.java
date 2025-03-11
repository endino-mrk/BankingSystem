package accounts;

import accounts.transactions.Compensable;
import accounts.transactions.Payable;
import accounts.transactions.TransactionService;
import accounts.transactions.Transaction.Transactions;
import bank.Bank;

public class CreditAccount extends Account implements Payable, Compensable {
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

    @Override
    public void setLoan(double loan) {
        this.loan = loan;
    }

    @Override
    public double getLoan() {
        return this.loan;
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
        if (!(account instanceof BalanceHolder)) {
            throw new IllegalAccountType("Cannot pay to account.");
        }

        if (TransactionService.pay(getBank(), this, (BalanceHolder) account, amount)) {
            addNewTransaction(getAccountNumber(), Transactions.Payment, "Paid to account");
            return true;
        }
        return false;
    }

    /**
     *
     * @param amount Amount of money to be recompensed.
     * @return
     */
    @Override
    public boolean recompense(double amount) {
        if (TransactionService.recompense(this, amount)) {
            addNewTransaction(getAccountNumber(), Transactions.Recompense, "Recompensed loan");
            return true;
        }
        return false;
    }
}
