package services.transaction;

import account.Account;
import database.sqlite.TransactionDBManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransactionLogService {

    /**
     * Records a transaction to an account.
     * @param account
     * @param type
     * @param description
     */
    public static void logTransaction(Account account, Transaction.Transactions type, String description) {
        Transaction transaction = new Transaction(account.getAccountNumber(), type, description);
        account.getTransactions().add(transaction); // add to account transactions ArrayList
        TransactionDBManager.addTransaction(transaction); // add to database
    }

    /**
     * Displays the transactions of an account.
     * @param account
     */
    public static void showTransactions(Account account) {
//        ArrayList<Transaction> transactions = TransactionDBManager.getTransactions(account.getAccountNumber());
//        if (transactions != null)
        ArrayList<Transaction> transactions = account.getTransactions();
        if (!transactions.isEmpty()) {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }



}
