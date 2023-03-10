package dwinugroho.cashier.resources;

import dwinugroho.cashier.database.ConnectionHelper;
import dwinugroho.cashier.models.OrderModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderResource {
    private final OrderModel order;

    /**
     * Initialise the `order` instance for this class
     *
     * @param order - The Order instance
     */
    public OrderResource(OrderModel order) {
        this.order = order;
    }

    /**
     * Save the passed `Order` instance to the database
     *
     * @throws java.sql.SQLException - Error
     */
    public void save() throws SQLException {
        // we should use prepared statement to prevent
        // SQL injection
        String sql = "INSERT INTO `order` (id_transaksi, id_user, no_meja, tanggal, id_masakan, jumlah_masakan, total_harga, keterangan, status_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        // this code replaces the `?` from the `sql` string above
        stmt.setLong(1, order.getTransactionID());
        stmt.setLong(2, order.getUserID());
        stmt.setLong(3, order.getTableNumber());
        stmt.setDate(4, order.getDate());
        stmt.setLong(5, order.getFoodID());
        stmt.setLong(6, order.getFoodAmount());
        stmt.setLong(7, order.getFoodPrice());
        stmt.setString(8, order.getDetails());
        stmt.setString(9, order.getStatus());

        stmt.executeUpdate();
    }

    /**
     * Update an Order
     *
     * @throws java.sql.SQLException - Error
     */
    public void update() throws SQLException {
        String sql = "UPDATE `order` SET id_transaksi=?, id_user=?, no_meja=?, tanggal=?, id_masakan=?, jumlah_masakan=?, total_harga=?, keterangan=?, status_order=? WHERE id_order=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, order.getTransactionID());
        stmt.setLong(2, order.getUserID());
        stmt.setLong(3, order.getTableNumber());
        stmt.setDate(4, order.getDate());
        stmt.setLong(5, order.getFoodID());
        stmt.setLong(6, order.getFoodAmount());
        stmt.setLong(7, order.getFoodPrice());
        stmt.setString(8, order.getDetails());
        stmt.setString(9, order.getStatus());
        stmt.setLong(10, order.getOrderID());

        stmt.executeUpdate();
    }

    /**
     * Delete an Order
     *
     * @throws java.sql.SQLException - Error
     */
    public void delete() throws SQLException {
        String sql = "DELETE FROM `order` WHERE id_order=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, order.getOrderID());

        stmt.executeUpdate();
    }

    /**
     * Get all Orders from the database
     *
     * @return orderList
     * @throws java.sql.SQLException - Error
     */
    public static ObservableList<OrderModel> findAll() throws SQLException {
        // this function can be static because we don't need the `User`
        // instance here
        String sql = "SELECT * FROM `order`";
        Connection connection = ConnectionHelper.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(sql);

        // we use List instead of Vector because it's the recommended way
        ObservableList<OrderModel> orderList = FXCollections.observableArrayList();

        // iterate through the available data and add the result to `orderList`
        while (rs.next()) {
            OrderModel order = new OrderModel();
            order.setOrderID(rs.getLong("id_order"));
            order.setTableNumber(rs.getLong("no_meja"));
            order.setDate(rs.getDate("tanggal"));
            order.setUserID(rs.getLong("id_user"));
            order.setDetails(rs.getString("keterangan"));
            order.setStatus(rs.getString("status_order"));
            orderList.add(order);
        }

        return orderList;
    }

    /**
     * Returns Order if they exists
     *
     * @return Order
     * @throws java.sql.SQLException - Error
     */
    public OrderModel find() throws SQLException {
        OrderModel orderResult = new OrderModel();
        String sql = "SELECT * FROM `order` WHERE id_order=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, order.getOrderID());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            orderResult.setOrderID(rs.getLong("id_order"));
            orderResult.setFoodID(rs.getLong("id_masakan"));
            orderResult.setFoodAmount(rs.getLong("jumlah_masakan"));
            orderResult.setFoodPrice(rs.getLong("total_harga"));
            orderResult.setTableNumber(rs.getLong("no_meja"));
            orderResult.setDate(rs.getDate("tanggal"));
            orderResult.setUserID(rs.getLong("id_user"));
            orderResult.setDetails(rs.getString("keterangan"));
            orderResult.setStatus(rs.getString("status_order"));
            return orderResult;
        }

        return null;
    }

    /**
     * Find Orders that has the same transaction ID
     *
     * @param transactionID - The transaction ID
     * @return Order
     * @throws java.sql.SQLException - Error
     */
    public static ObservableList<OrderModel> findByTransactionID(long transactionID) throws SQLException {
        ObservableList<OrderModel> orderResult = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `order` WHERE id_transaksi=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, transactionID);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            OrderModel order = new OrderModel();
            order.setOrderID(rs.getLong("id_order"));
            order.setFoodID(rs.getLong("id_masakan"));
            order.setFoodAmount(rs.getLong("jumlah_masakan"));
            order.setFoodPrice(rs.getLong("total_harga"));
            order.setTableNumber(rs.getLong("no_meja"));
            order.setDate(rs.getDate("tanggal"));
            order.setUserID(rs.getLong("id_user"));
            order.setDetails(rs.getString("keterangan"));
            order.setStatus(rs.getString("status_order"));
            orderResult.add(order);
        }

        if (!orderResult.isEmpty()) {
            return orderResult;
        }

        return null;
    }
}
