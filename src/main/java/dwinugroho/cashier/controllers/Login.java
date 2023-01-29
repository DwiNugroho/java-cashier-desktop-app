package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.helpers.LoginUser;
import dwinugroho.cashier.models.UserModel;
import dwinugroho.cashier.resources.UserResource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class Login {

    public TextField usernameField;
    public PasswordField passwordField;
    public Label passwordError;
    public Label usernameError;

    private boolean formChecker() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            if (password.isEmpty()) {
                passwordError.setText("Password tidak boleh kosong");
                passwordField.requestFocus();
            }
            if (username.isEmpty()) {
                usernameError.setText("Username tidak boleh kosong");
                usernameField.requestFocus();
            }
            return false;
        }
        return true;
    }
    public void loginHandler(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!this.formChecker()) {
            return;
        }

        try {
            UserModel user = new UserModel();
            user.setPassword(password);
            user.setUsername(username);

            UserResource userResource = new UserResource(user);
            UserModel loginUser = userResource.find();

            if (loginUser ==  null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Gagal Login!");
                alert.setHeaderText("Gagal Login!");
                alert.setContentText("Username atau Password yang kamu masukkan salah!");
                alert.showAndWait();
                return;
            }

            LoginUser.setData(loginUser);
            long userLevel = loginUser.getLevelID();

            switch ((int) userLevel) {
                case 1 -> {
                    FXRouter.goTo("home");
                }
                case 2 -> {
                    FXRouter.goTo("cashier", FXCollections.observableArrayList());
                }
                default -> throw new AssertionError();
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terjadi kesalahan!");
            alert.setHeaderText("Terjadi kesalahan pada server kami.");
            alert.showAndWait();
        }

    }
}
