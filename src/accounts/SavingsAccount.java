package accounts;

import bank.Bank;
import transactions.Deposit;
import transactions.FundTransfer;
import transactions.Withdrawal;

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

    /**
     *
     * @param amount Amount to be deposited.
     * @return
     */
    @Override
    public boolean cashDeposit(double amount) {
        return false;
    }

    /**
     *
     * @param bank Bank.Bank ID of the recepient's account.
     * @param account Recipient's account number.
     * @param amount Amount of money to be transferred.
     * @return
     * @throws IllegalAccountType
     */

    /**
     *
     * @param bank Bank.Bank ID of the recepient's account.
     * @param account Recipient's account number.
     * @param amount Amount of money to be transferred.
     * @return
     * @throws IllegalAccountType
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        return false;
    }

    /**
     *
     * @param account Accounts.Account number of the recepient.
     * @param amount Amount of money to be transferred.
     * @return
     * @throws IllegalAccountType
     */
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        return false;
    }

    /**
     *
     * @param amount Amount of money to be withdrawn from.
     * @return
     */
    @Override
    public boolean withdrawal(double amount) {
        return false;
    }
}
