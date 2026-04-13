import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator {
    public static void main(String[] args)
    {
        String ID = "";
        String fName = "";
        String lName = "";
        String title = "";
        int YOB = 0;
        String csvRec = "";
        boolean done = false;

        Scanner in = new Scanner(System.in);

        ArrayList<Person> personList = new ArrayList<>();

        // Loop and collect data for the Person records into the array list
        do {


            // get the five data fields

            ID = SafeInput.getNonZeroLenString(in, "Enter the ID");
            fName = SafeInput.getNonZeroLenString(in, "Enter the first name");
            lName = SafeInput.getNonZeroLenString(in, "Enter the last name");
            title = SafeInput.getNonZeroLenString(in, "Enter the title");
            YOB = SafeInput.getRangedInt(in, "Enter the year for the age calc: ", 1940, 2010);
            // Create a Person object and add it to the list
            Person person = new Person(ID, fName, lName, title, YOB);
            personList.add(person);

            // Prompt user for additional records
            done = SafeInput.getYNConfirm(in, "Are you done");
        }while(!done);

        File workingDirectory = new File(System.getProperty("user.dir"));

        //Path file = Paths.get(workingDirectory.getPath() + "\\src\\Persondata.txt");             // Windows Filesystem
        Path file = Paths.get(workingDirectory.getPath(), "resources", "Persondata1.txt");   // Cross System Friendly

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            // Write the Person objects to the file in CSV format
            for (Person person : personList) {
                writer.write(person.toCSV());
                writer.newLine();  // Add a new line after each record
            }
            writer.close(); // Must close the file to seal it and flush the buffer
            System.out.println("Data file written!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}