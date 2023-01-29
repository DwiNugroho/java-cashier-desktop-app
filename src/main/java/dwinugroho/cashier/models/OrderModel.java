package dwinugroho.cashier.models;

import java.sql.Date;

public class OrderModel {
    public long orderID;
    public long transactionID;
    public long userID;
    public long foodID;
    public long foodAmount;
    public long foodPrice;
    public long tableNumber;
    public String details;
    public String status;
    public Date date;

    /**
     * Get the order ID
     *
     * @return orderID
     */
    public long getOrderID() {
        return orderID;
    }

    /**
     * Set the order ID
     *
     * @param newID - The order ID
     */
    public void setOrderID(long newID) {
        this.orderID = newID;
    }

    /**
     * Get the transaction ID
     *
     * @return transactionID
     */
    public long getTransactionID() {
        return transactionID;
    }

    /**
     * Set the transaction ID
     *
     * @param newID - The new transaction ID
     */
    public void setTransactionID(long newID) {
        this.transactionID = newID;
    }

    /**
     * Get the user ID
     *
     * @return userID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Set the user ID
     *
     * @param newID - The new user ID
     */
    public void setUserID(long newID) {
        this.userID = newID;
    }

    /**
     * Get the food ID
     *
     * @return foodID
     */
    public long getFoodID() {
        return foodID;
    }

    /**
     * Set the food ID
     *
     * @param newID - The new food ID
     */
    public void setFoodID(long newID) {
        this.foodID = newID;
    }

    /**
     * Get the food amount
     *
     * @return foodAmount
     */
    public long getFoodAmount() {
        return foodAmount;
    }

    /**
     * Set the food amount
     *
     * @param newAmount - The new food amount
     */
    public void setFoodAmount(long newAmount) {
        this.foodAmount = newAmount;
    }

    /**
     * Get the food price
     *
     * @return foodAmount
     */
    public long getFoodPrice() {
        return foodPrice;
    }

    /**
     * Set the food price
     *
     * @param newPrice - The new food price
     */
    public void setFoodPrice(long newPrice) {
        this.foodPrice = newPrice;
    }

    /**
     * Get the details
     *
     * @return details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Set the order details
     *
     * @param newDetails - The new detail
     */
    public void setDetails(String newDetails) {
        this.details = newDetails;
    }

    /**
     * Get the status
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status
     *
     * @param newStatus - The new status
     */
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    /**
     * Get the table number
     *
     * @return tableNumber
     */
    public long getTableNumber() {
        return tableNumber;
    }

    /**
     * Set the table number
     *
     * @param newNumber - The new table number
     */
    public void setTableNumber(long newNumber) {
        this.tableNumber = newNumber;
    }

    /**
     * Get the order date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the user ID
     *
     * @param newDate - The new order date
     */
    public void setDate(Date newDate) {
        this.date = newDate;
    }
}
