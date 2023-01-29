package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.models.FoodModel;
import dwinugroho.cashier.resources.FoodResource;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class FoodForm {

    public Label formTitle;
    public TextField nameField;
    public TextField stockField;
    public TextField priceField;
    public String mode = "add";

    public void initialize() {
        FoodModel food = (FoodModel) FXRouter.getData();

        if (food.getName() != null) {
            formTitle.setText("Ubah Data");
            nameField.setText(food.getName());
            stockField.setText(String.valueOf(food.getStock()));
            priceField.setText(String.valueOf(food.getPrice()));
            mode = "edit";
        }
    }

    public void backHandler(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("foodList");
    }

    public void saveHandler(ActionEvent actionEvent) {
        String name = nameField.getText();
        String stock = stockField.getText();
        String price = priceField.getText().replaceAll("[a-zA-Z\\.\\s]", "");

        if (name.isEmpty() || stock.isEmpty() || price.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pastikan semua field harus diisi!");
            alert.setHeaderText("Pastikan semua field harus diisi.");
            alert.showAndWait();
            return;
        }

        try {
            FoodModel submittedFood = new FoodModel();
            submittedFood.setName(name);
            submittedFood.setStock(Long.parseLong(stock));
            submittedFood.setPrice(Long.parseLong(price));

            FoodModel food = (FoodModel) FXRouter.getData();
            if (mode == "add") {
                new FoodResource(submittedFood).save();
            } else {
                submittedFood.setFoodID(food.getFoodID());
                new FoodResource(submittedFood).update();
            }

            FXRouter.goTo("foodList");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
