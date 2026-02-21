import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class PersonAddIDTest {

    private static final Path IDS_PATH = Paths.get("ids.txt");
    private static final DateTimeFormatter DOB_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @BeforeEach
    void cleanup() throws Exception {
        Files.deleteIfExists(IDS_PATH);
    }

    private static String under18Dob() {
        return LocalDate.now().minusYears(17).format(DOB_FMT);
    }

    private static String adultDob() {
        return LocalDate.now().minusYears(19).format(DOB_FMT);
    }

    @Test
    void addID_validPassport_returnsTrue_andWritesToFile() throws Exception {
        Person p = new Person();
        p.setPersonID("56$_12#3AB");
        p.setBirthdate(under18Dob());

        boolean result = p.addID("PASSPORT", "AB123456");

        assertTrue(result);
        assertTrue(Files.exists(IDS_PATH));

        String content = Files.readString(IDS_PATH);
        assertTrue(content.contains("56$_12#3AB|PASSPORT|AB123456"));
    }

    @Test
    void addID_invalidPassport_returnsFalse_andDoesNotWrite() throws Exception {
        Person p = new Person();
        p.setPersonID("56$_12#3AB");
        p.setBirthdate(under18Dob());

        boolean result = p.addID("PASSPORT", "BAD");

        assertFalse(result);
        assertFalse(Files.exists(IDS_PATH));
    }

    @Test
    void addID_studentCard_under18_returnsTrue_andWritesToFile() throws Exception {
        Person p = new Person();
        p.setPersonID("56$_12#3AB");
        p.setBirthdate(under18Dob());

        boolean result = p.addID("STUDENT_CARD", "123456789012");

        assertTrue(result);
        assertTrue(Files.exists(IDS_PATH));

        String content = Files.readString(IDS_PATH);
        assertTrue(content.contains("56$_12#3AB|STUDENT_CARD|123456789012"));
    }

    @Test
    void addID_studentCard_age18OrOver_returnsFalse() throws Exception {
        Person p = new Person();
        p.setPersonID("56$_12#3AB");
        p.setBirthdate(adultDob());

        boolean result = p.addID("STUDENT_CARD", "123456789012");

        assertFalse(result);
        assertFalse(Files.exists(IDS_PATH));
    }

    @Test
    void addID_studentCard_blockedIfAlreadyHasNonStudentID_returnsFalse() throws Exception {
        Person p = new Person();
        p.setPersonID("56$_12#3AB");
        p.setBirthdate(under18Dob());

        assertTrue(p.addID("PASSPORT", "AB123456"));

        boolean result = p.addID("STUDENT_CARD", "123456789012");

        assertFalse(result);

        String content = Files.readString(IDS_PATH);
        assertTrue(content.contains("56$_12#3AB|PASSPORT|AB123456"));
        assertFalse(content.contains("56$_12#3AB|STUDENT_CARD|123456789012"));
    }
}