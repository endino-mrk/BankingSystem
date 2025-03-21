package database.sqlite;

import services.transaction.Transaction;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static database.sqlite.BankDBManager.isEmpty;

public class TransactionDBManager {
    public static void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_id TEXT NOT NULL," +
                "type TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "FOREIGN KEY (account_id) REFERENCES accounts (account_id));";
        DBConnection.runQuery(query);
    }

    public static void addTransaction(Transaction transaction) {
        String values = transaction.csvString();
        String query = "INSERT INTO transactions (account_id, type, description) VALUES " + values + ";";
        DBConnection.runQuery(query);
    }

    public static ArrayList<Transaction> getTransactions(String accountID) {
        String query = "SELECT * FROM transactions WHERE account_id = '" + accountID + "';";
        ResultSet transactionRS = DBConnection.runQuery(query);
        if (!isEmpty(transactionRS)) {
            ArrayList<Transaction> transactions = new ArrayList<>();

            try {
                while (transactionRS.next()) {
                    String accountId= transactionRS.getString("account_id");
                    String type = transactionRS.getString("type");
                    String description = transactionRS.getString("description");
                    transactions.add(new Transaction(accountID, Transaction.Transactions.valueOf(type), description));
                }
                return transactions;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
