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


import static java.nio.file.StandardOpenOption.CREATE;

/*
 *
 * @author wulft
 *
 * Use the thread safe NIO IO library to read a file
 */
public class ProductWriter {

    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec;

        List<Product> productList = new ArrayList<>();  // List to store products

        try
        {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                int line = 0;
                final String HEADER_FORMAT = "%-6s %-15s %-30s %-10s";
                final String DATA_FORMAT = "%06d %-15s %-30s %-10.2f";

                System.out.println(String.format(HEADER_FORMAT,
                        "ID#", " Name", " Description", "Price"));
                System.out.println("==============================================================");

                while ((rec = reader.readLine()) != null)
                {
                    String[] fields = rec.split(",");

                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1];
                    String desc = fields[2];
                    double price = Double.parseDouble(fields[3].trim());

                    String formattedRecord = String.format(
                            DATA_FORMAT,
                            id, name, desc, price
                    );

                    System.out.println(formattedRecord);
                }

                reader.close();
                System.out.println("\n\nData file read!");
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
