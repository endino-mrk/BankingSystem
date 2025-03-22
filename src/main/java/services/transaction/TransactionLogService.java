package services.transaction;

import account.Account;
import database.sqlite.AccountDBManager;
import database.sqlite.TransactionDBManager;
import java.util.ArrayList;

public class TransactionLogService {

    /**
     * Records an account transaction.
     * @param accountNumber
     * @param type
     * @param description
     */
    public static void logTransaction(String accountNumber, Transaction.Transactions type, String description) {
        if (AccountDBManager.accountExists(accountNumber)) {
            TransactionDBManager.addTransaction(accountNumber, type, description);
        }
    }

    /**
     * Displays the transactions of an account, fetches directly from database.
     * @param account
     */
    public static void showTransactions(Account account) {
        // fetches all transactions of account in database
        ArrayList<String> transactions = TransactionDBManager.fetchTransactions(account.getAccountNumber());
        // if there are previous transactions
        if (transactions != null) {
            for (String t : transactions) {
                System.out.println(t);
            }
        }
    }



}
