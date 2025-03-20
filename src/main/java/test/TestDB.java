package test;

import account.Account;
import account.CreditAccount;
import account.SavingsAccount;
import bank.Bank;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;
import database.sqlite.DBConnection;
import org.junit.Test;

import java.sql.SQLException;

public class TestDB {
    @Test
    public void TestAddAccount() throws SQLException, ClassNotFoundException {
        new DBConnection();
        BankDBManager.createTable();
        AccountDBManager.createTable();
        Bank bank = new Bank("202503201836B6485", "BDO", "12345678");

        BankDBManager.addBank(bank);

//        Account creditAccount = new CreditAccount("202503201836B6485", "12345678", "Michaela", "Endino", "mrk@mail.com", "123456");
//        System.out.println(creditAccount.csvString());
//        AccountDBManager.addAccount(creditAccount);

        Account savingsAccount = new SavingsAccount("202503201836B6485", "23456789", "Phil", "Collins", "phil@mail.com", "12345678", 500.0);
        AccountDBManager.addAccount(savingsAccount);
    }
}



