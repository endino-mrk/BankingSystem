package sqlite;

import java.sql.ResultSet;
import java.util.ArrayList;
import bank.Bank;

public class SQLiteBank {
    public static final String TableName = "banks";
    public static final String TableParamDef = "id INT PRIMARY KEY NOT NULL, bankname TEXT NOT NULL, " +
        "passcode TEXT NOT NULL, depositlimit REAL NOT NULL, withdrawlimit REAL NOT NULL, " + 
        "creditlimit REAL NOT NULL, processingfee REAL NOT NULL";
    public static final String TableParams = "id, bankname, passcode, depositlimit, withdrawlimit, creditlimit, processingfee";

    /**
     * Creates a "banks" SQL table. Does nothing if it already exists.
     */
    public static void createTable() {
        if (!SQLiteInteraction.isStarted()) {
            SQLiteInteraction.start();
        }
        
        SQLiteInteraction.createTable(TableName, TableParamDef);
    }

    /**
     * Retrieves all banks from the database.
     * 
     * @return ArrayList object containing the retrieved banks.
     */
    public static ArrayList<Bank> retrieveBanks() {
        if (!SQLiteInteraction.isStarted()) {
            SQLiteInteraction.start();
        }

        ArrayList<Bank> alb = new ArrayList<>();
        Bank b;

        ResultSet rs = SQLiteInteraction.select(TableName);

        try {
            while (rs.next()) {
                b = new Bank(rs.getInt("id"), rs.getString("bankname"), 
                    rs.getString("passcode"), rs.getDouble("depositlimit"), 
                    rs.getDouble("withdrawlimit"), rs.getDouble("creditlimit"), 
                    rs.getDouble("processingfee"));

                alb.add(b);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return alb;
    }

    /**
     * Inserts a Bank object into the SQL table.
     * 
     * @param b Bank object.
     */
    public static void insertToSQL(Bank b) {
        String sqlvalues = String.format("%d, %s, %s, %f, %f, %f, %f", b.getID(), b.getName(), b.getSecret(),
            b.getDepositLimit(), b.getWithdrawLimit(), b.getCreditLimit(), b.getProcessingFee());

        if (!SQLiteInteraction.insert(TableName, TableParams, sqlvalues)) {
            System.err.println("Failed to insert to SQL.");
        }
    }

    /**
     * Updates the bank in the SQL table with new information. Possibly unused.
     * 
     * @param b Bank object.
     */
    public static void updateInSQL(Bank b) {
        String sqlkv = String.format(
            "bankname=%s, passcode=%s, depositlimit=%f, withdrawlimit=%f, creditlimit=%f, processingfee=%f",
            b.getName(), b.getSecret(), b.getDepositLimit(), b.getWithdrawLimit(), b.getCreditLimit(), b.getProcessingFee()
        );

        if (!SQLiteInteraction.update(TableName, sqlkv, String.format("id=%d", b.getID()))) {
            System.err.println("Failed to update bank in SQL.");
        }
    }
}
