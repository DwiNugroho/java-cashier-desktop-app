package dwinugroho.cashier.models;

public class OrderDetailModel extends OrderModel{
    public String name;

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }
}
