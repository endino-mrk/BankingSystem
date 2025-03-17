package sqlite;

import java.sql.*;

public class SQLiteInteraction {
    private static boolean verbose = false; // developer variable

    private static Connection c = null; 
    private static Statement stmt = null;
    private static boolean initialized = false;


    /**
     * Starts the specified SQLite database.
     * 
     * @param dbname The <i>relative</i> pathname of the database. Usually ends in '.db'
     */
    public static void start(String dbname) {
        if (initialized) {
            System.err.println("SQL database already initialized.");
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:" + dbname;
            c = DriverManager.getConnection(url);
            c.setAutoCommit(true);

            stmt = c.createStatement();
            initialized = true;
        } catch (SQLException sqle) {
            if (verbose) {
                System.err.print("Error occurred while attempting to initialize SQL database: ");
                sqle.printStackTrace();
            } else {
                System.err.println("Error occurred while attempting to initialize SQL database: " + sqle);
            }
        } catch (ClassNotFoundException cnf) {
            System.err.println("unless if the sqlite-jdbc jar file was erased or was somehow not included as an (implicit) import, this is not supposed to trigger at all.");
        }
    }

    /**
     * Starts the specified SQLite database. Alias of start(String).
     * 
     * @param dbname The <i>relative</i> pathname of the database. Usually ends in '.db'
     */
    public static void open(String dbname) { start(dbname); }

    /**
     * Starts the SQLite database. Chosen pathname is "master.db".
     */
    public static void start() { start("master.db"); }

    /**
     * Starts the SQLite database. Chosen pathname is "master.db". Alias of start().
     */
    public static void open() { start("master.db"); }



    /**
     * Stops the SQLite database.
     */
    public static void stop() {
        if (!initialized) {
            System.err.println("SQL database already stopped.");
            return;
        }

        try {
            stmt.close();
            c.close();

            stmt = null;
            c = null;
            initialized = false;
        } catch (SQLException sqle) {
            if (verbose) {
                System.err.print("Error occurred while attempting to close SQL database: ");
                sqle.printStackTrace();
            } else {
                System.err.println("Error occurred while attempting to close SQL database: " + sqle);
            }
        }
    }

    /**
     * Stops the SQLite database. Alias of stop();
     */
    public static void close() { stop(); }


    /**
     * Executes an SQL command.
     * 
     * @param sqlcommand The SQL command.
     * @return Flag if update execution is successful.
     */
    private static boolean executeUpdate(String sqlcommand) {
        if (!initialized) {
            System.err.println("SQL database not initialized.");
            return false;
        }

        try {
            stmt.executeUpdate(sqlcommand);
            return true;
        } catch (SQLException sqle) {
            if (verbose) {
                System.err.print("Error occurred while executing SQL command: ");
                sqle.printStackTrace();
            } else {
                System.err.println("Error occurred while executing SQL command: " + sqle);
            }
            return false;
        }
    }

    /**
     * Executes an SQL query. Returns null if the database is not initialized or if an exception occurred.
     * 
     * @param sqlquery The SQL query.
     * @return ResultSet object. Getting no actual results <i>still</i> returns a ResultSet.
     */
    private static ResultSet executeQuery(String sqlquery) {
        if (!initialized) {
            System.err.println("SQL database not initialized.");
            return null;
        }

        try {
            return stmt.executeQuery(sqlquery);
        } catch (SQLException sqle) {
            if (verbose) {
                System.err.print("Error occurred while executing SQL query: ");
                sqle.printStackTrace();
            } else {
                System.err.println("Error occurred while executing SQL query: " + sqle);
            }
            return null;
        }
    }


    /**
     * Creates a new table in the SQL database.
     * 
     * @param name Table name.
     * @param sqlparameters Columns for the table. Use commas when adding multiple columns.
     * @return Flag if creation is successful.
     */
    public static boolean createTable(String name, String sqlparameters) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s);", name, sqlparameters);
        
        return executeUpdate(sql);
    }

    /**
     * Inserts a new data row into a specified table in the SQL database.
     * 
     * @param table Table name.
     * @param sqlparameters Names of the columns in the table to fill for the new row. Number of columns <b>must</b> be equal to the number of values.
     * @param sqlvalues Values of the columns in the table to fill for the new row. Number of values <b>must</b> be equal to the number of columns.
     * @return Flag if insertion is successful.
     */
    public static boolean insert(String table, String sqlparameters, String sqlvalues) {
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s);", table, sqlparameters, sqlvalues);

        return executeUpdate(sql);
    }

    /**
     * Updates one or multiple rows in the specified table.
     * 
     * @param table Table name.
     * @param sqlkv Format: (column name) = (new value). Use commas when modifying multiple rows.
     * @param sqlcondition Condition for selecting which row/s to update. If null, updates all rows in the table.
     * @return Flag if update is successful.
     */
    public static boolean update(String table, String sqlkv, String sqlcondition) {
        String sql;

        if (sqlcondition == null) {
            sql = String.format("UPDATE %s SET %s;", table, sqlkv);
        } else {
            sql = String.format("UPDATE %s SET %s WHERE %s;", table, sqlkv, sqlcondition);
        }

        return executeUpdate(sql);
    }

    /**
     * Retrieves one or more columns from the rows in the table. Returns null if the database is not initialized or if the table doesn't exist.
     * 
     * @param column The columns to retrieve from the table. If null, defaults to '*' (in which case, use select(String) or select(String, String)).
     * @param table Table name.
     * @param rowfilter Condition for selecting which row/s to retrieve from. If null, selects all rows.
     * @return ResultSet object. Note that getting no actual results or an empty table <i>still</i> returns a ResultSet.
     */
    public static ResultSet select(String column, String table, String rowfilter) {
        String sql;
        column = (column == null) ? "*" : column;

        if (rowfilter == null) {
            sql = String.format("SELECT %s FROM %s;", column, table);
        } else {
            sql = String.format("SELECT %s FROM %s WHERE %s;", column, table, rowfilter);
        }

        return executeQuery(sql);
    }

    /**
     * Retrieves all the columns from selected rows in the table. Returns null if the database is not initialized or if the table doesn't exist.
     * 
     * @param table Table name.
     * @param rowfilter Condition for selecting which row/s to retrieve from. If null, selects all rows (in which case, use select(String) instead).
     * @return ResultSet object. Note that getting no actual results or an empty table <i>still</i> returns a ResultSet.
     */
    public static ResultSet select(String table, String rowfilter) {
        return select(null, table, rowfilter);
    }

    /**
     * Retrieves the entire table. Returns null if the database is not initialized or if the table doesn't exist.
     * 
     * @param table Table name.
     * @return ResultSet object. Note that an empty table <i>still</i> returns a ResultSet.
     */
    public static ResultSet select(String table) {
        return select(table, null);
    }

    /**
     * Deletes one or multiple rows from a table based on a certain condition.
     * 
     * @param table Table name.
     * @param sqlcondition Condition for selecting which row/s to delete.
     * @return Flag if deletion is successful.
     */
    public static boolean delete(String table, String sqlcondition) {
        String sql = String.format("DELETE FROM %s WHERE %s;", table, sqlcondition);

        return executeUpdate(sql);
    }
}
