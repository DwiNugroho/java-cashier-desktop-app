package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.models.CashierTableModel;
import dwinugroho.cashier.models.FoodModel;
import dwinugroho.cashier.resources.FoodResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

public class CashierFoodList {
    public ObservableList<FoodModel> foods = FXCollections.observableArrayList();
    public TableView foodTable;
    public TableColumn foodIDColumn;
    public TableColumn nameColumn;
    public TableColumn stockColumn;
    public TableColumn priceColumn;
    public Button saveButton;
    public TextField foodAmount;

    public void initialize() {
        getData();
        initTable();

        foodAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue != "") {
                saveButton.setDisable(false);
            } else {
                saveButton.setDisable(true);
            }
        });
    }

    public void initTable() {
        foodTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                foodAmount.setDisable(false);
            }
        });

        foodIDColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.25));
        nameColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.25));
        stockColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.25));
        priceColumn.prefWidthProperty().bind(foodTable.widthProperty().multiply(0.25));

        foodIDColumn.setCellValueFactory(new PropertyValueFactory<FoodModel, Long>("foodID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<FoodModel, String>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<FoodModel, Long>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<FoodModel, Long>("price"));
    }

    public void getData() {
        try {
            foods = FXCollections.observableArrayList(FoodResource.findAll());
            ObservableList<CashierTableModel> foodList = (ObservableList<CashierTableModel>) FXRouter.getData();

            for (FoodModel food : foods) {
                for (CashierTableModel tableFood : foodList) {
                    if (food.getFoodID() == tableFood.getFoodID()) {
                        food.setStock(food.getStock() - tableFood.getFoodAmount());
                    }
                }
            }

            foodTable.setItems(foods);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        }
    }

    public void backHandler(ActionEvent actionEvent) throws IOException {
        ObservableList<FoodModel> foodList = (ObservableList<FoodModel>) FXRouter.getData();
        FXRouter.goTo("cashier", foodList);
    }

    public void saveHandler(ActionEvent actionEvent) throws IOException {
        Long amountFood = Long.valueOf(foodAmount.getText());

        FoodModel slectedFood = (FoodModel) foodTable.getSelectionModel().getSelectedItem();

        if (slectedFood.getStock() < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stok makanan sedang kosong");
            alert.setHeaderText("Stok makanan sedang kosong");
            alert.showAndWait();
            return;
        }

        if (slectedFood.getStock() < amountFood) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stok makanan tidak cukup");
            alert.setHeaderText("Stok makanan tidak cukup");
            alert.showAndWait();
            return;
        }

        CashierTableModel newFood = new CashierTableModel();
        newFood.setFoodID(slectedFood.getFoodID());
        newFood.setName(slectedFood.getName());
        newFood.setFoodAmount(amountFood);
        newFood.setFoodPrice(slectedFood.getPrice() * amountFood);

        ObservableList<CashierTableModel> foodList = (ObservableList<CashierTableModel>) FXRouter.getData();
        int anyIndex = -1;

        for (int i = 0; i < foodList.size(); i++) {
            CashierTableModel oldFood = foodList.get(i);
            if (oldFood.getFoodID() == newFood.getFoodID()) {
                anyIndex = i;
                break;
            }
        }

        if (anyIndex > -1) {
            foodList.get(anyIndex).setFoodAmount(foodList.get(anyIndex).foodAmount +  newFood.foodAmount);
            foodList.get(anyIndex).setFoodPrice(foodList.get(anyIndex).foodPrice + newFood.foodPrice);
        } else {
            foodList.add(newFood);
        }

        FXRouter.goTo("cashier", foodList);
    }

    public void inputChanged(ActionEvent actionEvent) {
        saveButton.setDisable(false);
    }
}
