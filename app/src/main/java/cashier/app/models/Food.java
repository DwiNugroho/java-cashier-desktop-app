/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cashier.app.models;

/**
 *
 * @author adwin
 */
public class Food {

    private long foodID;
    private String name;
    private long price;
    private long stock;

    /**
     * Get the food ID
     *
     * @return food ID
     */
    public long getFoodID() {
        return foodID;
    }

    /**
     * Set the food ID
     *
     * @param id - The food ID
     */
    public void setFoodID(long id) {
        this.foodID = id;
    }

    /**
     * Get the food name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the food name
     *
     * @param newName - The new food name
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Get the food stock
     *
     * @return stock
     */
    public long getStock() {
        return stock;
    }

    /**
     * Set the food stock
     *
     * @param newStock - The new food stock
     */
    public void setStock(long newStock) {
        this.stock = newStock;
    }

    /**
     * Get the food price
     *
     * @return price
     */
    public long getPrice() {
        return price;
    }

    /**
     * Set the food price
     *
     * @param newPrice - The new food price
     */
    public void setPrice(long newPrice) {
        this.price = newPrice;
    }
}
