package account;

import bank.Bank;
import launcher.StudentAccountLauncher;

public class StudentAccount extends Account implements BalanceHolder{
    private double balance;

    public StudentAccount(String bankID, String accountnumber, String ownerfname, String ownerlname, String owneremail, String pin, double balance) {
        super(bankID, accountnumber, ownerfname, ownerlname, owneremail, pin);
        this.balance = balance;
    }

    @Override
    public void setBalance(double amount) {this.balance = amount;}

    @Override
    public double getBalance() {return this.balance;}
    
    @Override
    public void init() {
        StudentAccountLauncher.studentAccountInit();
    }
}
