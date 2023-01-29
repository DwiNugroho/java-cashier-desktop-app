package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.helpers.Logout;
import dwinugroho.cashier.models.UserModel;
import dwinugroho.cashier.resources.UserResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class UserList {
    public TableView<UserModel> userTable;
    public ObservableList<UserModel> users = FXCollections.observableArrayList();
    public Button editButton;
    public Button deleteButton;
    public TableColumn userIDColumn;
    public TableColumn nameColumn;
    public TableColumn usernameColumn;
    public TableColumn passwordColumn;
    public TableColumn roleColumn;

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
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });

        userIDColumn.prefWidthProperty().bind(userTable.widthProperty().multiply(0.20));
        nameColumn.prefWidthProperty().bind(userTable.widthProperty().multiply(0.20));
        usernameColumn.prefWidthProperty().bind(userTable.widthProperty().multiply(0.20));
        passwordColumn.prefWidthProperty().bind(userTable.widthProperty().multiply(0.20));
        roleColumn.prefWidthProperty().bind(userTable.widthProperty().multiply(0.20));

        userIDColumn.setCellValueFactory(new PropertyValueFactory<UserModel, Long>("userID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<UserModel, Long>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<UserModel, Long>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<UserModel, Long>("levelID"));
    }

    public void getData() {
        try {
            users = FXCollections.observableArrayList(UserResource.findAll());
            userTable.setItems(users);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        }
    }

    public void editHandler(ActionEvent actionEvent) throws IOException {
        UserModel user = userTable.getSelectionModel().getSelectedItem();
        FXRouter.goTo("userForm", user);
    }

    public void addHandler(ActionEvent actionEvent) throws IOException {
        UserModel user = new UserModel();
        FXRouter.goTo("userForm", user);
    }

    public void deleteHandler(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Hapus user yang dipilih?");
        alert.setContentText("User akan hilang sepenuhnya");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK){
            return;
        }

        UserModel userData = userTable.getSelectionModel().getSelectedItem();

        try {
            new UserResource(userData).delete();
            getData();
        } catch (SQLException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        }
    }
}
