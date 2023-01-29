package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.models.LevelModel;
import dwinugroho.cashier.models.UserModel;
import dwinugroho.cashier.resources.LevelResource;
import dwinugroho.cashier.resources.UserResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;

public class UserForm {

    public Label formTitle;
    public TextField nameField;
    public String mode = "add";
    public TextField usernameField;
    public PasswordField passwordField;
    public ComboBox roleField;
    public ObservableList<LevelModel> levelList = FXCollections.observableArrayList();

    public void initialize() {
        UserModel user = (UserModel) FXRouter.getData();
        getData();

        if (user.getName() != null) {
            formTitle.setText("Ubah Data");
            nameField.setText(user.getName());
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            long levelID = user.getLevelID();
            int levelIndex = -1;

            for (int i = 0; i < roleField.getItems().size(); i++) {
                LevelModel roleItem = (LevelModel) roleField.getItems().get(i);
                if (roleItem.getLevelID() == levelID) {
                    levelIndex = i;
                    break;
                }
            }

            if (levelIndex > -1) {
                roleField.getSelectionModel().clearAndSelect(levelIndex);
            }
            mode = "edit";
        }
    }

    public void getData() {
        try {
            levelList = LevelResource.findAll();
            roleField.setItems(levelList);

            roleField.setConverter(new StringConverter<LevelModel>() {
                @Override
                public String toString(LevelModel object) {
                    if (object != null) {
                        return object.getLevelName();
                    }
                    return "";
                }

                @Override
                public LevelModel fromString(String string) {
                    ObservableList<LevelModel> items = roleField.getItems();

                    return (LevelModel) items.stream().filter(ap -> ap.getLevelName().equals(string));
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void backHandler(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("userList");
    }

    public void saveHandler(ActionEvent actionEvent) {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        LevelModel selectedRole = (LevelModel) roleField.getSelectionModel().getSelectedItem();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || selectedRole == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Gagal menambahkan data!");
            alert.setContentText("Pastikan semua input terisi!");
            alert.showAndWait();
            return;
        }

        long levelID = selectedRole.getLevelID();
        UserModel userData = new UserModel();
        userData.setName(name);
        userData.setUsername(username);
        userData.setPassword(password);
        userData.setLevelID(levelID);

        if (mode == "edit") {
            UserModel oldData = (UserModel) FXRouter.getData();
            userData.setUserID(oldData.getUserID());
        }

        try {
            if (mode == "edit") {
                new UserResource(userData).update();
            } else {
                new UserResource(userData).save();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Berhasil!");
            alert.setHeaderText("Data berhasil disimpan!");
            alert.showAndWait();

            FXRouter.goTo("userList");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        }
    }
}
