import org.junit.jupiter.api.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class AddPersonTest {

    private static final Path PERSONS = Paths.get("persons.txt");
    private static final Path IDS = Paths.get("ids.txt");

    @BeforeEach
    void cleanFiles() throws Exception {
        Files.deleteIfExists(PERSONS);
        Files.deleteIfExists(IDS);
    }

    @AfterEach
    void cleanupAfter() throws Exception {
        Files.deleteIfExists(PERSONS);
        Files.deleteIfExists(IDS);
    }

    @Test
    void addPerson_validPerson_shouldReturnTrue() {
        Person p = new Person(
                "56s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        );
        assertTrue(p.addPerson());
    }

    @Test
    void addPerson_invalidPersonID_shouldReturnFalse() {
        Person p = new Person(
                "16s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        );
        assertFalse(p.addPerson());
    }

    @Test
    void addPerson_invalidAddress_shouldReturnFalse() {
        Person p = new Person(
                "56s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|NSW|Australia",
                "15-11-1990"
        );
        assertFalse(p.addPerson());
    }

    @Test
    void addPerson_invalidBirthdate_shouldReturnFalse() {
        Person p = new Person(
                "56s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "31-02-2000"
        );
        assertFalse(p.addPerson());
    }

    @Test
    void addPerson_duplicateID_shouldReturnFalse() {
        Person p1 = new Person(
                "56s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        );

        Person p2 = new Person(
                "56s_d%&fAB",
                "Jane",
                "Doe",
                "10|Some Street|Melbourne|Victoria|Australia",
                "10-10-1995"
        );

        assertTrue(p1.addPerson());
        assertFalse(p2.addPerson());
    }
}