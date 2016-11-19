package de.bewusstlos.api.sql;
import java.io.*;
import java.security.InvalidParameterException;
import java.sql.*;
/**
 * Created by Yxns on 17.11.2016.
 * @author Yxns
 * @version 1.0
 */
public class SQLiteConnection {
    private Connection connection;
    private File file;
    /**
     * Class Constructor
     * @throws InvalidParameterException if file doesn't end with <code>.db</code>
     * @param file the database file. Must end with <code>.db</code> or throws {@link InvalidParameterException}.
     */
    public SQLiteConnection(File file){
        this.file = file;
        if (!file.getName().endsWith(".db")) {
            throw new InvalidParameterException("The file must be a typ a .db file.");
        }
        file.getParentFile().mkdirs();
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    /**
     * Connect to database
     * @return returns the success
     */
    public boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * Get connection
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * Get connection state
     * @return true if connection isn't null
     */
    public boolean isConnected() {
        return (this.connection != null);
    }
    /**
     * Simple get data from database
     * @param sql SQL Statement. Help at http://www.w3schools.com/sql/sql_intro.asp
     * @return {@link ResultSet} with the data of the request
     */
    public ResultSet getResult(String sql) {
        if (this.connection == null)
            return null;
        PreparedStatement statement;
        try {
            statement = this.connection.prepareStatement(sql);
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException exc) {
            exc.printStackTrace();
            return null;
        }
    }
    /**
     * Simple update database
     * @param sql SQL Statement. Help at http://www.w3schools.com/sql/sql_intro.asp
     * @return returns the success
     */
    public boolean update(String sql) {
        if (this.connection == null)
            return false;
        PreparedStatement statement;
        try {
            statement = this.connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }
}
