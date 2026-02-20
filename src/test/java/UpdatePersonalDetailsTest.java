import org.junit.jupiter.api.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class UpdatePersonalDetailsTest {

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
    void update_validChange_shouldReturnTrue() {
        Person p = new Person(
                "56s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        );

        assertTrue(p.addPerson());

        boolean result = p.updatePersonalDetails(
                "56s_d%&fAB",
                "Johnny",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        );

        assertTrue(result);
    }

    @Test
    void update_personNotFound_shouldReturnFalse() {
        Person p = new Person(
                "56s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        );

        assertFalse(p.updatePersonalDetails(
                "56s_d%&fAB",
                "Johnny",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        ));
    }

    @Test
    void update_under18CannotChangeAddress_shouldReturnFalse() {
        Person p = new Person(
                "56s_d%&fAB",
                "Kid",
                "One",
                "10|Young Street|Melbourne|Victoria|Australia",
                "01-01-2010"
        );

        assertTrue(p.addPerson());

        assertFalse(p.updatePersonalDetails(
                "56s_d%&fAB",
                "Kid",
                "One",
                "99|Other Street|Melbourne|Victoria|Australia",
                "01-01-2010"
        ));
    }

    @Test
    void update_birthdayChangedWithOtherChange_shouldReturnFalse() {
        Person p = new Person(
                "56s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        );

        assertTrue(p.addPerson());

        assertFalse(p.updatePersonalDetails(
                "56s_d%&fAB",
                "Johnny",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "16-11-1990"
        ));
    }

    @Test
    void update_evenFirstDigitCannotChangeID_shouldReturnFalse() {
        Person p = new Person(
                "66s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        );

        assertTrue(p.addPerson());

        assertFalse(p.updatePersonalDetails(
                "67s_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990"
        ));
    }
}