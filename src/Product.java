import java.util.Objects;

public class Product {

    // Fields (Instance Variables)
    private String name;
    private String description;
    private String ID;  // ID should never change sequence of digits
    private double cost;

    // Constructors


    /**
     * Constructor that initializes a Product with first name, last name, and year of birth.
     * The ID is generated automatically.
     *
     * @param ID The ID of the product
     * @param name The first name of the Product.
     * @param description The last name of the Product.
     * @param cost The cost of the Product.
     * @throws IllegalArgumentException If YOB is outside the range 1940-2010.
     */
    public Product(String ID, String name, String description, double cost)
    {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
    
    // Getters and Setters for Instance Variables

    /**
     * Retrieves the unique ID number of the product.
     * @return The ID number.
     */
    public String getID() {
        return ID;
    }


    /**
     * Prevent changing the ID after initialization
     * @param ID The ID number to set. This will always throw an exception.
     * @throws UnsupportedOperationException This method always throws an exception since ID cannot be changed.
     */
    public void setID(String ID) {
        throw new UnsupportedOperationException("ID cannot be changed once set");
    }

    /**
     * Retrieves the first name of the Product.
     * @return The first name of the Product.
     */
    public String getname() {
        return name;
    }

    /**
     * Sets the first name of the Product.
     * @param name The new first name for the Product.
     */
    public void setname(String name) {
        this.name = name;
    }

    /**
     * Retrieves the last name of the Product.
     * @return The last name of the Product.
     */
    public String getdescription() {
        return description;
    }

    /**
     * Sets the last name of the Product.
     * @param description The new last name for the Product.
     */
    public void setdescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the year of birth of the Product.
     * @return The year of birth.
     */
    public double getCost() {
        return cost;
    }

    // Utility Methods

    /**
     * Converts the Product object to a string representation.
     * @return A string representing the Product object.
     */
    @Override
    public String toString() {
        return "Product [ID=" + ID
                + ", name=" + name
                + ", description=" + description
                + ", cost=" + cost
                + "]";
    }



    /**
     * Computes the hash code for the Product object based on the ID number.
     *
     * @return The hash code of the Product object.
     */

    /**
     * Converts the Product object to a CSV (Comma Separated Values) string representation.
     * The fields are ordered as: name, description, IDNum, title, and YOB.
     *
     * @return A CSV string representation of the Product object.
     */
    public String toCSV() {
        return ID + "," + name + "," + description + "," + cost;
    }


    /**
     * Converts the Product object to a JSON string representation.
     * The JSON structure includes: IDNum, name, description, title, and YOB.
     *
     * @return A JSON string representation of the Product object.
     *
     * diepv Comment: Might try using Gson library for this function to remove manual quotes
     */
    public String toJSONRecord() {
        char DQ = '"';
        return "{" +
                DQ + "IDNum" + DQ + ":" + DQ + this.ID + DQ + "," +
                DQ + "name" + DQ + ":" + DQ + this.name + DQ + "," +
                DQ + "description" + DQ + ":" + DQ + this.description + DQ + "," +
                DQ + "title" + DQ + ":" + DQ + this.cost + DQ +
                "}";
    }

    /**
     * Converts the Product object to an XML string representation.
     * The XML structure includes: name, description, ID, title, and YOB.
     *
     * @return A string representing the Product object in XML format.
     */
    public String toXML() {
        return "<Product>" +
                "<ID>" + ID + "</ID>" +
                "<name>" + name + "</name>" +
                "<description>" + description + "</description>" +
                "<cost>" + cost + "</cost>" +
                "</Product>";
    }
}

