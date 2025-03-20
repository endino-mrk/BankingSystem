package database.sqlite;

import bank.Bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BankDBManager {
    /**
     * Creates the banks table if it does not already exist.
     */
    public static void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS banks (" +
                "bank_id TEXT PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "passcode TEXT NOT NULL," +
                "depositLimit REAL NOT NULL," +
                "creditLimit REAL NOT NULL," +
                "withdrawLimit REAL NOT NULL," +
                "processingFee REAL NOT NULL);";
        DBConnection.runQuery(query);
    }

    /**
     * Adds a bank to the database.
     * @param bank The Bank object to add.
     */
    public static void addBank(Bank bank) {
        if (!bankExists(bank.getID())) {
            String values = bank.csvString();
            DBConnection.runQuery("INSERT INTO Banks (bank_id, name, passcode, depositLimit, creditLimit, withdrawLimit, processingFee) VALUES " + values + ";");
        }
    }

    /**
     * Fetches a bank by ID.
     * @param bankID The ID of the bank to fetch.
     * @return The Bank object, or null if not found.
     */
    public static Bank fetchBank(String bankID) {
        if (bankExists(bankID)) {
            String sqlQuery = "SELECT * FROM Banks WHERE bank_id = '" + bankID + "'";
            ResultSet bank = DBConnection.runQuery(sqlQuery);
            try {
                while (bank.next()) {
                    String id = bank.getString("bank_id");
                    String name = bank.getString("name");
                    String passcode = bank.getString("passcode");
                    double depositlimit = bank.getDouble("depositLimit");
                    double withdrawlimit = bank.getDouble("creditLimit");
                    double creditlimit = bank.getDouble("withdrawLimit");
                    double processingfee = bank.getDouble("processingFee");
                    return new Bank(id, name, passcode, depositlimit, withdrawlimit, creditlimit, processingfee);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Retrieves all basic information of all banks in the database.
     * @return A HashMap with bank IDs as keys and bank names as values.
     */
    public static HashMap<String, String> getBanks() {
        String sqlQuery = "SELECT bank_id, name FROM Banks;";
        ResultSet banks = DBConnection.runQuery(sqlQuery);
        if (banks != null) {
            HashMap<String, String> bankDetails = new HashMap<>();
            try {
                while (banks.next()) {
                    bankDetails.put(banks.getString("bank_id"), banks.getString("name"));
                }
                return bankDetails;
            } catch (SQLException e) {
                e.printStackTrace();
            }
         }
        return null;
    }

    /**
     * Checks if a bank exists in the database by ID.
     * @param bankID The ID of the bank.
     * @return True if the bank exists, false otherwise.
     */
    public static boolean bankExists(String bankID) {
        String query = "SELECT bank_id FROM banks WHERE bank_id = '" + bankID + "';";
        ResultSet account = DBConnection.runQuery(query);
        try {
            return account.next(); // returns true if bank exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all accounts belonging to a specific bank.
     * @param bankID The ID of the bank.
     * @return A HashMap with account IDs as keys and full names as values.
     */
    public static HashMap<String, String> getAllAccounts(String bankID) {
        String query = "SELECT account_id, first_name, last_name FROM accounts WHERE bank_id = '" + bankID + "';";
        ResultSet accounts = DBConnection.runQuery(query);
        return extractAccountDetails(accounts);
    }

    /**
     * Retrieves all credit accounts belonging to a specific bank.
     * @param bankID The ID of the bank.
     * @return A HashMap with account IDs as keys and full names as values.
     */
    public static HashMap<String, String> getCreditAccounts(String bankID) {
        String query = "SELECT accounts.account_id, first_name, last_name FROM accounts " +
                "INNER JOIN credit_accounts ON accounts.account_id = credit_accounts.account_id " +
                "WHERE accounts.bank_id = '" + bankID + "';";
        ResultSet creditAccounts = DBConnection.runQuery(query);
        return extractAccountDetails(creditAccounts);
    }

    /**
     * Retrieves all savings accounts belonging to a specific bank.
     * @param bankID The ID of the bank.
     * @return A HashMap with account IDs as keys and full names as values.
     */
    public static HashMap<String, String> getSavingsAccounts(String bankID) {
        String query = "SELECT accounts.account_id, first_name, last_name FROM accounts " +
                "INNER JOIN savings_accounts ON accounts.account_id = savings_accounts.account_id " +
                "WHERE accounts.bank_id = '" + bankID + "';";
        ResultSet savingsAccounts = DBConnection.runQuery(query);
        return extractAccountDetails(savingsAccounts);
     }

    /**
     * Checks if the given ResultSet is empty.
     * @param rs The ResultSet to check.
     * @return True if the ResultSet is empty, false otherwise.
     */
    private static boolean isEmpty(ResultSet rs) {
        try {
            return (!rs.isBeforeFirst() && rs.getRow() == 0); // returns true if result set is empty
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Extracts account details from the ResultSet and maps them into a HashMap.
     * @param rs The ResultSet containing the account data.
     * @return A HashMap with account IDs as keys and full names as values, or null if empty.
     */
    private static HashMap<String, String> extractAccountDetails(ResultSet rs) {
        try {
            if (!isEmpty(rs)) {
                HashMap<String, String> accountDetails = new HashMap<>();
                while (rs.next()) {
                    String name = rs.getString("first_name") + " " + rs.getString("last_name");
                    accountDetails.put(rs.getString("account_id"), name);
                }
                return accountDetails;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
