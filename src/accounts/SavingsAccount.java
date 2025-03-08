package accounts;

import bank.Bank;
import accounts.transactions.Deposit;
import accounts.transactions.FundTransfer;
import accounts.transactions.Withdrawal;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {

    private double balance;

    /**
     *
     * @param BANK
     * @param ACCOUNTNUMBER
     * @param OWNERFNAME
     * @param OWNERLNAME
     * @param OWNEREMAIL
     * @param pin
     * @param balance
     */
    public SavingsAccount(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        super(BANK, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }


    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

    public double getBalance() {
        return balance;
    }

    /**
     * Validates whether this savings account has enough balance to proceed with such a transaction based on the amount that is to be adjusted.
     *
     * @param amount Amount of money to be supposedly adjusted from this account’s balance.
     * @return Flag if transaction can proceed by adjusting the account balance by the amount to be
     *  changed.
     */
    private boolean hasEnoughBalance(double amount) {
        return false;
    }

    /**
     * Warns the account owner that their balance is not enough for the transaction to proceed successfully.
     */
    private void insufficientBalance() {

    }

    /**
     * Adjust the account balance of this savings account based on the amount to be adjusted. If it results to the account balance going less than 0.0, then it is forcibly reset to 0.0.
     * @param amount Amount to be added or subtracted from the account balance.
     */
    private void adjustAccountBalance(double amount) {

    }

    @Override
    public boolean cashDeposit(double amount) {
        return false;
    }




    @Override
    public boolean withdrawal(double amount) {
//        if (hasEnoughBalance(amount)) {
//            adjustAccountBalance(amount);
//            return true;
//        }
//        insufficientBalance();
        return false;
    }

    public String toString() {
        return "";
    }

    @Override
    public boolean transfer(bank.Bank bank, Account account, double amount) throws IllegalAccountType {
        return false;
    }
}
