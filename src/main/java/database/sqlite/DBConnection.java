package database.sqlite;

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
    }

    /**
     * Runs a given SQL query string.
     * - If the query is a `SELECT` statement, it returns a `ResultSet`.
     * - For other queries (`INSERT`, `UPDATE`, `DELETE`), it executes the update.
     *
     * @param query The SQL query to be executed.
     * @return ResultSet of the executed query if it's a `SELECT` query, otherwise null.
     */
    public static ResultSet runQuery(String query) {
        if (sqliteConnection != null) {
            Statement statement;
            try {
                statement = sqliteConnection.createStatement();
                if (query.split(" ")[0].equals("SELECT")) {
                    return statement.executeQuery(query);
                } else {
                    statement.executeUpdate(query);
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
