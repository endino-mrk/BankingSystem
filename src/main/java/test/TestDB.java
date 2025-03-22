package test;

import account.SavingsAccount;
import database.sqlite.DBConnection;
import org.junit.Test;
import services.transaction.TransferService;

import java.sql.SQLException;

public class TestDB {
    @Test
    public void TestDB() throws SQLException, ClassNotFoundException {
        new DBConnection();

        SavingsAccount source = new SavingsAccount("202503B7158", "7158346993", "Source", "Savings", "s@mail.com", "123456", 1000.0);
        SavingsAccount recipient = new SavingsAccount("202503B7158", "7158695037", "Recipient", "Savings", "r@mail.com", "123456", 1000.0);
        TransferService.transfer(source, recipient.getAccountNumber(), 500.0, 0.0);
    }
}
















//
//import account.*;
//import bank.Bank;
//import database.sqlite.AccountDBManager;
//import database.sqlite.BankDBManager;
//import database.sqlite.DBConnection;
//import database.sqlite.TransactionDBManager;
//import org.junit.Test;
//import services.transaction.PaymentService;
//import services.transaction.RecompenseService;
//import services.transaction.Transaction;
//import services.transaction.TransferService;
//
//import java.sql.SQLException;
//
//public class TestDB {
//    @Test
//    public void TestAddAccount() throws SQLException, ClassNotFoundException {
//        new DBConnection();
//        BankDBManager.createTable();
//        AccountDBManager.createTable();
//        Bank bank = new Bank("202503201836B6485", "BDO", "12345678");
//
//        BankDBManager.addBank(bank);
//
////        Account creditAccount = new CreditAccount("202503201836B6485", "12345678", "Michaela", "Endino", "mrk@mail.com", "123456");
////        System.out.println(creditAccount.csvString());
////        AccountDBManager.addAccount(creditAccount);
//
//        Account savingsAccount = new SavingsAccount("202503201836B6485", "23456789", "Phil", "Collins", "phil@mail.com", "12345678", 500.0);
//        AccountDBManager.addAccount(savingsAccount);
//        DBConnection.closeConnection();
//    }
//
//    @Test
//    public void TestAddTransaction() throws SQLException, ClassNotFoundException {
//        new DBConnection();
////        Bank bank = new Bank("202503201836B6485", "BDO", "12345678");
////        BankDBManager.addBank(bank);
//        Account credAccount = new CreditAccount("202503201836B6485", "23456789", "Phil", "Collins", "phil@mail.com", "12345678");
////        AccountDBManager.addAccount(credAccount);
//        Account savingsAccount = new SavingsAccount("202503201836B6485", "12345678", "Phil", "Collins", "phil@mail.com", "12345678", 500.0);
////        AccountDBManager.addAccount(savingsAccount);
//        Account savingsAccount1 = new SavingsAccount("202503201836B6485", "87654321", "James", "Krivchenia", "phil@mail.com", "12345678", 500.0);
////        AccountDBManager.addAccount(savingsAccount1);
//
//
//        PaymentService.pay((LoanHolder) credAccount, (BalanceHolder) savingsAccount, 500.0);
//        RecompenseService.recompense((LoanHolder) credAccount, 250);
////        TransferService.transfer((BalanceHolder) savingsAccount, (BalanceHolder) savingsAccount1, 1500);
////        Transaction t = new Transaction("1107", Transaction.Transactions.Withdraw, "example transaction");
////        TransactionDBManager.addTransaction(t);
//        DBConnection.closeConnection();
//    }
//
//    @Test
//    public void TestClose() {
//        DBConnection.closeConnection();
//    }
//}
//
//
//
