import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product p;

    @BeforeEach
    void setUp() {
        // Set up a test product before each test
        p = new Product("000011", "Unscrewed Pommel", "End him rightly", 1337.15);
    }

    // ----------- Test setters -----------
    @Test
    void setID() {
        assertThrows(UnsupportedOperationException.class, () -> {
            p.setID("999999");
        });
    }

    @Test
    void setname() {
        p.setname("New Name");
        assertEquals("New Name", p.getname());
    }

    @Test
    void setdescription() {
        // Change the description and verify
        p.setdescription("New Description");
        assertEquals("New Description", p.getdescription());
    }

    // ----------- Test Utility Methods -----------

    @Test
    void testToString() {
        // Test toString() method
        String expected = "Product [ID=000011, name=Unscrewed Pommel, description=End him rightly, cost=1337.15]";
        assertEquals(expected, p.toString(), "toString should return the correct string representation.");
    }

    @Test
    void toCSV() {
        // Test toCSV() method
        String expected = "000011,Unscrewed Pommel,End him rightly,1337.15";
        assertEquals(expected, p.toCSV(), "toCSV should return the correct CSV representation.");
    }

    @Test
    void toJSONRecord() {
        // Test toJSONRecord() method
        String expected = "{\"IDNum\":\"000011\",\"name\":\"Unscrewed Pommel\",\"description\":\"End him rightly\",\"title\":\"1337.15\"}";
        assertEquals(expected, p.toJSONRecord(), "toJSONRecord should return the correct JSON representation.");
    }

    @Test
    void toXML() {
        // Test toXML() method
        String expected = "<Product><ID>000011</ID><name>Unscrewed Pommel</name><description>End him rightly</description><cost>1337.15</cost></Product>";
        assertEquals(expected, p.toXML(), "toXML should return the correct XML representation.");
    }
}
