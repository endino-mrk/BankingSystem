package database.sqlite;

import bank.Bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BankDBManager {

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
     * Inserts the given bank's details into the database.
     * @param bank
     */
    public static void addBank(Bank bank) {
        String values = bank.csvString();
        DBConnection.runQuery("INSERT INTO Banks (bank_id, name, passcode, depositLimit, creditLimit, withdrawLimit, processingFee) VALUES " + values + ";");
    }

    /**
     * Fetches a bank record from the database and returns a Bank object from it.
     * @param bankID
     * @return
     */
    public static Bank fetchBank(String bankID) {
        String sqlQuery = "SELECT * FROM Banks WHERE bank_id = '" + bankID + "'";
        ResultSet bank = DBConnection.runQuery(sqlQuery);
        if (bank != null) {
            try {
                bank.next();

                String id = bank.getString("bank_id");
                String name = bank.getString("name");
                String passcode = bank.getString("passcode");
                double depositlimit = bank.getDouble("depositLimit");
                double withdrawlimit = bank.getDouble("creditLimit");
                double creditlimit = bank.getDouble("withdrawLimit");
                double processingfee = bank.getDouble("processingFee");
                return new Bank(id, name, passcode, depositlimit, withdrawlimit, creditlimit, processingfee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Retrieves all basic information of all banks in the database.
     * @return An ArrayList of String objects representing a Bank's ID and name.
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






}
