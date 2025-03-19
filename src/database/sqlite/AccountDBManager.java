package database.sqlite;

import account.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDBManager {
    public static void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS accounts (" +
                "account_id TEXT PRIMARY KEY," +
                "bank_id TEXT NOT NULL," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "pin TEXT NOT NULL," +
                "FOREIGN KEY (bank_id) REFERENCES banks (bank_id)" +
                ");";
        DBConnection.runQuery(query);
        createSavingsAccTable();
        createCreditAccTable();
    }

    public static void addAccount(Account account) {
        String values = ((Account) account).csvString();
        String query = "INSERT INTO accounts (account_id, bank_id, first_name, last_name, email, pin) VALUES " + values + ";";
        DBConnection.runQuery(query);

        if (account instanceof SavingsAccount) {
            addSavingsAccount((SavingsAccount) account);
        } else if (account instanceof CreditAccount) {
            addCreditAccount((CreditAccount) account);
        }
    }

    public static void createSavingsAccTable() {
        String query = "CREATE TABLE IF NOT EXISTS savings_accounts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_id TEXT NOT NULL," +
                "balance REAL NOT NULL," +
                "FOREIGN KEY (account_id) REFERENCES accounts (account_id);";
        DBConnection.runQuery(query);
    }

    public static void addSavingsAccount(SavingsAccount account) {
        String values = account.csvString();
        String query = "INSERT INTO savings_accounts (account_id, balance) VALUES " + values + ";";
        DBConnection.runQuery(query);
    }

    public static void createCreditAccTable() {
        String query = "CREATE TABLE IF NOT EXISTS credit_accounts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_id TEXT NOT NULL," +
                "loan REAL NOT NULL," +
                "FOREIGN KEY (account_id) REFERENCES accounts (account_id);";
        DBConnection.runQuery(query);
    }

    public static void addCreditAccount(CreditAccount account) {
        String values = account.csvString();
        String query = "INSERT INTO credit_accounts (account_id, loan) VALUES " + values + ";";
        DBConnection.runQuery(query);
    }

    public Account fetchAccount(String accountID) {
        if (accountExists(accountID)) {
            if (existsInSavings(accountID)) {
                return fetchSavings();
            } else if (existsInCredit(accountID)) {
                return fetchCredit();
            }
        }
        return null;
    }

    public static boolean accountExists(String accountID) {
        String query = "SELECT account_id FROM accounts WHERE account_id = " + accountID + ";";
        ResultSet account = DBConnection.runQuery(query);
        try {
            return account.next(); // returns true if account exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existsInSavings(String accountID) {
        String query = "SELECT account_id FROM savings_accounts WHERE account_id = " + accountID + ";";
        ResultSet account = DBConnection.runQuery(query);
        try {
            return account.next(); // returns true if account exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existsInCredit(String accountID) {
        String query = "SELECT account_id FROM credit_accounts WHERE account_id = " + accountID + ";";
        ResultSet account = DBConnection.runQuery(query);
        try {
            return account.next(); // returns true if account exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Account fetchCredit() {
        String query = "SELECT accounts.*, loan" +
                "FROM accounts" +
                "INNER JOIN credit_accounts ON credit_accounts.account_id = accounts.account_id;";
        ResultSet account = DBConnection.runQuery(query);
        try {
            Account a = new CreditAccount(account.getString("bank_id"), account.getString("account_id"), account.getString("first_name"), account.getString("last_name"), account.getString("email"), account.getString("pin"));
            ((CreditAccount) a).setLoan(account.getDouble("loan"));
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Account fetchSavings() {
        String query = "SELECT accounts.*, loan" +
                "FROM accounts" +
                "INNER JOIN savings_accounts ON savings_accounts.account_id = accounts.account_id;";
        ResultSet account = DBConnection.runQuery(query);
        try {
            return new SavingsAccount(account.getString("bank_id"), account.getString("account_id"), account.getString("first_name"), account.getString("last_name"), account.getString("email"), account.getString("pin"), account.getDouble("balance"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateAccountBalance(BalanceHolder account) {
        String query = "UPDATE savings_account" +
                "SET balance = " + account.getBalance() +
                "WHERE account_id = " + ((Account) account).getAccountNumber();
        DBConnection.runQuery(query);
    }

    public static void updateAccountLoan(LoanHolder account) {
        String query = "UPDATE credit_accounts" +
                "SET loan = " + account.getLoan() +
                "WHERE account_id = " + ((Account) account).getAccountNumber();
        DBConnection.runQuery(query);
    }
}
