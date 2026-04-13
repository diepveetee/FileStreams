import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;

/**
 * @author wulft snd diepv
 * <p>
 * Use the thread-safe NIO IO library to read a Product data file
 */
public class ProductReader {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec;

        List<Product> productList = new ArrayList<>();
        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                // Typical Java pattern of inherited classes
                // We wrap a BufferedReader around a lower-level BufferedInputStream
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                // Formatting for output
                final String FORMAT = "%-6s %-20s %-30s %10s";  // Format for the header
                final String DATA_FORMAT = "%-6s %-20s %-30s %10.2f";  // Format for data (including cost)

                // Print the header for the product list
                System.out.println(String.format(FORMAT, "ID", "Name", "Description", "Cost"));
                System.out.println("==============================================================");

                // Read each line from the file
                while ((rec = reader.readLine()) != null) {
                    String[] fields = rec.split(",");

                    String ID = fields[0].trim();
                    String name = fields[1].trim();
                    String description = fields[2].trim();
                    double cost = Double.parseDouble(fields[3].trim());

                    // Create a Product object and add it to the ArrayList
                    Product product = new Product(ID, name, description, cost);
                    productList.add(product);

                    // Format and print the record
                    String formattedRecord = String.format(DATA_FORMAT, ID, name, description, cost);
                    System.out.println(formattedRecord);
                }
                reader.close();
                System.out.println("\n\nData file read!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
