package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.helpers.Logout;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Home {

    public void logoutHandler(ActionEvent actionEvent) throws IOException {
        Logout.go();
    }

    public void goToFoodScene(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("foodList");
    }

    public void goToCashierScene(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("cashier", FXCollections.observableArrayList());
    }

    public void goToTransactionScene(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("transaction");
    }

    public void goToUserListScene(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("userList");
    }
}
