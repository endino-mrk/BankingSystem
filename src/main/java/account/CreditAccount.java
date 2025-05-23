package account;

import bank.Bank;
import launcher.CreditAccountLauncher;
import account.Account;
import account.LoanHolder;

public class CreditAccount extends Account implements LoanHolder {
    private double loan;

    public CreditAccount(String bankID, String accountnumber, String ownerfname, String ownerlname, String owneremail, String pin) {
        super(bankID, accountnumber, ownerfname, ownerlname, owneremail, pin);
    }

    @Override
    public void setLoan(double amount) {this.loan = amount;}

    @Override
    public double getLoan() {
        return this.loan;
    }

    @Override
    public void init() {
        CreditAccountLauncher.creditAccountInit();
    }
}
