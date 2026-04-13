import java.util.Scanner;

/**
 * A class to handle safe input methods using a Scanner.
 * The Scanner used for input can be System.in or another custom Scanner.
 * Methods in this class handle various types of input validation, including Strings,
 * integers, doubles, and confirmation responses.
 */
public class SafeInputObj {

    // Instance-level variable for the scanner
    private Scanner pipe;

    /**
     * Default constructor which sets the scanner to System.in.
     */
    public SafeInputObj() {
        this.pipe = new Scanner(System.in);  // Default to System.in
    }

    /**
     * Constructor that allows specifying a custom Scanner.
     * @param scanner The Scanner to use for input.
     */
    public SafeInputObj(Scanner scanner) {
        this.pipe = scanner;  // Use the provided Scanner
    }

    /**
     * Get a String which contains at least one character.
     * @param prompt Prompt for the user.
     * @return A String response that is not zero length.
     */
    public String getNonZeroLenString(String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.length() == 0 || !retString.matches("[a-zA-Z.' ]+"));  // Check for valid input
        return retString;
    }

    /**
     * Get an int value within a specified numeric range.
     * @param prompt Input prompt message (does not include range).
     * @param low The low end of the inclusive range.
     * @param high The high end of the inclusive range.
     * @return An int value within the inclusive range.
     */
    public int getRangedInt(String prompt, int low, int high) {
        int retVal = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
            if (pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();  // Consume newline character
                if (retVal >= low && retVal <= high) {
                    done = true;
                } else {
                    System.out.println("\nNumber is out of range [" + low + "-" + high + "]: " + retVal);
                }
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter an int: " + trash);
            }
        } while (!done);

        return retVal;
    }

    /**
     * Get an unconstrained int value.
     * @param prompt The input prompt.
     * @return An unconstrained int value.
     */
    public int getInt(String prompt) {
        int retVal = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();  // Consume newline character
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter an int: " + trash);
            }
        } while (!done);

        return retVal;
    }

    /**
     * Get a double value within an inclusive range.
     * @param prompt The input prompt (does not include range info).
     * @param low The low value of the range (inclusive).
     * @param high The high value of the range (inclusive).
     * @return A double value within the specified inclusive range.
     */
    public double getRangedDouble(String prompt, double low, double high) {
        double retVal = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
            if (pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();
                pipe.nextLine();  // Consume newline character
                if (retVal >= low && retVal <= high) {
                    done = true;
                } else {
                    System.out.println("\nNumber is out of range [" + low + "-" + high + "]: " + retVal);
                }
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a double: " + trash);
            }
        } while (!done);

        return retVal;
    }

    /**
     * Get an unconstrained double value.
     * @param prompt The input prompt.
     * @return An unconstrained double value.
     */
    public double getDouble(String prompt) {
        double retVal = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();
                pipe.nextLine();  // Consume newline character
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a double: " + trash);
            }
        } while (!done);

        return retVal;
    }

    /**
     * Get a [Y/N] confirmation from the user.
     * @param prompt The input prompt (does not include [Y/N]).
     * @return True for yes, false for no.
     */
    public boolean getYNConfirm(String prompt) {
        boolean retVal = true;
        String response = "";
        boolean gotAVal = false;

        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                gotAVal = true;
                retVal = true;
            } else if (response.equalsIgnoreCase("N")) {
                gotAVal = true;
                retVal = false;
            } else {
                System.out.println("You must answer [Y/N]! " + response);
            }

        } while (!gotAVal);

        return retVal;
    }

    /**
     * Get a string that matches a RegEx pattern.
     * @param prompt The prompt for the user.
     * @param regExPattern The Java style RegEx pattern to constrain the input.
     * @return A string that matches the RegEx pattern supplied.
     */
    public String getRegExString(String prompt, String regExPattern) {
        String response = "";
        boolean gotAVal = false;

        do {
            System.out.print("\n" + prompt + ": ");
            response = pipe.nextLine();
            if (response.matches(regExPattern)) {
                gotAVal = true;
            } else {
                System.out.println("\n" + response + " must match the pattern " + regExPattern);
                System.out.println("Try again!");
            }

        } while (!gotAVal);

        return response;
    }
}

