import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {

    private JTextField tfName, tfDescription, tfID, tfCost, tfRecordCount;

    private RandomAccessFile raf;  // RandomAccessFile for writing product records
    private int recordCount = 0; // Tracks number of records written during this session


    /**
     * Constructor sets up the GUI and opens the RandomAccessFile.
     */
    public RandProductMaker() {
        super("Random Product Maker");


        // Attempt to open the file for read/write access
        try {
            raf = new RandomAccessFile("products.dat", "rw");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "File error: " + e.getMessage());
        }

        //Form field layout
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


    /**
     * Reads user input, validates it, constructs a Product object,
     * converts it to a fixed-length record, and writes it to the file.
     */

    private void addRecord() {
        try {
            // Read and trim user input
            String name = tfName.getText().trim();
            String desc = tfDescription.getText().trim();
            String id = tfID.getText().trim();
            double cost = Double.parseDouble(tfCost.getText().trim());


            // Basic validation
            if (name.isEmpty() || desc.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            // Create Product object
            Product p = new Product(id, name, desc, cost);

            // Convert to fixed-length record
            String record = p.toFixedRecord();



            // Append record to end of file
            raf.seek(raf.length());
            raf.write(record.getBytes());


            // Update record count
            recordCount++;
            tfRecordCount.setText(String.valueOf(recordCount));

            tfName.setText("");
            tfDescription.setText("");
            tfID.setText("");
            tfCost.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid input. Please check your fields.\n\nDetails: " + ex.getMessage(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}
