import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ObjInputTest {

    private SafeInputObj safeInput;

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setup() {
        // Initialize the SafeInputObj with System.in
        safeInput = new SafeInputObj();
    }

    @AfterEach
    public void restoreSystemStreams() {
        // Restore original System.out and System.in after each test
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void simulateUserInput(String input) {
        // Convert input string to an InputStream
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);  // Redirect System.in to the simulated input stream
    }

    private String captureOutput(Runnable method) {
        // Capture the output printed to System.out
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);  // Redirect System.out

        // Run the method that prints to System.out
        method.run();

        return outputStream.toString();
    }

    @Test
    public void testGetNonZeroLenString_validInput() {
        // Simulate valid user input
        simulateUserInput("John");

        // Act: Call the method
        String result = safeInput.getNonZeroLenString("Enter a name");

        // Assert: Check if the returned value matches the input
        assertEquals("John", result);
    }

    @Test
    public void testGetNonZeroLenString_invalidInput() {
        // Simulate invalid user input followed by valid input
        simulateUserInput("\nJohn");

        // Act: Call the method
        String result = safeInput.getNonZeroLenString("Enter a name");

        // Assert: Check if the returned value matches the valid input after retrying
        assertEquals("John", result);
    }

    @Test
    public void testGetRangedInt_validInput() {
        // Simulate valid user input within the specified range
        simulateUserInput("5");

        // Act: Call the method with a valid range
        int result = safeInput.getRangedInt("Enter a number", 1, 10);

        // Assert: Verify the result is within the range
        assertEquals(5, result);
    }

    @Test
    public void testGetRangedInt_invalidInput() {
        // Simulate invalid input followed by valid input within range
        simulateUserInput("15\n5");

        // Act: Call the method with a valid range
        int result = safeInput.getRangedInt("Enter a number", 1, 10);

        // Assert: Ensure the second valid input is returned
        assertEquals(5, result);
    }

    @Test
    public void testGetInt_validInput() {
        // Simulate valid user input for an integer
        simulateUserInput("42");

        // Act: Call the method
        int result = safeInput.getInt("Enter an integer");

        // Assert: Verify the returned value matches the input
        assertEquals(42, result);
    }

    @Test
    public void testGetYNConfirm_validInputYes() {
        // Simulate user input "Y" for Yes
        simulateUserInput("Y");

        // Act: Call the method
        boolean result = safeInput.getYNConfirm("Do you want to continue");

        // Assert: Verify the result is true for "Y"
        assertTrue(result);
    }

    @Test
    public void testGetYNConfirm_validInputNo() {
        // Simulate user input "N" for No
        simulateUserInput("N");

        // Act: Call the method
        boolean result = safeInput.getYNConfirm("Do you want to continue");

        // Assert: Verify the result is false for "N"
        assertFalse(result);
    }

    @Test
    public void testGetRegExString_validInput() {
        // Simulate valid user input matching the regex
        simulateUserInput("abc123");

        // Act: Call the method with a regex pattern
        String result = safeInput.getRegExString("Enter a valid input", "[a-zA-Z0-9]+");

        // Assert: Check if the result matches the expected value
        assertEquals("abc123", result);
    }

    @Test
    public void testGetRegExString_invalidInput() {
        // Simulate invalid input followed by valid input
        simulateUserInput("!!invalid\nabc123");

        // Act: Call the method
        String result = safeInput.getRegExString("Enter a valid input", "[a-zA-Z0-9]+");

        // Assert: Ensure the valid input ("abc123") is accepted
        assertEquals("abc123", result);
    }

    @Test
    public void testGetRangedInt_invalidInputMessage() {
        // Simulate invalid input followed by valid input within range
        simulateUserInput("15\n5");

        // Capture the output printed during invalid input
        String output = captureOutput(() -> safeInput.getRangedInt("Enter a number", 1, 10));

        // Assert: Check that the invalid input message was printed
        assertTrue(output.contains("You must enter an int"));
        assertTrue(output.contains("Number is out of range"));
    }

    @Test
    public void testGetYNConfirm_invalidInputMessage() {
        // Simulate invalid input followed by valid input
        simulateUserInput("Maybe\nY");

        // Capture the output printed during invalid input
        String output = captureOutput(() -> safeInput.getYNConfirm("Do you want to continue"));

        // Assert: Check that the invalid input message was printed
        assertTrue(output.contains("You must answer [Y/N]"));
    }
}
