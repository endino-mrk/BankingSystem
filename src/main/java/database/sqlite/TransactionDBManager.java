package database.sqlite;

import org.sqlite.core.DB;
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

    public static void addTransaction(String accountID, Transaction.Transactions type, String description) {
//        String values = transaction.csvString();
//        String query = "INSERT INTO transactions (account_id, type, description) VALUES " + values + ";";
//        DBConnection.runQuery(query);

        String query = "INSERT INTO transactions (account_id, type, description) VALUES (?, ?, ?);";
        DBConnection.runQuery(query, accountID, type.toString(), description);

    }

    public static ArrayList<String> fetchTransactions(String accountID) {
        String query = "SELECT * FROM transactions WHERE account_id = ?;";
        ResultSet rs = DBConnection.runQuery(query, accountID);
        if (!isEmpty(rs)) {
            ArrayList<String> transactions = new ArrayList<>();
            try {
                while(rs.next()) {
                    // add transactions from result set to transactions ArrayList
                    transactions.add(String.format("[Acc. No. %s] - [%s] -- %s", rs.getString("account_id"), rs.getString("type"), rs.getString("description")));
                }
                return transactions;
            } catch (SQLException e) {}
        }
        return null;
    }
}
