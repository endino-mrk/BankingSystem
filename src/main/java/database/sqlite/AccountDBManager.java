package database.sqlite;

import account.*;
import account.CreditAccount;
import account.SavingsAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import account.Account;
import database.sqlite.DBConnection;
import services.transaction.Transaction;

import javax.xml.transform.Result;

public class AccountDBManager {
    /**
     * Creates the accounts, savings, and credit accounts tables if they do not already exist.
     */
    public static void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS accounts (" +
                "account_id TEXT PRIMARY KEY," +
                "bank_id TEXT NOT NULL," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "pin TEXT NOT NULL," +
                "type INT NOT NULL," +
                "FOREIGN KEY (bank_id) REFERENCES banks (bank_id)" +
                ");";
        DBConnection.runQuery(query);
        createSavingsAccTable();
        createCreditAccTable();
    }

    /**
     * Creates the savings accounts table if it does not exist.
     */
    private static void createSavingsAccTable() {
        String query = "CREATE TABLE IF NOT EXISTS savings_accounts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_id TEXT NOT NULL UNIQUE," +
                "balance REAL NOT NULL," +
                "FOREIGN KEY (account_id) REFERENCES accounts (account_id));";
        DBConnection.runQuery(query);
    }

    /**
     * Creates the credit accounts table if it does not exist.
     */
    private static void createCreditAccTable() {
        String query = "CREATE TABLE IF NOT EXISTS credit_accounts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_id TEXT NOT NULL UNIQUE," +
                "loan REAL NOT NULL," +
                "FOREIGN KEY (account_id) REFERENCES accounts (account_id));";
        DBConnection.runQuery(query);
    }

    /**
     * Adds an account to the database. Also adds it to the appropriate account type table.
     * @param account The account object to add.
     */
    public static void addAccount(Account account) {
        if (!accountExists(account.getAccountNumber())) {
            String query = "INSERT INTO accounts (account_id, bank_id, first_name, last_name, email, pin, type) VALUES (?, ?, ?, ?, ?, ?, ?);";

            // gets account type; 1 = Savings, 2 = Credit
            int type = account instanceof SavingsAccount ? 1 : 2;

            DBConnection.runQuery(query, account.getAccountNumber(), account.getBankID(), account.getOwnerFName(), account.getOwnerLName(), account.getOwnerEmail(), account.getPin(), type);

            if (type == 1) {
                addSavingsAccount((SavingsAccount) account);
            } else {
                addCreditAccount((CreditAccount) account);
            }

        }
    }

    /**
     * Adds a savings account to the savings_accounts table.
     * @param account The savings account object to add.
     */
    private static void addSavingsAccount(SavingsAccount account) {
        if (!existsInSavings(account.getAccountNumber())) {
            String query = "INSERT INTO savings_accounts (account_id, balance) VALUES (?, ?);";
            DBConnection.runQuery(query, account.getAccountNumber(), account.getBalance());
        }
    }

    /**
     * Adds a credit account to the credit_accounts table.
     * @param account The credit account object to add.
     */
    private static void addCreditAccount(CreditAccount account) {
        if (!existsInCredit(account.getAccountNumber())) {
            String query = "INSERT INTO credit_accounts (account_id, loan) VALUES (?, ?);";
            DBConnection.runQuery(query, account.getAccountNumber(), account.getLoan());
        }

    }

    /**
     * Fetches an account by its ID.
     * @param accountID The ID of the account to fetch.
     * @return The account object if it exists, otherwise null.
     */
    public static Account fetchAccount(String accountID) {
        Account a = null;
        if (accountExists(accountID)) {
            if (existsInSavings(accountID)) {
                 a = fetchSavings(accountID);
            } else if (existsInCredit(accountID)) {
                a = fetchCredit(accountID);
            } else {
                return a;
            }

            // fetch transactions
//            ArrayList<Transaction> transactions = TransactionDBManager.getTransactions(accountID);
//            if (transactions != null) {
//                for (Transaction t: transactions) {
//                    a.getTransactions().add(t);
//                }
//            }
        }
        return a;
    }

    /**
     * Checks if an account exists based on the given query.
     * @param query The SQL query to execute.
     * @return True if the account exists, false otherwise.
     */
    private static boolean exists(String query, Object... params) {
        ResultSet account = DBConnection.runQuery(query, params);
        try {
            return account.next(); // returns true if account exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if an account exists by ID.
     * @param accountID The account ID.
     * @return True if the account exists, false otherwise.
     */
    public static boolean accountExists(String accountID) {
        String query = "SELECT account_id FROM accounts WHERE account_id = ?;";
        return exists(query, accountID);
    }

    /**
     * Checks if an account exists in the savings table.
     * @param accountID The account ID.
     * @return True if it exists in savings, false otherwise.
     */
    public static boolean existsInSavings(String accountID) {
        String query = "SELECT account_id FROM savings_accounts WHERE account_id = ?;";
        return exists(query, accountID);
    }

    /**
     * Checks if an account exists in the credit table.
     * @param accountID The account ID.
     * @return True if it exists in credit, false otherwise.
     */
    public static boolean existsInCredit(String accountID) {
        String query = "SELECT account_id FROM credit_accounts WHERE account_id = ?;";
        return exists(query, accountID);
    }

    /**
     * Checks if an account exists within a specific bank.
     * @param bankID The bank ID.
     * @param accountID The account ID.
     * @return True if the account exists in the bank, false otherwise.
     */
    public static boolean existsInBank(String bankID, String accountID) {
        String query = "SELECT * FROM accounts WHERE bank_id = ? AND account_id = ?;";
        return exists(query, bankID, accountID);
    }

    /**
     * Fetch account type of account. 1 = Savings, 2 = Credit, null = does not exists
     * @param accountNumber
     * @return
     */
    public static String fetchType(String accountNumber) {
        String type = null;
        if (accountExists(accountNumber)) {
            String query = "SELECT type FROM accounts WHERE account_id = ?;";
            ResultSet rs = DBConnection.runQuery(query, accountNumber);

            try {
                rs.next();
                type = rs.getString("type");
            } catch (SQLException e) {}
        }
        return type;
    }

    /**
     * Fetches a credit account and returns it as an Account object.
     * @return The CreditAccount object or null if not found.
     */
    private static Account fetchCredit(String accountID) {
        String query = "SELECT accounts.*, loan FROM accounts " +
                "INNER JOIN credit_accounts ON credit_accounts.account_id = accounts.account_id " +
                "WHERE accounts.account_id = ?;";
        ResultSet account = DBConnection.runQuery(query, accountID);
        try {
            Account a = new CreditAccount(account.getString("bank_id"), account.getString("account_id"), account.getString("first_name"), account.getString("last_name"), account.getString("email"), account.getString("pin"));
            ((CreditAccount) a).setLoan(account.getDouble("loan"));

            // fetch transactions
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches a savings account and returns it as an Account object.
     * @return The SavingsAccount object or null if not found.
     */
    private static Account fetchSavings(String accountID) {
        String query = "SELECT accounts.*, balance FROM accounts " +
                "INNER JOIN savings_accounts ON savings_accounts.account_id = accounts.account_id " +
                "WHERE accounts.account_id = ?;";
        ResultSet account = DBConnection.runQuery(query, accountID);
        try {
            Account a = new SavingsAccount(account.getString("bank_id"), account.getString("account_id"), account.getString("first_name"), account.getString("last_name"), account.getString("email"), account.getString("pin"), account.getDouble("balance"));
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the balance of a savings account.
     * @param accountNumber The BalanceHolder account to update.
     * @param amount Amount to be changed. Either positive or negative value.
     */
    public static void updateAccountBalance(String accountNumber, double amount) {
        // Fetch account balance of account number
        String query = "SELECT balance FROM savings_accounts WHERE account_id = ?;";
        ResultSet rs = DBConnection.runQuery(query, accountNumber);
        double balance = 0.0;
        try {
            rs.next();
            balance = rs.getDouble("balance");
        } catch(SQLException ex) {

        }

        // Update balance
        balance += amount;

        query = "UPDATE savings_accounts SET balance = ? WHERE account_id = ?;";
        DBConnection.runQuery(query, balance, accountNumber);
    }

    /**
     * Updates the loan amount of a credit account.
     * @param accountNumber The LoanHolder account to update.
     * @param amount The amount to be adjusted. Either positive or negative.
     */
    public static void updateAccountLoan(String accountNumber, double amount) {
        // fetch account initial account loan
        String query = "SELECT loan FROM credit_accounts WHERE account_id = ?";
        ResultSet rs = DBConnection.runQuery(query, accountNumber);
        double loan = 0.0;
        try {
            rs.next();
            loan = rs.getDouble("loan");
        } catch(SQLException ex) {}

        // Update loan
        loan += amount;

        query = "UPDATE credit_accounts SET loan = ? WHERE account_id = ?";
        DBConnection.runQuery(query, loan, accountNumber);
    }
}
