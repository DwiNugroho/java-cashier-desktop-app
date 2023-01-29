package dwinugroho.cashier.resources;

import dwinugroho.cashier.database.ConnectionHelper;
import dwinugroho.cashier.models.FoodModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodResource {
    public final FoodModel food;

    /**
     * Initialise the `food` instance for this class
     *
     * @param food - The Food instance
     */
    public FoodResource (FoodModel food) {
        this.food = food;
    }

    /**
     * Save the passed `food` instance to the database
     *
     * @throws java.sql.SQLException - Error
     */
    public void save() throws SQLException {
        // we should use prepared statement to prevent
        // SQL injection
        String sql = "INSERT INTO masakan (nama_masakan, harga, stok, deleted) VALUES (?, ?, ?, false)";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        // this code replaces the `?` from the `sql` string above
        // e.g (?, ?, ?) will turn into ("nama_masakan", "1000", 1, "Habis")
        stmt.setString(1, food.getName());
        stmt.setLong(2, food.getPrice());
        stmt.setLong(3, food.getStock());

        stmt.executeUpdate();
    }

    /**
     * Update `masakan`
     *
     * @throws java.sql.SQLException - Error
     */
    public void update() throws SQLException {
        String sql = "UPDATE masakan SET nama_masakan=?, harga=?, stok=? WHERE id_masakan=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, food.getName());
        stmt.setLong(2, food.getPrice());
        stmt.setLong(3, food.getStock());
        stmt.setLong(4, food.getFoodID());

        stmt.executeUpdate();
    }

    /**
     * Delete a food
     *
     * @throws java.sql.SQLException - Error
     */
    public void delete() throws SQLException {
        String sql = "UPDATE masakan SET deleted=? WHERE id_masakan=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        int id = (int) food.getFoodID();

        stmt.setBoolean(1, true);
        stmt.setInt(2, id);

        stmt.executeUpdate();
    }

    /**
     * Get all foods from the database
     *
     * @return foodList
     * @throws java.sql.SQLException - Error
     */
    public static List<FoodModel> findAll() throws SQLException {
        // this function can be static because we don't need the `User`
        // instance here
        String sql = "SELECT * FROM masakan WHERE deleted=false";
        Connection connection = ConnectionHelper.getConnection();
        ResultSet result = connection.createStatement().executeQuery(sql);

        // we use List instead of Vector because it's the recommended way
        List<FoodModel> foodList = new ArrayList<>();

        // iterate through the available data and add the result to `foodList`
        while (result.next()) {
            FoodModel food = new FoodModel();
            food.setFoodID(result.getLong("id_masakan"));
            food.setName(result.getString("nama_masakan"));
            food.setPrice(result.getLong("harga"));
            food.setStock(result.getLong("stok"));
            foodList.add(food);
        }

        return foodList;
    }

    /**
     * Returns food if it exists
     *
     * @return foodResult
     * @throws java.sql.SQLException - Error
     */
    public FoodModel find() throws SQLException {
        FoodModel foodResult = new FoodModel();
        String sql = "SELECT * FROM masakan WHERE id_masakan=? OR nama_masakan=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, food.getFoodID());
        stmt.setString(2, food.getName());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            foodResult.setFoodID(rs.getLong("id_masakan"));
            foodResult.setName(rs.getString("nama_masakan"));
            foodResult.setPrice(rs.getLong("harga"));
            foodResult.setStock(rs.getLong("stok"));

            return foodResult;
        }

        return null;
    }

    /**
     * Find a food using an ID
     *
     * @param id - The food ID
     * @return foodResult
     * @throws java.sql.SQLException - Error
     */
    public static FoodModel findByID(long id) throws SQLException {
        FoodModel foodResult = new FoodModel();
        String sql = "SELECT * FROM masakan WHERE id_masakan=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            foodResult.setFoodID(rs.getLong("id_masakan"));
            foodResult.setName(rs.getString("nama_masakan"));
            foodResult.setPrice(rs.getLong("harga"));
            foodResult.setStock(rs.getLong("stok"));

            return foodResult;
        }

        return null;
    }
}
