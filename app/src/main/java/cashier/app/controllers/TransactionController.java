/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cashier.app.controllers;

import cashier.app.database.ConnectionHelper;
import cashier.app.models.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adwin
 */
public class TransactionController {

    private final Transaction transaction;

    /**
     * Initialise the `transaction` instance for this class
     *
     * @param transaction - The Transaction instance
     */
    public TransactionController(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * Save the passed `Transaction` instance to the database
     *
     * @return
     * @throws java.sql.SQLException - Error
     */
    public long save() throws SQLException {
        // we should use prepared statement to prevent
        // SQL injection
        String sql = "INSERT INTO transaksi (id_user, tanggal, total_harga, total_bayar, kembalian) "
                + "VALUES (?, ?, ?, ?, ?)";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // this code replaces the `?` from the `sql` string above
        stmt.setLong(1, transaction.getUserID());
        stmt.setDate(2, transaction.getDate());
        stmt.setLong(3, transaction.getTotalPrice());
        stmt.setLong(4, transaction.getTotalPaid());
        stmt.setLong(5, transaction.getExchange());

        long affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        ResultSet result = stmt.getGeneratedKeys();

        if (result.next()) {
            return result.getLong(1);
        }

        return 0;
    }

    /**
     * Update an Transaction
     *
     * @throws java.sql.SQLException - Error
     */
    public void update() throws SQLException {
        String sql = "UPDATE order SET id_user=?, tanggal=?, total_harga=?, total_bayar=?, kembalian=? WHERE id_transaksi=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, transaction.getUserID());
        stmt.setDate(2, transaction.getDate());
        stmt.setLong(3, transaction.getTotalPrice());
        stmt.setLong(4, transaction.getTotalPaid());
        stmt.setLong(5, transaction.getExchange());
        stmt.setLong(6, transaction.getTransactionID());

        stmt.executeUpdate();
    }

    /**
     * Delete a Transaction
     *
     * @throws java.sql.SQLException - Error
     */
    public void delete() throws SQLException {
        String sql = "DELETE FROM transaksi WHERE id_transaksi=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, transaction.getTransactionID());

        stmt.executeUpdate();
    }

    /**
     * Get all Transactions from the database
     *
     * @return transactionList
     * @throws java.sql.SQLException - Error
     */
    public static List<Transaction> findAll() throws SQLException {
        String sql = "SELECT * FROM `transaksi`";
        Connection connection = ConnectionHelper.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(sql);

        // we use List instead of Vector because it's the recommended way
        List<Transaction> transactionList = new ArrayList<>();

        // iterate through the available data and add the result to `orderList`
        while (rs.next()) {
            Transaction transaction = new Transaction();
            transaction.setTransactionID(rs.getLong("id_transaksi"));
            transaction.setUserID(rs.getLong("id_user"));
            transaction.setDate(rs.getDate("tanggal"));
            transaction.setTotalPaid(rs.getLong("total_bayar"));
            transaction.setTotalPrice(rs.getLong("total_harga"));
            transaction.setExchange(rs.getLong("kembalian"));
            transactionList.add(transaction);
        }

        return transactionList;
    }

    /**
     * Returns Transaction if they exists
     *
     * @return transaction
     * @throws java.sql.SQLException - Error
     */
    public Transaction find() throws SQLException {
        Transaction transactionData = new Transaction();
        String sql = "SELECT * FROM order WHERE id_order=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, transactionData.getTransactionID());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            transactionData.setUserID(rs.getLong("id_user"));
            transactionData.setTransactionID(rs.getLong("id_transaksi"));
            transactionData.setDate(rs.getDate("tanggal"));
            transactionData.setTotalPrice(rs.getLong("total_harga"));
            transactionData.setTotalPaid(rs.getLong("total_bayar"));
            transactionData.setExchange(rs.getLong("kembalian"));
            return transactionData;
        }

        return null;
    }
}
