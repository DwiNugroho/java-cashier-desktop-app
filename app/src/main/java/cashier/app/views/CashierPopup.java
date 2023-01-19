/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cashier.app.views;

import cashier.app.controllers.FoodController;
import cashier.app.helpers.FormatRupiah;
import cashier.app.models.Food;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adwin
 */
public class CashierPopup extends javax.swing.JFrame {

    private DefaultTableModel foodTableModel;
    private Cashier parentWindow;
    private Food slectedFood = new Food();

    /**
     * Creates new form CashierPopup
     *
     * @param parent
     */
    public CashierPopup(Cashier parent) {
        this.setLocationRelativeTo(null);
        initComponents();
        initTableModel();
        parentWindow = parent;
        addButton.setEnabled(false);
        getAllData();
        setSelectListener();
    }

    private void initTableModel() {
        String[] columns = new String[]{
            "ID Makanan", "Nama Makanan", "Stock Makanan", "Harga"
        };
        foodTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        foodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        foodTable.setModel(foodTableModel);
        foodTable.getTableHeader().setReorderingAllowed(false); // prevent from table re-ordering
    }

    private void getAllData() {
        try {
            List<Food> foods = FoodController.findAll();

            for (Food food : foods) {
                foodTableModel.addRow(new Object[]{
                    food.getFoodID(),
                    food.getName(),
                    food.getStock(),
                    FormatRupiah.format(food.getPrice())
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(CashierPopup.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Tidak dapat memuat data!");
        }
    }

    private void setSelectListener() {
        foodTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int selectedRow = foodTable.getSelectedRow();

            if (selectedRow == -1) {
                return;
            }

            slectedFood.setFoodID((long) foodTable.getValueAt(selectedRow, 0));
            slectedFood.setName((String) foodTable.getValueAt(selectedRow, 1));
            slectedFood.setStock((long) foodTable.getValueAt(selectedRow, 2));
            String priceNumber = FormatRupiah.normalise(foodTable.getValueAt(selectedRow, 3).toString());
            slectedFood.setPrice(Long.parseLong(priceNumber));

            addButton.setEnabled(true);
        });
    }

    private void addHandler() {
        if (totalField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Jumlah makanan tidak boleh kosong!");
            return;
        }

        if (slectedFood.getStock() < 1) {
            JOptionPane.showMessageDialog(this, "Stok makanan sedang kosong!");
            return;
        }

        if (slectedFood.getStock() < Long.parseLong(totalField.getText())) {
            JOptionPane.showMessageDialog(this, "Stok makanan tidak cukup!");
            return;
        }

        long id = slectedFood.getFoodID();
        String foodName = slectedFood.getName();
        long amount = slectedFood.getStock();
        long selectedAmount = Long.parseLong(totalField.getText());
        long price = slectedFood.getPrice();

        List<Object> item;
        item = new ArrayList<>();

        item.add(id);
        item.add(foodName);
        item.add(selectedAmount);
        item.add(price * selectedAmount);

        List<List<Object>> allItems = new ArrayList<List<Object>>();

        boolean isDuplicate = false;

        for (List<Object> old : parentWindow.tableData.getRows()) {
            // if the ID is the same, we combine both of them
            String originalID = old.get(0).toString();
            String newID = item.get(0).toString();
            long originalAmount = (long) old.get(2);
            long newAmount = (long) item.get(2);
            long originalPrice = Long.parseLong(FormatRupiah.normalise(old.get(3).toString()));
            long newPrice = Long.parseLong(FormatRupiah.normalise(item.get(3).toString()));

            // if the item already exists, modify it instead of adding a new one
            if (originalID.equals(newID)) {
                List<Object> merged = new ArrayList<Object>();
                merged.add(originalID);
                merged.add(old.get(1));
                merged.add(originalAmount + newAmount);
                merged.add(originalPrice + newPrice);

                allItems.add(merged);
                isDuplicate = true;
            } else {
                allItems.add(old);
            }
        }

        if (!isDuplicate) {
            allItems.add(item);
        }

        Food food = new Food();

        food.setFoodID(id);
        food.setName(foodName);
        food.setStock(amount - selectedAmount);
        food.setPrice(price);

        try {
            // update the data inside the database
            new FoodController(food).update();

            // cleanup, reset to initial state
            foodTable.clearSelection();
            totalField.setText("");

            // set parent table data
            parentWindow.setTableData(allItems);

            JOptionPane.showMessageDialog(this, "Makanan berhasil ditambahkan!");

            // refresh the table data
            foodTable.clearSelection();
            foodTableModel.setRowCount(0);
            foodTable.revalidate();
            getAllData();
        } catch (SQLException ex) {
            Logger.getLogger(CashierPopup.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gagal memperbarui masakan!");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        foodTable = new javax.swing.JTable();
        backButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        totalField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        foodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Makanan", "Nama Makanan", "Stok Makanan", "Harga"
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

        backButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        backButton.setText("Batal");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        addButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addButton.setText("Tambah Makanan");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        totalField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        totalField.setMinimumSize(new java.awt.Dimension(200, 22));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totalField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(addButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(totalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        this.addHandler();
    }//GEN-LAST:event_addButtonActionPerformed

    /**
     * @param args the command line arguments
     * @param parent
     */
    public static void main(String args[], Cashier parent) {
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
            java.util.logging.Logger.getLogger(CashierPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CashierPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CashierPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CashierPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CashierPopup(parent).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton backButton;
    private javax.swing.JTable foodTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField totalField;
    // End of variables declaration//GEN-END:variables
}
