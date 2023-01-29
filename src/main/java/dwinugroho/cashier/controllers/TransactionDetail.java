package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.models.FoodModel;
import dwinugroho.cashier.models.OrderDetailModel;
import dwinugroho.cashier.models.OrderModel;
import dwinugroho.cashier.models.TransactionModel;
import dwinugroho.cashier.resources.FoodResource;
import dwinugroho.cashier.resources.OrderResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

public class TransactionDetail {
    public TableView<OrderDetailModel> foodTable;
    public ObservableList<OrderDetailModel> foods = FXCollections.observableArrayList();
    public TableColumn<OrderDetailModel, Long> foodIDColumn;
    public TableColumn<OrderDetailModel, String> nameColumn;
    public TableColumn<OrderDetailModel, Long> stockColumn;
    public TableColumn<OrderDetailModel, Long> priceColumn;
    public TableColumn tableNumber;

    public void backHandler(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("transaction");
    }

    public void initialize() {
        getData();
        initTable();
    }

    public void initTable() {
        foodIDColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.20));
        tableNumber.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.20));
        nameColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.20));
        stockColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.20));
        priceColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.20));

        foodIDColumn.setCellValueFactory(new PropertyValueFactory<OrderDetailModel, Long>("orderID"));
        tableNumber.setCellValueFactory(new PropertyValueFactory<OrderDetailModel, Long>("tableNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<OrderDetailModel, String>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<OrderDetailModel, Long>("foodAmount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<OrderDetailModel, Long>("foodPrice"));
    }

    public void getData() {
        try {
            TransactionModel transaction = (TransactionModel) FXRouter.getData();
            long transactionID = transaction.getTransactionID();
            ObservableList<OrderModel> orders = FXCollections.observableArrayList(OrderResource.findByTransactionID(transactionID));

            for (OrderModel order : orders) {
                OrderDetailModel orderDetail = new OrderDetailModel();
                orderDetail.setDate(order.getDate());
                orderDetail.setOrderID(order.getOrderID());
                orderDetail.setFoodID(order.getFoodID());
                orderDetail.setTableNumber(order.getTableNumber());
                orderDetail.setFoodAmount(order.getFoodAmount());
                orderDetail.setFoodPrice(order.getFoodPrice());

                FoodModel foodItem = FoodResource.findByID(order.getFoodID());
                orderDetail.setName(foodItem.getName());

                foods.add(orderDetail);
            }

            foodTable.setItems(foods);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        }
    }
}
