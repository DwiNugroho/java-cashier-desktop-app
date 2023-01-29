package dwinugroho.cashier.models;

public class CashierTableModel extends OrderModel {
    public String name;

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }
}
