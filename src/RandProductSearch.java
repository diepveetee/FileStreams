import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;

public class RandProductSearch extends JFrame {

    private JTextField tfSearch;
    private JTextArea taResults;
    private RandomAccessFile raf;

    public RandProductSearch() {
        super("Random Product Search");

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

    private void searchRecords() {
        try {
            taResults.setText("");
            raf.seek(0);

            long recordSize = Product.NAME_LEN +
                    Product.DESC_LEN +
                    Product.ID_LEN +
                    10; // cost field

            String query = tfSearch.getText().trim().toLowerCase();

            while (raf.getFilePointer() < raf.length()) {
                byte[] buffer = new byte[(int) recordSize];
                raf.read(buffer);

                String rec = new String(buffer);

                Product p = Product.fromFixedRecord(rec);

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
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}
