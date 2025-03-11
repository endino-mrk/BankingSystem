package accounts;

import accounts.transactions.Depositable;
import accounts.transactions.Transferrable;
import accounts.transactions.Withdrawable;
import accounts.transactions.Transaction.Transactions;
import bank.Bank;
import accounts.transactions.TransactionService;


public class SavingsAccount extends Account implements Withdrawable, Depositable, Transferrable {
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

    /*
     * protected sa sya kay para same package lang maka access
     */
    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }
    
    // private boolean hasEnoughBalance(double amount) {
    //     return this.balance - amount >= 0;
    // }

    // private void insufficientBalance() {
    //     System.out.println("Account has insufficient balance.");
    // }

    // private void adjustAccountBalance(double amount) {
    //     this.balance += amount;
    // } 

    
    /**
     *
     * @param amount Amount to be deposited.
     * @return
     */
    @Override
    public boolean cashDeposit(double amount) {
        if (TransactionService.cashDeposit(getBank(), this, amount)) {
            addNewTransaction(getAccountNumber(), Transactions.Deposit, "Deposited to this account");
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param amount Amount of money to be withdrawn from.
     * @return
     */
    @Override
    public boolean withdrawal(double amount) {
        if (TransactionService.withdraw(getBank(), this, amount)) {
            addNewTransaction(getAccountNumber(), Transactions.Withdraw,"Withdrawed from this account");
            return true;
        }
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
       return transfer(account, amount + bank.getProcessingFee());
            
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
        if (TransactionService.transfer(getBank(), this, account, amount)) {
            addNewTransaction(getAccountNumber(), Transactions.FundTransfer, "Transferred from this account");
            return true;
        }
        return false;
    }


}
