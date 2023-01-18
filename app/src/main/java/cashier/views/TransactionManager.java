package cashier.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import cashier.controllers.TransactionSource;
import cashier.controllers.UserSource;
import cashier.helpers.FormatRupiah;
import cashier.helpers.Popup;
import cashier.helpers.Receipt;
import cashier.helpers.Report;
import cashier.models.Transaction;
import cashier.models.User;

public class TransactionManager extends javax.swing.JFrame {
	private DefaultTableModel transactionTableModel;
	private Admin parentWindow;
	private User currentUser;
	private Transaction currentTransaction = new Transaction();

	/**
	 * Creates new form MenuManagement
	 */
	public TransactionManager() {
	}
	public TransactionManager(Admin parent, User user) {
		this.setLocationRelativeTo(null);
		initComponents();
		initTableModel();
		parentWindow = parent;
		currentUser = user;

		populateData();
		attachOnSelectionEvent();
	}

	/**
	 * Set the desired table model
	 */
	private void initTableModel() {
		String[] columns = new String[]{
			"ID", "Nama Kasir", "Total Harga", "Total Bayar", "Kembalian", "Tanggal"
		};
		transactionTableModel = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};
		transactionTable.setModel(transactionTableModel);
		transactionTable.getTableHeader().setReorderingAllowed(false); // prevent from table re-ordering
	}

	/**
	 * Populate table with initial data from the database
	 */
	public void populateData() {
		// always reset the table first before filling it
		transactionTable.clearSelection();
		transactionTableModel.setRowCount(0);
		transactionTable.revalidate();

		try {
			List<Transaction> transactions = TransactionSource.findAll();

			for (Transaction transaction : transactions) {
				String userName = UserSource.findByID(transaction.getUserID()).getName();

				transactionTableModel.addRow(new Object[] {
					transaction.getTransactionID(),
					userName,
					FormatRupiah.format(transaction.getTotalPrice()),
					FormatRupiah.format(transaction.getTotalPaid()),
					FormatRupiah.format(transaction.getExchange()),
					transaction.getDate(),
				});
			}
		} catch (SQLException ex) {
			Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Tidak dapat memuat data!");
			ex.printStackTrace();
		}
	}

	/**
	 * Listen for each row selection and update the values accordingly
	 */
	private void attachOnSelectionEvent() {
		transactionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				int selectedRow = transactionTable.getSelectedRow();

				// we need to guard this here because when the row is cleared,
				// nothing gets selected
				if (selectedRow == -1) return;

				// set details for text fields
				nameField.setText(transactionTable.getValueAt(selectedRow, 1).toString());
				priceField.setText(transactionTable.getValueAt(selectedRow, 2).toString());
				paidField.setText(transactionTable.getValueAt(selectedRow, 3).toString());
				exchangeField.setText(transactionTable.getValueAt(selectedRow, 4).toString());

				// the only thing that matters here is the transaction id
				// because we need that to index the orders
				currentTransaction.setTransactionID(Integer.parseInt(transactionTable.getValueAt(selectedRow, 0).toString()));
			}
		});
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        windowTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();
        exchangeField = new javax.swing.JTextField();
        paidField = new javax.swing.JTextField();
        priceField = new javax.swing.JTextField();
        paidLabel = new javax.swing.JLabel();
        exchangeLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        detailButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        windowTitle.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        windowTitle.setText("Manajemen Transaksi");

        jScrollPane1.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N

        transactionTable.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama Kasir", "Total Harga", "Total Bayar", "Kembalian", "Tanggal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Long.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(transactionTable);

        exchangeField.setEditable(false);

        paidField.setEditable(false);

        priceField.setEditable(false);

        paidLabel.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        paidLabel.setText("Total Bayar");

        exchangeLabel.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        exchangeLabel.setText("Kembalian");

        priceLabel.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        priceLabel.setText("Total Harga");

        nameField.setEditable(false);

        nameLabel.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        nameLabel.setText("Nama Kasir");

        backButton.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        backButton.setText("Kembali");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        logoutButton.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        detailButton.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        detailButton.setText("Detail Transaksi");
        detailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(232, 232, 232)
                        .addComponent(windowTitle))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nameField, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(nameLabel)
                        .addComponent(priceLabel)
                        .addComponent(priceField, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(paidField, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(paidLabel)
                        .addComponent(exchangeLabel)
                        .addComponent(exchangeField, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(detailButton, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                    .addComponent(logoutButton))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logoutButton)
                    .addComponent(windowTitle)
                    .addComponent(backButton))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(priceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paidLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paidField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exchangeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exchangeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(detailButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void printReceiptButtonActionPerformed(java.awt.event.ActionEvent evt) {
//		if (transactionTable.getSelectedRow() == -1) {
//			JOptionPane.showMessageDialog(this, "Tidak ada transaksi yang dipilih!");
//			return;
//		}
//		int selectedRow = transactionTable.getSelectedRow();
//		String transactionID = transactionTable.getValueAt(selectedRow, 0);
//		Receipt.getReceipt(transactionID);
	}                                                  

	private void printReportButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// create a new row for each iteration
		List<List<String>> rows = new ArrayList<List<String>>();

		// add the header for the spreadsheet
		List<String> header = new ArrayList<String>();
		header.add("ID Transaksi");
		header.add("Nama Kasir");
		header.add("Total Bayar");
		header.add("Total Harga");
		header.add("Kembalian");
		header.add("Tanggal Transaksi");
		rows.add(header);

		// fill the row with each of the column value
		for (int i = 0, len = transactionTableModel.getRowCount(); i < len; i++) {
			List<String> row = new ArrayList<String>();
			for (int j = 0; j < transactionTableModel.getColumnCount(); j++){
				row.add(transactionTableModel.getValueAt(i, j).toString());
			}
			rows.add(row);
		}

		// export to excel
		Report.getReport(rows);
	}                                                 

	private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
		Popup.<Admin>open(new Admin(currentUser), "Halaman Admin");
		this.dispose();
	}//GEN-LAST:event_backButtonActionPerformed

	private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
		Popup.<Login>open(new Login(), "Login Aplikasi Kasir");
		this.dispose();
		parentWindow.dispose();
	}//GEN-LAST:event_logoutButtonActionPerformed

    private void detailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailButtonActionPerformed
		// throw an error if a user tries to open a detail page without selecting any transaction
		if (transactionTable.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Harap pilih transaksi terlebih dahulu!");
			return;
		}
        Popup.<TransactionPopup>open(new TransactionPopup(currentTransaction.getTransactionID()), "Detail Transaksi");
    }//GEN-LAST:event_detailButtonActionPerformed

	/**
	 * @param args the command line arguments
	*/
	public static void main(String args[]) {
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new TransactionManager().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton detailButton;
    private javax.swing.JTextField exchangeField;
    private javax.swing.JLabel exchangeLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField paidField;
    private javax.swing.JLabel paidLabel;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JTable transactionTable;
    private javax.swing.JLabel windowTitle;
    // End of variables declaration//GEN-END:variables
}
