/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cashier.app.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adwin
 */
public class OrderTable {

    private List<List<Object>> rows;

    public OrderTable() {
        this.rows = new ArrayList<>();
    }

    /**
     * Get rows
     *
     * @return rows
     */
    public List<List<Object>> getRows() {
        return rows;
    }

    /**
     * Append row
     *
     * @param row - The row you want to add
     */
    public void addRow(List<Object> row) {
        rows.add(row);
    }

    /**
     * Set rows
     *
     * @param newRows - The new rows
     */
    public void setRows(List<List<Object>> newRows) {
        this.rows = newRows;
    }
}
