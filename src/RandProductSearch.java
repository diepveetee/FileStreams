import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;

public class RandProductSearch extends JFrame {

    private JTextField tfSearch; // User input for partial name search
    private JTextArea taResults;// Displays matching product records
    private RandomAccessFile raf; // RandomAccessFile for reading product data



    /**
     * Constructor for GUI and opens the RandomAccessFile.
     */
    public RandProductSearch() {
        super("Random Product Search");

        // Attempt to open the data file for reading. R is for read only
        try {
            raf = new RandomAccessFile("products.dat", "r");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "File error: " + e.getMessage());
        }

        setLayout(new BorderLayout());

        // Top panel
        JPanel top = new JPanel();
        top.add(new JLabel("Search Name:"));
        tfSearch = new JTextField(20);
        top.add(tfSearch);

        JButton btnSearch = new JButton("Search");
        top.add(btnSearch);

        add(top, BorderLayout.NORTH);

        // Results area
        taResults = new JTextArea();
        taResults.setEditable(false);
        add(new JScrollPane(taResults), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchRecords());

        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    /**
     * Reads through the RandomAccessFile record-by-record,
     * reconstructs Product objects, and displays those whose
     * names contain the user's search text.
     */

    private void searchRecords() {
        try {
            taResults.setText("");
            raf.seek(0);    //start at beginning of file

            long recordSize = Product.NAME_LEN +
                    Product.DESC_LEN +
                    Product.ID_LEN +
                    10; // cost field at 10 chars

            String query = tfSearch.getText().trim().toLowerCase();

            tfSearch.getText().trim().toLowerCase();

            // Loop through all records in the file
            while (raf.getFilePointer() < raf.length()) {

                // Read one full fixed-length record
                byte[] buffer = new byte[(int) recordSize];
                raf.read(buffer);

                // Convert raw bytes to a string
                String rec = new String(buffer);

                // Rebuild a Product object from the fixed record
                Product p = Product.fromFixedRecord(rec);

                // Case-insensitive partial match on product name
                if (p.getName().toLowerCase().contains(query)) {
                    taResults.append(
                            "Name: " + p.getName() + "\n" +
                                    "Description: " + p.getDescription() + "\n" +
                                    "ID: " + p.getID() + "\n" +
                                    "Cost: $" + p.getCost() + "\n\n"
                    );
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred while searching the product file.\n\nDetails: " + ex.getMessage(),
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}
