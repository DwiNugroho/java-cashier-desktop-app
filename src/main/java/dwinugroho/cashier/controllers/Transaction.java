package dwinugroho.cashier.controllers;

import dwinugroho.cashier.helpers.FXRouter;
import dwinugroho.cashier.helpers.Logout;
import dwinugroho.cashier.models.TransactioFullModel;
import dwinugroho.cashier.models.TransactionModel;
import dwinugroho.cashier.resources.TransactionResource;
import dwinugroho.cashier.resources.UserResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

public class Transaction {
    public TableColumn foodIDColumn;
    public TableColumn nameColumn;
    public TableColumn totalColumn;
    public Button backButton;
    public TableView transactionTable;

    public ObservableList<TransactioFullModel> transactionList = FXCollections.observableArrayList();
    public Button detailButton;
    public TableColumn purchaseColumn;
    public TableColumn exchangeColumn;
    public TableColumn dateColumn;

    public void initialize() {
        getData();
        initTable();
    }

    public void getData()  {
        try {
            ObservableList<TransactionModel> transactionListData = (ObservableList<TransactionModel>) TransactionResource.findAll();
            if (transactionListData.size() > 0) {
                ObservableList<TransactioFullModel> transData = FXCollections.observableArrayList();

                for (TransactionModel transactionItem : transactionListData) {
                    String userName = UserResource.findByID(transactionItem.getUserID()).getName();
                    TransactioFullModel fullTransaction = new TransactioFullModel();
                    fullTransaction.setTransactionID(transactionItem.getTransactionID());
                    fullTransaction.setExchange(transactionItem.getExchange());
                    fullTransaction.setName(userName);
                    fullTransaction.setDate(transactionItem.getDate());
                    fullTransaction.setTotalPrice(transactionItem.getTotalPrice());
                    fullTransaction.setTotalPaid(transactionItem.getTotalPaid());
                    fullTransaction.setUserID(transactionItem.getUserID());
                    transData.add(fullTransaction);
                }

                transactionList = transData;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initTable() {
        transactionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                detailButton.setDisable(false);
            }
        });

        foodIDColumn.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.16));
        nameColumn.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.16));
        totalColumn.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.16));
        purchaseColumn.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.16));
        exchangeColumn.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.16));
        dateColumn.prefWidthProperty().bind(transactionTable.widthProperty().multiply(0.20));


        foodIDColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, Long>("transactionID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("name"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, Long>("totalPrice"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, Long>("totalPaid"));
        exchangeColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, Long>("exchange"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<TransactionModel, Long>("date"));

        transactionTable.setItems(transactionList);
    }

    public void backHandler(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("home");
    }

    public void logoutHandler(ActionEvent actionEvent) throws IOException {
        Logout.go();
    }

    public void detailHandler(ActionEvent actionEvent) throws IOException {
        TransactioFullModel transaction = (TransactioFullModel) transactionTable.getSelectionModel().getSelectedItem();
        FXRouter.goTo("transactionDetail", transaction);
    }
}
