/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cashier.app.database;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author adwin
 */
public class ConnectionHelper {
    private static final String DB_HOST = "localhost";
    private static final String DB_NAME = "cashier";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_URL = "jdbc:mysql://"+DB_HOST+"/"+DB_NAME+"?useSSL=false";

    /**
     * Method that returns connection to the database which we can then
     * re-use over and over
     * @return connection
     * @throws java.sql.SQLException - Error
     */
    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection;
    }
}
