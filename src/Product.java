import java.io.Serializable;

public class Product implements Serializable {

    // Fixed field lengths for RandomAccessFile
    public static final int NAME_LEN = 35;
    public static final int DESC_LEN = 75;
    public static final int ID_LEN = 6;

    // Fields
    private String name;
    private String description;
    private String ID;
    private double cost;

    // Constructor
    public Product(String ID, String name, String description, double cost) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    // Getters
    public String getID() { return ID.trim(); }
    public String getName() { return name.trim(); }
    public String getDescription() { return description.trim(); }
    public double getCost() { return cost; }

    // Prevent ID changes
    public void setID(String ID) {
        throw new UnsupportedOperationException("ID cannot be changed once set");
    }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCost(double cost) { this.cost = cost; }

    // Utility: pad a string to a fixed length
    public static String pad(String s, int length) {
        if (s.length() > length) {
            return s.substring(0, length);
        }
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < length) {
            sb.append(" ");
        }
        return sb.toString();
    }

    // Convert to fixed-length record for RandomAccessFile
    public String toFixedRecord() {
        return pad(name, NAME_LEN)
                + pad(description, DESC_LEN)
                + pad(ID, ID_LEN)
                + String.format("%-10.2f", cost);
    }

    // Rebuild a Product from a fixed-length record
    public static Product fromFixedRecord(String rec) {
        String name = rec.substring(0, NAME_LEN).trim();
        String desc = rec.substring(NAME_LEN, NAME_LEN + DESC_LEN).trim();
        String id = rec.substring(NAME_LEN + DESC_LEN, NAME_LEN + DESC_LEN + ID_LEN).trim();
        double cost = Double.parseDouble(
                rec.substring(NAME_LEN + DESC_LEN + ID_LEN).trim()
        );
        return new Product(id, name, desc, cost);
    }

    @Override
    public String toString() {
        return "Product [ID=" + ID +
                ", name=" + name +
                ", description=" + description +
                ", cost=" + cost + "]";
    }

    // CSV, JSON, XML (optional for this assignment)
    public String toCSV() {
        return ID + "," + name + "," + description + "," + cost;
    }

    public String toJSONRecord() {
        char DQ = '"';
        return "{" +
                DQ + "ID" + DQ + ":" + DQ + ID + DQ + "," +
                DQ + "name" + DQ + ":" + DQ + name + DQ + "," +
                DQ + "description" + DQ + ":" + DQ + description + DQ + "," +
                DQ + "cost" + DQ + ":" + cost +
                "}";
    }

    public String toXML() {
        return "<Product>" +
                "<ID>" + ID + "</ID>" +
                "<name>" + name + "</name>" +
                "<description>" + description + "</description>" +
                "<cost>" + cost + "</cost>" +
                "</Product>";
    }
}
