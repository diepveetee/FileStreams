import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame implements ActionListener {

    private JTextField tfProductId, tfName, tfQuantity, tfPrice, tfRecordCount;
    private JButton btnAdd;

    private RandomAccessFile file;
    private int recordCount = 0;

    // Fixed field lengths
    private final int ID_LEN = 10;
    private final int NAME_LEN = 30;
    private final int QTY_LEN = 5;
    private final int PRICE_LEN = 10;

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        // Labels and text fields
        add(new JLabel("Product ID:"));
        tfProductId = new JTextField();
        add(tfProductId);

        add(new JLabel("Name:"));
        tfName = new JTextField();
        add(tfName);

        add(new JLabel("Quantity:"));
        tfQuantity = new JTextField();
        add(tfQuantity);

        add(new JLabel("Price:"));
        tfPrice = new JTextField();
        add(tfPrice);

        add(new JLabel("Record Count:"));
        tfRecordCount = new JTextField("0");
        tfRecordCount.setEditable(false);
        add(tfRecordCount);

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(this);
        add(btnAdd);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            file = new RandomAccessFile("products.dat", "rw");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addRecord();
        }
    }

    private void addRecord() {
        String id = tfProductId.getText().trim();
        String name = tfName.getText().trim();
        String qtyStr = tfQuantity.getText().trim();
        String priceStr = tfPrice.getText().trim();

        // Validation
        if (id.isEmpty() || name.isEmpty() || qtyStr.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
            return;
        }

        int qty;
        double price;

        try {
            qty = Integer.parseInt(qtyStr);
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be int and Price must be numeric!");
            return;
        }

        try {
            // Move to end of file
            file.seek(file.length());

            // Write padded fields
            writeFixedString(padRight(id, ID_LEN), file);
            writeFixedString(padRight(name, NAME_LEN), file);
            writeFixedString(padRight(String.valueOf(qty), QTY_LEN), file);
            writeFixedString(padRight(String.valueOf(price), PRICE_LEN), file);

            recordCount++;
            tfRecordCount.setText(String.valueOf(recordCount));

            clearFields();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Write error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        tfProductId.setText("");
        tfName.setText("");
        tfQuantity.setText("");
        tfPrice.setText("");
        tfProductId.requestFocus();
    }

    // Pad string to fixed length
    private String padRight(String s, int length) {
        if (s.length() > length) {
            return s.substring(0, length);
        }
        return String.format("%-" + length + "s", s);
    }

    // Write fixed-length string to file
    private void writeFixedString(String s, RandomAccessFile file) throws IOException {
        for (int i = 0; i < s.length(); i++) {
            file.writeChar(s.charAt(i));
        }
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}