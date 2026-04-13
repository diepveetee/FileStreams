import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person p;

    @BeforeEach
    void setUp() {
        p = new Person("000010", "Heinz", "Doofenshmirtz", "Dr", 1974);
    }

    // ----------- Test setters -----------
    @Test
    void setID() {
        assertThrows(UnsupportedOperationException.class, () -> {
            p.setID("999999");
        });
    }

    @Test
    void setFirstName() {
        p.setFirstName("Heinrich");
        assertEquals("Heinrich", p.getFirstName());
    }

    @Test
    void setLastName() {
        p.setLastName("Doofenshmirtz");
        assertEquals("Doofenshmirtz", p.getLastName());
    }

    @Test
    void setTitle() {
        p.setTitle("Prof");
        assertEquals("Prof", p.getTitle());
    }

    @Test
    void setYOB() {
        p.setYOB(1980);
        assertEquals(1980, p.getYOB());
    }

    // ----------- Test special methods -----------
    @Test
    void fullName() {
        assertEquals("Heinz Doofenshmirtz", p.fullName());
    }

    @Test
    void formalName() {
        assertEquals("Dr Heinz Doofenshmirtz", p.formalName());
    }

    @Test
    void testToString() {
        String expected =
                "Person [ID=000010, firstName=Heinz, lastName=Doofenshmirtz, title=Dr, YOB=1974]";
        assertEquals(expected, p.toString());
    }

    @Test
    void toCSV() {
        String expected = "000010,Heinz,Doofenshmirtz,Dr,1974";
        assertEquals(expected, p.toCSV());
    }

    @Test
    void toJSONRecord() {
        String expected =
                "{\"IDNum\":\"000010\",\"firstName\":\"Heinz\",\"lastName\":" +
                        "\"Doofenshmirtz\",\"title\":\"Dr\",\"YOB\":1974}";
        assertEquals(expected, p.toJSONRecord());
    }

    @Test
    void toXML() {
        String expected =
                "<Person><ID>000010</ID><firstName>Heinz</firstName><lastName>" +
                        "Doofenshmirtz</lastName><title>Dr</title><YOB>1974</YOB></Person>";
        assertEquals(expected, p.toXML());
    }
}


// Want to keep this code here because even if I set the ID to the same as p's ID,
// Any attempt to call the setter constitutes as change. Thus the test fails.
//
//void setID() {
//    p.setID("000010");
//    assertEquals("000010", p.getIDNum());
//}
