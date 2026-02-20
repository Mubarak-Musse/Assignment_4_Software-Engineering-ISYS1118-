import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Person {

    private static final String PERSONS_FILE = "persons.txt";
    private static final String IDS_FILE = "ids.txt";

    private String personID;
    private String firstName;
    private String lastName;
    private String address;     // expected input: StreetNo|Street|City|Victoria|Country
    private String birthdate;   // DD-MM-YYYY

    private HashMap<Date, Integer> demeritPoints = new HashMap<>();

    public Person() {
    }

    public Person(String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
    }

    // 1) addPerson()
    public boolean addPerson() {
        try {
            if (!isValidPersonID(this.personID)) return false;
            if (isBlank(this.firstName) || isBlank(this.lastName)) return false;
            if (!isValidBirthdate(this.birthdate)) return false;
            if (!isValidAddress(this.address)) return false;

            Path path = Paths.get(PERSONS_FILE);

            // avoid duplicate personID
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 5 && parts[0].equals(this.personID)) {
                        return false;
                    }
                }
            }

            // store address with commas (because we use | as delimiter in file)
            String storedAddress = this.address.trim().replace("|", ",");

            String record = this.personID.trim() + "|" +
                    this.firstName.trim() + "|" +
                    this.lastName.trim() + "|" +
                    storedAddress + "|" +
                    this.birthdate.trim() + System.lineSeparator();

            Files.write(path, record.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // 2) updatePersonalDetails(...)
    public boolean updatePersonalDetails(String newPersonID,
                                         String newFirstName,
                                         String newLastName,
                                         String newAddress,
                                         String newBirthdate) {

        try {
            File file = new File(PERSONS_FILE);
            if (!file.exists()) return false;

            if (!isValidPersonID(newPersonID)) return false;
            if (isBlank(newFirstName) || isBlank(newLastName)) return false;
            if (!isValidBirthdate(newBirthdate)) return false;
            if (!isValidAddress(newAddress)) return false;

            List<String> lines = Files.readAllLines(file.toPath());
            List<String> updatedLines = new ArrayList<>();

            boolean personFound = false;

            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length != 5) {
                    updatedLines.add(line);
                    continue;
                }

                String oldID = parts[0];
                String oldFirst = parts[1];
                String oldLast = parts[2];
                String oldStoredAddress = parts[3];  // already stored with commas
                String oldBirthdate = parts[4];

                if (oldID.equals(this.personID)) {
                    personFound = true;

                    int oldAge = ageFromBirthdate(oldBirthdate);

                    String newStoredAddress = newAddress.trim().replace("|", ",");

                    // Rule: if under 18, address can't change
                    if (oldAge < 18 && !oldStoredAddress.equals(newStoredAddress)) {
                        return false;
                    }

                    // Rule: if birthdate changes, nothing else can change
                    boolean birthdayChanged = !oldBirthdate.equals(newBirthdate.trim());
                    boolean otherChanged =
                            !oldID.equals(newPersonID.trim()) ||
                                    !oldFirst.equals(newFirstName.trim()) ||
                                    !oldLast.equals(newLastName.trim()) ||
                                    !oldStoredAddress.equals(newStoredAddress);

                    if (birthdayChanged && otherChanged) {
                        return false;
                    }

                    // Rule: if first digit of OLD id is even, id can't change
                    char firstChar = oldID.charAt(0);
                    if (Character.isDigit(firstChar)) {
                        int d = firstChar - '0';
                        if (d % 2 == 0 && !oldID.equals(newPersonID.trim())) {
                            return false;
                        }
                    }

                    String updatedLine =
                            newPersonID.trim() + "|" +
                                    newFirstName.trim() + "|" +
                                    newLastName.trim() + "|" +
                                    newStoredAddress + "|" +
                                    newBirthdate.trim();

                    updatedLines.add(updatedLine);
                } else {
                    updatedLines.add(line);
                }
            }

            if (!personFound) return false;

            Files.write(file.toPath(), updatedLines);

            // keep object in sync (helps addID, etc.)
            this.personID = newPersonID.trim();
            this.firstName = newFirstName.trim();
            this.lastName = newLastName.trim();
            this.address = newAddress.trim();
            this.birthdate = newBirthdate.trim();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // 3) addID(...)
    public boolean addID(String idType, String idNumber) {
        try {
            if (this.personID == null || this.personID.trim().isEmpty()) return false;
            if (idType == null || idNumber == null) return false;

            String type = idType.trim().toUpperCase();
            String number = idNumber.trim();

            boolean valid;

            switch (type) {
                case "PASSPORT":
                    valid = isValidPassport(number);
                    break;

                case "DRIVERS_LICENCE":
                case "DRIVER_LICENCE":
                case "DRIVERS_LICENSE":
                case "DRIVER_LICENSE":
                    type = "DRIVERS_LICENCE";
                    valid = isValidDriversLicence(number);
                    break;

                case "MEDICARE":
                    valid = isValidMedicare(number);
                    break;

                case "STUDENT_CARD":
                    if (this.birthdate == null || !isValidBirthdate(this.birthdate)) return false;

                    int age = ageFromBirthdate(this.birthdate);
                    if (age >= 18) return false;

                    // only allowed if they have no passport/licence/medicare already
                    if (hasAnyNonStudentID(this.personID.trim())) return false;

                    valid = isValidStudentCard(number);
                    break;

                default:
                    return false;
            }

            if (!valid) return false;

            // avoid exact duplicate entry (simple check)
            if (isDuplicateIDEntry(this.personID.trim(), type, number)) return false;

            String line = this.personID.trim() + "|" + type + "|" + number + System.lineSeparator();

            Files.write(Paths.get(IDS_FILE),
                    line.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // not required by the 3 main tasks (left here so project compiles)
    public String addDemeritPoints() {
        return "Success";
    }

    // -----------------------
    // validation helpers
    // -----------------------

    private boolean isValidPersonID(String id) {
        if (id == null) return false;
        id = id.trim();
        if (id.length() != 10) return false;

        char c1 = id.charAt(0);
        char c2 = id.charAt(1);

        if (c1 < '2' || c1 > '9') return false;
        if (c2 < '2' || c2 > '9') return false;

        char last1 = id.charAt(8);
        char last2 = id.charAt(9);

        if (last1 < 'A' || last1 > 'Z') return false;
        if (last2 < 'A' || last2 > 'Z') return false;

        int specialCount = 0;
        for (int i = 2; i <= 7; i++) {
            char ch = id.charAt(i);
            boolean isLetter = Character.isLetter(ch);
            boolean isDigit = Character.isDigit(ch);
            if (!isLetter && !isDigit) specialCount++;
        }

        return specialCount >= 2;
    }

    private boolean isValidBirthdate(String dob) {
        if (dob == null) return false;
        dob = dob.trim();
        if (!dob.matches("\\d{2}-\\d{2}-\\d{4}")) return false;

        // strict calendar validation (so 31-02-2000 fails)
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(dob, fmt);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidAddress(String addressWithPipes) {
        if (addressWithPipes == null) return false;

        String[] parts = addressWithPipes.split("\\|");
        if (parts.length != 5) return false;

        String streetNumber = parts[0].trim();
        String street = parts[1].trim();
        String city = parts[2].trim();
        String state = parts[3].trim();
        String country = parts[4].trim();

        if (!streetNumber.matches("\\d+")) return false;
        if (street.isEmpty() || city.isEmpty() || country.isEmpty()) return false;

        return state.equalsIgnoreCase("Victoria");
    }

    private int ageFromBirthdate(String dob) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate birth = LocalDate.parse(dob.trim(), fmt);
        return Period.between(birth, LocalDate.now()).getYears();
    }

    // -----------------------
    // ID rules (missing before)
    // -----------------------

    private boolean isValidPassport(String passport) {
        // 8 chars: 2 uppercase letters + 6 digits
        return passport != null && passport.matches("^[A-Z]{2}\\d{6}$");
    }

    private boolean isValidDriversLicence(String licence) {
        // 10 chars: 2 uppercase letters + 8 digits
        return licence != null && licence.matches("^[A-Z]{2}\\d{8}$");
    }

    private boolean isValidMedicare(String medicare) {
        // 9 digits
        return medicare != null && medicare.matches("^\\d{9}$");
    }

    private boolean isValidStudentCard(String studentCard) {
        // 12 digits
        return studentCard != null && studentCard.matches("^\\d{12}$");
    }

    private boolean hasAnyNonStudentID(String personId) {
        try {
            Path path = Paths.get(IDS_FILE);
            if (!Files.exists(path)) return false;

            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length != 3) continue;

                String pid = parts[0].trim();
                String type = parts[1].trim().toUpperCase();

                if (pid.equals(personId) && !type.equals("STUDENT_CARD")) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDuplicateIDEntry(String personId, String type, String number) {
        try {
            Path path = Paths.get(IDS_FILE);
            if (!Files.exists(path)) return false;

            for (String line : Files.readAllLines(path)) {
                String[] parts = line.split("\\|");
                if (parts.length != 3) continue;

                if (parts[0].trim().equals(personId)
                        && parts[1].trim().equalsIgnoreCase(type)
                        && parts[2].trim().equals(number)) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // getters/setters (usually helpful for tests)
    public String getPersonID() { return personID; }
    public void setPersonID(String personID) { this.personID = personID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
}