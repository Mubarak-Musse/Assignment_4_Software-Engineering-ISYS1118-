
/*Activity 1 :  */


import java.util.HashMap;
import java.util.Date;


public class Person {

    private String personID;
    private String firstName;
    private String lastName; 
    private String address;
    private String birthdate;
    // A variable that holds the demerit point 
    // with offense day 
    
    private HashMap<Date, Integer> demeritPoints;
    public boolean addPerson() {

        // Write your Code your here

        return true;
    }

    public boolean updatePersonalDetails(String newPersonID,
                                         String newFirstName,
                                         String newLastName,
                                         String newAddress,
                                         String newBirthdate) {


        try {
            java.io.File file = new java.io.File(PERSONS_FILE);
            if (!file.exists()) return false;

            java.util.List<String> lines = java.nio.file.Files.readAllLines(file.toPath());
            java.util.List<String> updatedLines = new java.util.ArrayList<>();

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
                String oldAddress = parts[3];
                String oldBirthdate = parts[4];

                if (oldID.equals(this.personID)) {
                    personFound = true;

                    // Validate base conditions (same as addPerson)
                    if (!isValidPersonID(newPersonID)) return false;
                    if (!isValidBirthdate(newBirthdate)) return false;
                    if (!isValidAddress(newAddress)) return false;

                    int oldAge = ageFromBirthdate(oldBirthdate);

                    // Rule 1: Under 18 cannot change address
                    if (oldAge < 18 && !oldAddress.equals(newAddress.replace("|", ","))) {
                        return false;
                    }

                    // Rule 2: If birthday changes, nothing else can change
                    boolean birthdayChanged = !oldBirthdate.equals(newBirthdate);
                    boolean otherChanged =
                            !oldID.equals(newPersonID) ||
                                    !oldFirst.equals(newFirstName) ||
                                    !oldLast.equals(newLastName) ||
                                    !oldAddress.equals(newAddress.replace("|", ","));

                    if (birthdayChanged && otherChanged) {
                        return false;
                    }

                    // Rule 3: If first digit of old ID is even, ID cannot change
                    char firstChar = oldID.charAt(0);
                    if ((firstChar - '0') % 2 == 0 && !oldID.equals(newPersonID)) {
                        return false;
                    }

                    String storedAddress = newAddress.replace("|", ",");

                    String updatedLine =
                            newPersonID + "|" +
                                    newFirstName + "|" +
                                    newLastName + "|" +
                                    storedAddress + "|" +
                                    newBirthdate;

                    updatedLines.add(updatedLine);
                } else {
                    updatedLines.add(line);
                }
            }

            if (!personFound) return false;

            java.nio.file.Files.write(file.toPath(), updatedLines);

            return true;

        } catch (Exception e) {
            return false;
        }

    } public String addDemeritPoints() {


        // Write Your Code here 
        return "Sucess";
    }
    private static final String PERSONS_FILE = "persons.txt";

    private boolean isValidPersonID(String id) {
        if (id == null || id.length() != 10) return false;

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
        if (!dob.matches("\\d{2}-\\d{2}-\\d{4}")) return false;

        int dd = Integer.parseInt(dob.substring(0, 2));
        int mm = Integer.parseInt(dob.substring(3, 5));
        int yyyy = Integer.parseInt(dob.substring(6, 10));

        if (mm < 1 || mm > 12) return false;
        if (dd < 1 || dd > 31) return false;
        if (yyyy < 1900 || yyyy > 2100) return false;

        return true;
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
        int yyyy = Integer.parseInt(dob.substring(6, 10));
        int mm = Integer.parseInt(dob.substring(3, 5));
        int dd = Integer.parseInt(dob.substring(0, 2));

        java.time.LocalDate birth = java.time.LocalDate.of(yyyy, mm, dd);
        java.time.LocalDate today = java.time.LocalDate.now();
        return java.time.Period.between(birth, today).getYears();
    }

}



