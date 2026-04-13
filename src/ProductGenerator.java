import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProductGenerator {

    public static void main(String[] args) {
        String ID = "";
        String name = "";
        String description = "";
        double cost = 0.0;
        boolean done = false;

        Scanner in = new Scanner(System.in);

        ArrayList<Product> productList = new ArrayList<>();

        // Loop and collect data for Product records into the array list
        do {
            // Get the four data fields for the Product
            ID = SafeInput.getNonZeroLenString(in, "Enter the Product ID");
            name = SafeInput.getNonZeroLenString(in, "Enter the Product name");
            description = SafeInput.getNonZeroLenString(in, "Enter the Product description");
            cost = SafeInput.getDouble(in, "Enter the Product cost");

            // Create a Product object and add it to the list
            Product product = new Product(ID, name, description, cost);
            productList.add(product);

            // Prompt user for additional records
            done = SafeInput.getYNConfirm(in, "Are you done?");
        } while (!done);

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath(), "resources", "ProductData1.txt");   // Cross-platform file path

        try {
            // Write the Product objects to the file in CSV format
            OutputStream out = new BufferedOutputStream(Files.newOutputStream
                    (file, StandardOpenOption.CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            // Write each Product to the file
            for (Product product : productList) {
                writer.write(product.toCSV());
                writer.newLine();  // Add a new line after each record
            }

            writer.close();  // Must close the file to seal it and flush the buffer
            System.out.println("Product data file written!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
