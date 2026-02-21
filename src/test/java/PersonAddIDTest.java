import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PersonAddIDTest {

    @BeforeEach
    void cleanup() throws Exception {
        // Make tests independent: delete ids.txt before each test
        Files.deleteIfExists(Paths.get("ids.txt"));
    }

    @Test
    void addID_validPassport_returnsTrue_andWritesToFile() throws Exception {
        Person p = new Person();
        p.setPersonID("56s_d%&fAB");
        p.setBirthdate("15-11-2000");

        boolean result = p.addID("PASSPORT", "AB123456");

        assertTrue(result);
        assertTrue(Files.exists(Paths.get("ids.txt")));

        String content = Files.readString(Paths.get("ids.txt"));
        assertTrue(content.contains("56s_d%&fAB|PASSPORT|AB123456"));
    }

    @Test
    void addID_invalidPassport_returnsFalse_andDoesNotCreateFile() throws Exception {
        Person p = new Person();
        p.setPersonID("56s_d%&fAB");
        p.setBirthdate("15-11-2000");

        boolean result = p.addID("PASSPORT", "AB12345X"); // invalid passport pattern

        assertFalse(result);
        assertFalse(Files.exists(Paths.get("ids.txt")));
    }

    @Test
    void addID_validDriversLicence_returnsTrue() throws Exception {
        Person p = new Person();
        p.setPersonID("56s_d%&fAB");
        p.setBirthdate("15-11-2000");

        boolean result = p.addID("DRIVERS_LICENCE", "DL12345678");

        assertTrue(result);

        String content = Files.readString(Paths.get("ids.txt"));
        assertTrue(content.contains("56s_d%&fAB|DRIVERS_LICENCE|DL12345678"));
    }

    @Test
    void addID_validMedicare_returnsTrue() throws Exception {
        Person p = new Person();
        p.setPersonID("56s_d%&fAB");
        p.setBirthdate("15-11-2000");

        boolean result = p.addID("MEDICARE", "123456789");

        assertTrue(result);

        String content = Files.readString(Paths.get("ids.txt"));
        assertTrue(content.contains("56s_d%&fAB|MEDICARE|123456789"));
    }

    @Test
    void addID_studentCard_under18_noOtherIDs_returnsTrue() throws Exception {
        Person p = new Person();
        p.setPersonID("56s_d%&fAB");
        p.setBirthdate("15-11-2012"); // under 18

        boolean result = p.addID("STUDENT_CARD", "123456789012");

        assertTrue(result);

        String content = Files.readString(Paths.get("ids.txt"));
        assertTrue(content.contains("56s_d%&fAB|STUDENT_CARD|123456789012"));
    }
}