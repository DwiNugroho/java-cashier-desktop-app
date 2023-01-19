/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cashier.app.views;

import cashier.app.controllers.FoodController;
import cashier.app.controllers.OrderController;
import cashier.app.controllers.TransactionController;
import cashier.app.helpers.FormatRupiah;
import cashier.app.helpers.OrderTable;
import cashier.app.helpers.Popup;
import cashier.app.models.Food;
import cashier.app.models.Order;
import cashier.app.models.Transaction;
import cashier.app.models.User;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adwin
 */
public class Cashier extends javax.swing.JFrame {

    private final User loginUser;
    private DefaultTableModel foodTableModel;
    public OrderTable tableData;
    private long price = 0, amount = 0;

    /**
     * Creates new form Cashier
     *
     * @param user
     */
    public Cashier(User user) {
        this.setLocationRelativeTo(null);
        initComponents();
        initTableModel();
        setSelectListener();
        tableData = new OrderTable();
        loginUser = user;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (foodTable.getRowCount() != 0) {
                    JOptionPane.showMessageDialog(null, "Anda masih punya item tersisa!");
                    return;
                }
                System.exit(0);
            }
        });

        if (user.getLevelID() != 1) {
            backButton.setVisible(false);
        }

        totalPurchase.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                setExchangeAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setExchangeAmount();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                setExchangeAmount();
            }
        });
    }

    private void initTableModel() {
        String[] columns = new String[]{
            "ID Makanan", "Nama Makanan", "Jumlah Pesanan", "Harga"
        };
        foodTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        foodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        foodTable.setModel(foodTableModel);
        foodTable.getTableHeader().setReorderingAllowed(false); // prevent from table re-ordering
    }

    private void setSelectListener() {
        foodTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int selectedRow = foodTable.getSelectedRow();

            if (selectedRow == -1) {
                return;
            }

            deleteButton.setEnabled(true);
        });
    }

    public void setTableData(List<List<Object>> data) {
        // reset the table before adding a new one
        foodTableModel.setRowCount(0);
        foodTable.clearSelection();
        foodTable.validate();

        // populate the table
        for (List<Object> row : data) {
            foodTableModel.addRow(new Object[]{
                row.get(0),
                row.get(1),
                row.get(2),
                FormatRupiah.format((long) row.get(3)),});
        }
        tableData.setRows(data);

        // takes care of the details
        for (List<Object> row : data) {
            amount += (long) row.get(2);
        }
        foodAmountField.setText(String.valueOf(amount));

        for (List<Object> row : data) {
            price += (long) row.get(3);
        }
        totalPriceField.setText(FormatRupiah.format(price));
    }

    private void setExchangeAmount() {
        // don't do anything if we don't have any items
        if (totalPriceField.getText().isEmpty() || totalPurchase.getText().isEmpty()) {
            return;
        }

        String priceNumber = FormatRupiah.normalise(totalPriceField.getText());
        long totalPrice = Long.parseLong(priceNumber);
        long payAmount = Long.parseLong(totalPurchase.getText());
        long exchange = totalPrice - payAmount;

        // handle a case when the paid amount is insufficient
        if (exchange < 0 && !"".equals(String.valueOf(exchange))) {
            totalPriceField1.setText(FormatRupiah.format(exchange).replace("-", ""));
        } else {
            totalPriceField1.setText("Pembayaran belum cukup");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        foodTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        foodAmountField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        totalPriceField = new javax.swing.JTextField();
        deleteButton = new javax.swing.JButton();
        totalPurchase = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tableNumber = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        confirmOrder = new javax.swing.JButton();
        totalPriceField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        backButton.setText("Kembali");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Kasir");

        logoutButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        foodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Makanan", "Nama Makanan", "Jumlah Makanan", "Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        foodTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(foodTable);

        addButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addButton.setText("Tambah Makanan");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        foodAmountField.setEditable(false);
        foodAmountField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Jumlah Makanan");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Total Harga");

        totalPriceField.setEditable(false);
        totalPriceField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        deleteButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        deleteButton.setText("Hapus Makanan");

        totalPurchase.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Total Bayar");

        tableNumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Nomor Meja");

        confirmOrder.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        confirmOrder.setText("Konfirmasi Order");
        confirmOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmOrderActionPerformed(evt);
            }
        });

        totalPriceField1.setEditable(false);
        totalPriceField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Kembalian");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(356, 356, 356)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6)
                                .addComponent(totalPriceField1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5)
                                .addComponent(tableNumber)
                                .addComponent(jLabel4)
                                .addComponent(totalPurchase)
                                .addComponent(jLabel3)
                                .addComponent(totalPriceField)
                                .addComponent(jLabel2)
                                .addComponent(foodAmountField)
                                .addComponent(confirmOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(logoutButton)
                    .addComponent(backButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addButton)
                            .addComponent(deleteButton))
                        .addGap(52, 52, 52))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tableNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(foodAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPriceField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(confirmOrder)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        Popup.<Home>open(new Home(loginUser), "Halaman Utama");
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        if (foodTable.getRowCount() != 0) {
            JOptionPane.showMessageDialog(null, "Anda masih punya item tersisa!");
            return;
        }
        Popup.<Login>open(new Login(), "Authentication");
        this.dispose();
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        int rowCount = foodTableModel.getRowCount();

        if (rowCount != 0) {
            for (int i = 0; i < rowCount; i++) {
                List<Object> row = new ArrayList<>();
                row.add(foodTableModel.getValueAt(i, 0));
                row.add(foodTableModel.getValueAt(i, 1));
                row.add(foodTableModel.getValueAt(i, 2));
                row.add(foodTableModel.getValueAt(i, 3));
                tableData.addRow(row);
            }
        }

        Popup.<CashierPopup>open(new CashierPopup(this), "Tambah Makanan");
    }//GEN-LAST:event_addButtonActionPerformed

    private void confirmOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmOrderActionPerformed
        int rowCount = foodTableModel.getRowCount();

        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada item yang akan dibeli!");
            return;
        }

        if (totalPurchase.getText().isEmpty() || tableNumber.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua field!");
            return;
        }

        long userID = loginUser.getUserID();
        Date date = new Date(new java.util.Date().getTime());
        long tableNumberField = Long.parseLong(tableNumber.getText());
        long totalPaid = Long.parseLong(totalPurchase.getText());
        long totalPrice = Long.parseLong(FormatRupiah.normalise(totalPriceField.getText()));
        long exchange = Long.parseLong(FormatRupiah.normalise(totalPriceField1.getText()));

        Transaction transaction = new Transaction();
        transaction.setUserID(userID);
        transaction.setDate(date);
        transaction.setTotalPrice(totalPrice);
        transaction.setTotalPaid(totalPaid);
        transaction.setExchange(exchange);

        try {
            long transactionID = new TransactionController(transaction).save();
            Order order = new Order();

            for (int i = 0; i < rowCount; i++) {

                long amountSelected = Long.parseLong(foodTableModel.getValueAt(i, 2).toString());
                long priceSelected = Long.parseLong(FormatRupiah.normalise(foodTableModel.getValueAt(i, 3).toString()));

                Food food = new Food();
                food.setName(foodTableModel.getValueAt(i, 1).toString());
                long foodID = new FoodController(food).find().getFoodID();


                order.setTransactionID(transactionID);
                order.setUserID(userID);
                order.setDate(date);
                order.setTableNumber(tableNumberField);
                order.setFoodAmount(amountSelected);
                order.setFoodPrice(priceSelected);
                order.setFoodID(foodID);

                if (loginUser.getLevelID() == 4) {
                    order.setDetails("Pesanan dilakukan oleh pelanggan");
                    order.setStatus("Belum Dibayar");
                } else {
                    order.setDetails("Pesanan dilayani oleh kasir");
                    order.setStatus("Lunas");
                }

                new OrderController(order).save();
            }

            // cleanup for the fields and table
            foodTableModel.setRowCount(0);
            foodTable.clearSelection();
            foodTable.validate();
            tableNumber.setText("");
            totalPurchase.setText("");
            totalPriceField1.setText("");
            totalPriceField.setText("");
            foodAmountField.setText("");

            if (loginUser.getLevelID() == 4) {
                JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan! Pesanan anda akan segera dilayani");
                return;
            }

            JOptionPane.showMessageDialog(this, "Transaksi berhasil dibuat!");
        } catch (SQLException ex) {
            Logger.getLogger(Cashier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Transaksi gagal disimpan!");
        }
    }//GEN-LAST:event_confirmOrderActionPerformed

    /**
     * @param args the command line arguments
     * @param loginUser
     */
    public static void main(String args[], User loginUser) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FoodList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FoodList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FoodList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FoodList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Cashier(loginUser).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton confirmOrder;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField foodAmountField;
    private javax.swing.JTable foodTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField tableNumber;
    private javax.swing.JTextField totalPriceField;
    private javax.swing.JTextField totalPriceField1;
    private javax.swing.JTextField totalPurchase;
    // End of variables declaration//GEN-END:variables
}
