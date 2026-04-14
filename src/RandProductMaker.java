import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {

    private JTextField tfName, tfDescription, tfID, tfCost, tfRecordCount;
    private RandomAccessFile raf;
    private int recordCount = 0;

    public RandProductMaker() {
        super("Random Product Maker");

        try {
            raf = new RandomAccessFile("products.dat", "rw");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "File error: " + e.getMessage());
        }

        setLayout(new GridLayout(6, 2));

        add(new JLabel("ID:"));
        tfID = new JTextField();
        add(tfID);

        add(new JLabel("Name:"));
        tfName = new JTextField();
        add(tfName);

        add(new JLabel("Description:"));
        tfDescription = new JTextField();
        add(tfDescription);

        add(new JLabel("Cost:"));
        tfCost = new JTextField();
        add(tfCost);

        add(new JLabel("Record Count:"));
        tfRecordCount = new JTextField("0");
        tfRecordCount.setEditable(false);
        add(tfRecordCount);

        JButton btnAdd = new JButton("Add");
        JButton btnQuit = new JButton("Quit");

        add(btnAdd);
        add(btnQuit);

        btnAdd.addActionListener(e -> addRecord());
        btnQuit.addActionListener(e -> System.exit(0));

        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void addRecord() {
        try {
            String name = tfName.getText().trim();
            String desc = tfDescription.getText().trim();
            String id = tfID.getText().trim();
            double cost = Double.parseDouble(tfCost.getText().trim());

            if (name.isEmpty() || desc.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            Product p = new Product(id, name, desc, cost);
            String record = p.toFixedRecord();

            raf.seek(raf.length());
            raf.write(record.getBytes());

            recordCount++;
            tfRecordCount.setText(String.valueOf(recordCount));

            tfName.setText("");
            tfDescription.setText("");
            tfID.setText("");
            tfCost.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}
