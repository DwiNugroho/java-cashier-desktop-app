package dwinugroho.cashier.resources;

import dwinugroho.cashier.database.ConnectionHelper;
import dwinugroho.cashier.models.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserResource {
    private UserModel user;

    /**
     * Initialise the `user` instance for this class
     *
     * @param user
     */
    public UserResource(UserModel user) {
        this.user = user;
    }

    /**
     * Save the passed `user` instance to the database
     *
     * @throws java.sql.SQLException - Error
     */
    public void save() throws SQLException {
        String sql = "INSERT INTO user(name, username, password, id_level) VALUES (?, ?, ?, ?)";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());
        stmt.setLong(4, user.getLevelID());

        stmt.executeUpdate();
    }

    /**
     * Update a user
     *
     * @throws java.sql.SQLException - Error
     */
    public void update() throws SQLException {
        String sql = "UPDATE user SET name=?, username=?, password=?, id_level=? WHERE id_user=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());
        stmt.setLong(4, user.getLevelID());
        stmt.setLong(5, user.getUserID());

        stmt.executeUpdate();
    }

    /**
     * Delete a user
     *
     * @throws java.sql.SQLException - Error
     */
    public void delete() throws SQLException {
        String sql = "DELETE FROM user WHERE id_user=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, user.getUserID());

        stmt.executeUpdate();
    }

    /**
     * Get all users from the database
     *
     * @return userList
     * @throws java.sql.SQLException - Error
     */
    public static List<UserModel> findAll() throws SQLException {

        String sql = "SELECT * FROM user";
        Connection connection = ConnectionHelper.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(sql);

        List<UserModel> userList = new ArrayList<>();

        while (rs.next()) {
            UserModel user = new UserModel();
            user.setUserID(rs.getLong("id_user"));
            user.setName(rs.getString("name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setLevelID(rs.getLong("id_level"));
            userList.add(user);
        }

        return userList;
    }

    /**
     * Returns a user if it exists
     *
     * @return userResult
     * @throws SQLException - Error
     */
    public UserModel find() throws SQLException {
        UserModel userResult = new UserModel();
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            userResult.setUserID(rs.getLong("id_user"));
            userResult.setLevelID(rs.getLong("id_level"));
            userResult.setName(rs.getString("name"));
            userResult.setUsername(rs.getString("username"));

            return userResult;
        }

        return null;
    }

    /**
     * Returns a user if it exists
     *
     * @param id - The user ID
     * @return User
     * @throws java.sql.SQLException - Error
     */
    public static UserModel findByID(long id) throws SQLException {
        UserModel userResult = new UserModel();
        String sql = "SELECT * FROM user WHERE id_user=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            userResult.setUserID(rs.getLong("id_user"));
            userResult.setLevelID(rs.getLong("id_level"));
            userResult.setName(rs.getString("name"));
            userResult.setUsername(rs.getString("username"));

            return userResult;
        }

        return null;
    }
}
