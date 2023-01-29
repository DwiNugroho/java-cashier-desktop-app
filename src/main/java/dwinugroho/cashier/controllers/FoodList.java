package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.helpers.Logout;
import dwinugroho.cashier.models.FoodModel;
import dwinugroho.cashier.resources.FoodResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class FoodList {
    public TableView<FoodModel> foodTable;
    public ObservableList<FoodModel> foods = FXCollections.observableArrayList();
    public TableColumn<FoodModel, Long> foodIDColumn;
    public TableColumn<FoodModel, String> nameColumn;
    public TableColumn<FoodModel, Long> stockColumn;
    public TableColumn<FoodModel, Long> priceColumn;
    public Button editButton;
    public Button deleteButton;

    public void backHandler(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("home");
    }

    public void logoutHandler(ActionEvent actionEvent) throws IOException {
        Logout.go();
    }

    public void initialize() {
        getData();
        initTable();
    }

    public void initTable() {
        foodTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
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
//        priceColumn.setCellValueFactory(column -> {
//            TableCell<FoodModel, String> cell = new TableCell<FoodModel, String>() {
//
//                protected void updateItem(Long item, boolean empty) {
//                    super.updateItem(String.valueOf(item), empty);
//                    if (empty) {
//                        this.setText(null);
//                    } else {
//                        this.setText(new FormatRupiah().format(item));
//                    }
//                }
//            };
//            return (ObservableValue<String>) cell;
//        });
    }

    public void getData() {
        try {
            foods = FXCollections.observableArrayList(FoodResource.findAll());
            foodTable.setItems(foods);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        }
    }

    public void editHandler(ActionEvent actionEvent) throws IOException {
        FoodModel food = foodTable.getSelectionModel().getSelectedItem();
        FXRouter.goTo("foodForm", food);
    }

    public void addHandler(ActionEvent actionEvent) throws IOException {
        FoodModel food = new FoodModel();
        FXRouter.goTo("foodForm", food);
    }

    public void deleteHandler(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus makanan yang dipilih?");
        alert.setContentText("makanan akan hilang sepenuhnya");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK){
            return;
        }

        try {
            long foodID = foodTable.getSelectionModel().getSelectedItem().foodID;
            FoodModel food = new FoodModel();
            food.setFoodID(foodID);
            new FoodResource(food).delete();

            getData();
        } catch (SQLException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Terjadi kesalahan!");
            errorAlert.setHeaderText("Terjadi kesalahan pada server kami.");
            errorAlert.showAndWait();
        }
    }
}
