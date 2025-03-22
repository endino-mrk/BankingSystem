package database.sqlite;

import services.transaction.Transaction;

import java.sql.*;

public class DBConnection {
    public static Connection sqliteConnection;

    /**
     * Creates a new database connection instance.
     * @throws SQLException if a database access error occurs.
     */
    public DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:master.db";
        sqliteConnection = DriverManager.getConnection(url);
        BankDBManager.createTable();
        AccountDBManager.createTable();
        TransactionDBManager.createTable();
//        Statement statement = sqliteConnection.createStatement();
//        statement.executeQuery("PRAGMA busy_timeout = 5000");
    }

    /**
     * Runs a given SQL query string.
     * - If the query is a `SELECT` statement, it returns a `ResultSet`.
     * - For other queries (`INSERT`, `UPDATE`, `DELETE`), it executes the update.
     *
     * @param query The SQL query to be executed.
     * @return ResultSet of the executed query if it's a `SELECT` query, otherwise null.
     */
    public static ResultSet runQuery(String query, Object... params) {
        if (sqliteConnection != null) {
            try {
                PreparedStatement statement = sqliteConnection.prepareStatement(query);

                if (params != null || params.length != 0) {
                    // sets the param values to query placeholders
                    for (int i = 0; i < params.length; i++) {
                        statement.setObject(i + 1, params[i]);
                    }
                }

                // executes statement based on type of query
                if (query.startsWith("SELECT")) {
                    return statement.executeQuery();
                } else {
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection to database not established.");
        }
        return null;
    }

    /**
     * Closes the current SQLite database connection.
     */
    public static void closeConnection() {
        if (sqliteConnection != null) {
            try {
                sqliteConnection.close();
                sqliteConnection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
