package database.sqlite;

import account.*;
import account.CreditAccount;
import account.SavingsAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import account.Account;
import database.sqlite.DBConnection;

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
            String values = account.csvString();
            String query = "INSERT INTO accounts (account_id, bank_id, first_name, last_name, email, pin) VALUES " + values + ";";
            DBConnection.runQuery(query);

            if (account instanceof SavingsAccount) {
                addSavingsAccount((SavingsAccount) account);
            } else if (account instanceof CreditAccount) {
                addCreditAccount((CreditAccount) account);
            }
        }
    }

    /**
     * Adds a savings account to the savings_accounts table.
     * @param account The savings account object to add.
     */
    private static void addSavingsAccount(SavingsAccount account) {
//        String values = account.csvString();
        if (!existsInSavings(account.getAccountNumber())) {
            String values = String.format("('%s', %f)", account.getAccountNumber(), account.getBalance());
            String query = "INSERT INTO savings_accounts (account_id, balance) VALUES " + values + ";";
            DBConnection.runQuery(query);
        }
    }

    /**
     * Adds a credit account to the credit_accounts table.
     * @param account The credit account object to add.
     */
    private static void addCreditAccount(CreditAccount account) {
//        String values = account.csvString();
        if (!existsInCredit(account.getAccountNumber())) {
            String values = String.format("('%s', %f)", account.getAccountNumber(), account.getLoan());
            String query = "INSERT INTO credit_accounts (account_id, loan) VALUES " + values + ";";
            DBConnection.runQuery(query);
        }

    }

    /**
     * Fetches an account by its ID.
     * @param accountID The ID of the account to fetch.
     * @return The account object if it exists, otherwise null.
     */
    public static Account fetchAccount(String accountID) {
        if (accountExists(accountID)) {
            if (existsInSavings(accountID)) {
                return fetchSavings();
            } else if (existsInCredit(accountID)) {
                return fetchCredit();
            }
        }
        return null;
    }

    /**
     * Checks if an account exists based on the given query.
     * @param query The SQL query to execute.
     * @return True if the account exists, false otherwise.
     */
    private static boolean exists(String query) {
        ResultSet account = DBConnection.runQuery(query);
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
        String query = "SELECT account_id FROM accounts WHERE account_id = '" + accountID + "';";
        return exists(query);
    }

    /**
     * Checks if an account exists in the savings table.
     * @param accountID The account ID.
     * @return True if it exists in savings, false otherwise.
     */
    public static boolean existsInSavings(String accountID) {
        String query = "SELECT account_id FROM savings_accounts WHERE account_id = '" + accountID + "';";
        return exists(query);
    }

    /**
     * Checks if an account exists in the credit table.
     * @param accountID The account ID.
     * @return True if it exists in credit, false otherwise.
     */
    public static boolean existsInCredit(String accountID) {
        String query = "SELECT account_id FROM credit_accounts WHERE account_id = '" + accountID + "';";
        return exists(query);
    }

    /**
     * Checks if an account exists within a specific bank.
     * @param bankID The bank ID.
     * @param accountID The account ID.
     * @return True if the account exists in the bank, false otherwise.
     */
    public static boolean existsInBank(String bankID, String accountID) {
        String query = "SELECT * FROM accounts WHERE bank_id = '" + bankID + "' AND account_id = '" + accountID + "';";
        return exists(query);
    }

    /**
     * Fetches a credit account and returns it as an Account object.
     * @return The CreditAccount object or null if not found.
     */
    private static Account fetchCredit() {
        String query = "SELECT accounts.*, loan " +
                "FROM accounts " +
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

    /**
     * Fetches a savings account and returns it as an Account object.
     * @return The SavingsAccount object or null if not found.
     */
    private static Account fetchSavings() {
        String query = "SELECT accounts.*, loan " +
                "FROM accounts " +
                "INNER JOIN savings_accounts ON savings_accounts.account_id = accounts.account_id;";
        ResultSet account = DBConnection.runQuery(query);
        try {
            return new SavingsAccount(account.getString("bank_id"), account.getString("account_id"), account.getString("first_name"), account.getString("last_name"), account.getString("email"), account.getString("pin"), account.getDouble("balance"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the balance of a savings account.
     * @param account The BalanceHolder account to update.
     */
    public static void updateAccountBalance(BalanceHolder account) {
        String query = "UPDATE savings_account " +
                "SET balance = " + account.getBalance() + " " +
                "WHERE account_id = '" + ((Account) account).getAccountNumber() + "';";
        DBConnection.runQuery(query);
    }

    /**
     * Updates the loan amount of a credit account.
     * @param account The LoanHolder account to update.
     */
    public static void updateAccountLoan(LoanHolder account) {
        String query = "UPDATE credit_accounts " +
                "SET loan = " + account.getLoan() + " " +
                "WHERE account_id = '" + ((Account) account).getAccountNumber() + "';";
        DBConnection.runQuery(query);
    }
}
