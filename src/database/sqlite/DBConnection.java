package database.sqlite;

import java.sql.*;

public class DBConnection {
    public static Connection sqliteConnection;

    // connect with database on instantiation
    public DBConnection() throws SQLException {
        String url = "jdbc:sqlite:master.db";
        sqliteConnection = DriverManager.getConnection(url);
    }

    /**
     * Runs a given SQL query string.
     * @param query The SQL query to be executed.
     * @return ResultSet of the executed query
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
