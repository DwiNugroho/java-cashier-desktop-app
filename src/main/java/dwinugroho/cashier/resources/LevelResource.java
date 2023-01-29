package dwinugroho.cashier.resources;

import dwinugroho.cashier.database.ConnectionHelper;
import dwinugroho.cashier.models.LevelModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelResource {
    private final LevelModel level;

    /**
     * Initialise the `level` instance for this class
     *
     * @param level - The Level instance
     */
    public LevelResource(LevelModel level) {
        this.level = level;
    }

    /**
     * Returns a level if it exists
     *
     * @return levelResult
     * @throws java.sql.SQLException - Error
     */
    public LevelModel find() throws SQLException {
        LevelModel levelResult = new LevelModel();
        String sql = "SELECT * FROM level WHERE id_level=? OR nama_level=?";
        Connection connection = ConnectionHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, level.getLevelID());
        stmt.setString(2, level.getLevelName());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            levelResult.setLevelID(rs.getLong("id_level"));
            levelResult.setLevelName(rs.getString("nama_level"));

            return levelResult;
        }

        return null;
    }

    /**
     * Get all levels from the database
     *
     * @return levelList
     * @throws java.sql.SQLException - Error
     */
    public static ObservableList<LevelModel> findAll() throws SQLException {
        // this function can be static because we don't need the `User`
        // instance here
        String sql = "SELECT * FROM level";
        Connection connection = ConnectionHelper.getConnection();
        ResultSet result = connection.createStatement().executeQuery(sql);

        // we use List instead of Vector because it's the recommended way
        ObservableList<LevelModel> levelList = FXCollections.observableArrayList();

        // iterate through the available data and add the result to `levelList`
        while (result.next()) {
            LevelModel level = new LevelModel();
            level.setLevelID(result.getLong("id_level"));
            level.setLevelName(result.getString("nama_level"));
            levelList.add(level);
        }

        return levelList;
    }
}
