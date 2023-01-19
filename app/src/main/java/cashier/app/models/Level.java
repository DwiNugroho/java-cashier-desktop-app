/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cashier.app.models;

/**
 *
 * @author adwin
 */
public class Level {

    public static java.util.logging.Level SEVER;

    private long levelID = 0;
    private String levelName = "";

    /**
     * Get the level ID
     *
     * @return levelID
     */
    public long getLevelID() {
        return levelID;
    }

    /**
     * Set the level ID
     *
     * @param newID - The new level ID
     */
    public void setLevelID(long newID) {
        this.levelID = newID;
    }

    /**
     * Get the level name
     *
     * @return levelName
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Set the level name
     *
     * @param newName - The new level name
     */
    public void setLevelName(String newName) {
        this.levelName = newName;
    }
}
