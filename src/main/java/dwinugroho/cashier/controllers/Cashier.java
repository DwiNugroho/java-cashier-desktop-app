package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.helpers.FormatRupiah;
import dwinugroho.cashier.helpers.LoginUser;
import dwinugroho.cashier.helpers.Logout;
import dwinugroho.cashier.models.*;
import dwinugroho.cashier.resources.FoodResource;
import dwinugroho.cashier.resources.OrderResource;
import dwinugroho.cashier.resources.TransactionResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class Cashier {

    public TableColumn foodIDColumn;
    public TableColumn nameColumn;
    public TableColumn totalColumn;
    public TableColumn priceColumn;
    public Button deleteButton;
    public TextField tableNumber;
    public TextField totalPurchase;
    public TextField foodAmountField;
    public TextField totalPriceField;
    public TextField changeField;
    public Button confirmButton;
    public Button backButton;
    public TableView foodTable;

    public ObservableList<CashierTableModel> foodList = FXCollections.observableArrayList();

    public void initialize() {
        getData();
        initTable();
        UserModel loginUser = LoginUser.getData();
        if (loginUser != null) {
            long level = loginUser.getLevelID();

            if (!(level == 1 || level == 3)) {
                backButton.setVisible(false);
            }
        }

        totalPurchase.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!totalPriceField.getText().isEmpty()) {
                updateField();
            }
        });
    }

    public void getData() {
        ObservableList<CashierTableModel> foodListData = (ObservableList<CashierTableModel>) FXRouter.getData();
        if (foodListData.size() > 0) {
            foodList = foodListData;
        }

        updateTotal();
    }

    public void initTable() {
        foodTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                deleteButton.setDisable(false);
            }
        });

        foodIDColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.25));
        nameColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.25));
        totalColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.25));
        priceColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.25));

        foodIDColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, Long>("foodID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("name"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, Long>("foodAmount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, Long>("foodPrice"));

        foodTable.setItems(foodList);
    }

    public void updateTotal() {
        for (CashierTableModel food : foodList) {
            Long newAmount;
            if (totalPriceField.getText().isEmpty()) {
                newAmount = food.getFoodPrice();
            } else {
                newAmount = food.getFoodPrice() + FormatRupiah.normalise(totalPriceField.getText());
            }
            totalPriceField.setText(FormatRupiah.format(newAmount));
        }

        for (CashierTableModel food : foodList) {
            Long newAmount;
            if (foodAmountField.getText().isEmpty()) {
                newAmount = food.getFoodAmount();
            } else {
                newAmount = food.getFoodAmount() + Long.parseLong(foodAmountField.getText());
            }
            foodAmountField.setText(String.valueOf(newAmount));
        }
    }

    public void updateField() {
        if (!totalPurchase.getText().isEmpty()) {
            Long total = FormatRupiah.normalise(totalPriceField.getText());
            Long purchase = Long.parseLong(totalPurchase.getText());

            Long change = purchase - total;

            if (change > 0) {
                changeField.setText(FormatRupiah.format(change));
            } else {
                changeField.setText("Total Bayar Tidak Cukup!");
            }
        }
    }

    public void backHandler(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("home");
    }

    public void logoutHandler(ActionEvent actionEvent) throws IOException {
        Logout.go();
    }

    public void confirmHandler(ActionEvent actionEvent) {
        if (tableNumber.getText().isEmpty() || totalPurchase.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Semua input wajib diisi!");
            alert.showAndWait();
            return;
        }

        long userID = LoginUser.getData().getUserID();
        Date date = new Date(new java.util.Date().getTime());
        long tableNumberField = Long.parseLong(tableNumber.getText());
        long totalPaid = Long.parseLong(totalPurchase.getText());
        long totalPrice = FormatRupiah.normalise(totalPriceField.getText());
        long exchange = FormatRupiah.normalise(changeField.getText());




        TransactionModel transaction = new TransactionModel();
        transaction.setUserID(userID);
        transaction.setDate(date);
        transaction.setTotalPrice(totalPrice);
        transaction.setTotalPaid(totalPaid);
        transaction.setExchange(exchange);
//
        try {
            long transactionID = new TransactionResource(transaction).save();
            OrderModel order = new OrderModel();
            FoodModel foodItem = new FoodModel();

            for (CashierTableModel food : foodList) {
                order.setTransactionID(transactionID);
                order.setUserID(userID);
                order.setDate((java.sql.Date) date);
                order.setTableNumber(tableNumberField);
                order.setFoodAmount(food.getFoodAmount());
                order.setFoodPrice(food.getFoodPrice());
                order.setFoodID(food.getFoodID());

                order.setDetails("Pesanan dilayani oleh kasir");
                order.setStatus("Lunas");

                new OrderResource(order).save();
            }

            for (CashierTableModel food : foodList) {

                FoodModel foodOnDB = FoodResource.findByID(food.getFoodID());

                foodItem.setFoodID(food.getFoodID());
                foodItem.setName(foodOnDB.getName());
                foodItem.setStock(foodOnDB.getStock() - food.getFoodAmount());
                foodItem.setPrice(foodOnDB.getPrice());

                new FoodResource(foodItem).update();
            }


            foodList.removeAll(foodList);

            tableNumber.setText("");
            foodAmountField.setText("");
            totalPriceField.setText("");
            totalPurchase.setText("");
            changeField.setText("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Berhasil!");
            alert.setHeaderText("Transaksi berhasil dibuat!");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        }
    }

    public void addHandler(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("cashierFoodList", foodList);
    }

    public void deleteHandler(ActionEvent actionEvent) {
        int foodIndex = foodTable.getSelectionModel().getSelectedIndex();
        foodList.remove(foodIndex);
    }
}
