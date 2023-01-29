package dwinugroho.cashier;

import dwinugroho.cashier.helpers.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXRouter.bind(this, primaryStage, "Cashier Application");
        this.setRoute();
        FXRouter.startFrom("login");
    }

    private void setRoute() {
        FXRouter.when("login", "login.fxml", "Authentication", 420, 420);
        FXRouter.when("home", "home.fxml", "Halaman Utama", 500, 500);
        FXRouter.when("foodList", "foodList.fxml", "Daftar Makanan");
        FXRouter.when("foodForm", "foodForm.fxml", "Makanan", 500, 500);
        FXRouter.when("cashier", "cashier.fxml", "Kasir", 728, 500);
        FXRouter.when("cashierFoodList", "cashierFoodList.fxml", "Makanan", 600, 500);
        FXRouter.when("transaction", "transactions.fxml", "Transaksi", 728, 500);
        FXRouter.when("transactionDetail", "transactionDetail.fxml", "Detail Transaksi");
        FXRouter.when("userList", "userList.fxml", "Daftar User");
        FXRouter.when("userForm", "userForm.fxml", "User", 500, 500);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
