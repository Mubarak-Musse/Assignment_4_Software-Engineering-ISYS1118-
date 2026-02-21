
/*Activity 1 :  */

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Date;


public class Person {

    private String personID;
    private String firstName;
    private String lastName; 
    private String address;
    private String birthdate; 


    
public class SimplePersonStorage {

    public static boolean addPerson(String filePath, String personId,String address, String birthdate) {

        if (!isValidPersonId(personId)) {
            return false;
        }

        if (!isValidAddress(address)) {
            return false;
        }

        if (!isValidBirthdate(birthdate)) {
            return false;
        }

        String line = personId + "|" + address + "|" + birthdate + System.lineSeparator();

        try (FileWriter writer = new FileWriter(filePath, true)) { // append mode [web:8]
            writer.write(line);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----- Condition 1: personId -----
    private static boolean isValidPersonId(String id) {
        if (id == null || id.length() != 10) {
            return false;
        }

        char c0 = id.charAt(0);
        char c1 = id.charAt(1);

        // first two chars: digits between 2 and 9
        if (!Character.isDigit(c0) || !Character.isDigit(c1)) {
            return false;
        }
        if (c0 < '2' || c0 > '9' || c1 < '2' || c1 > '9') {
            return false;
        }

        // last two chars: uppercase letters
        char c8 = id.charAt(8);
        char c9 = id.charAt(9);
        if (!Character.isUpperCase(c8) || !Character.isUpperCase(c9)) {
            return false;
        }

        // middle 6 chars (index 2..7) must contain at least 2 special chars
        int specialCount = 0;
        for (int i = 2; i <= 7; i++) {
            char c = id.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                specialCount++;
            }
        }
        return specialCount >= 2;
    }

    // ----- Condition 2: address -----
    private static boolean isValidAddress(String address) {
        if (address == null) {
            return false;
        }

        // Split by '|'
        String[] parts = address.split("\\|");
        if (parts.length != 5) {
            return false;
        }

        String streetNumber = parts[0];
        String street       = parts[1];
        String city         = parts[2];
        String state        = parts[3];
        String country      = parts[4];

        // street number must be all digits
        if (streetNumber.isEmpty()) {
            return false;
        }
        for (int i = 0; i < streetNumber.length(); i++) {
            if (!Character.isDigit(streetNumber.charAt(i))) {
                return false;
            }
        }

        // state must be exactly "Victoria"
        if (!"Victoria".equals(state)) {
            return false;
        }

        // you can also require non-empty street, city, country
        if (street.isEmpty() || city.isEmpty() || country.isEmpty()) {
            return false;
        }

        return true;
    }

    // ----- Condition 3: birthdate -----
    private static boolean isValidBirthdate(String date) {
        if (date == null) {
            return false;
        }

        // Expected format: DD-MM-YYYY (length 10)
        if (date.length() != 10) {
            return false;
        }

        // Check positions 2 and 5 are '-'
        if (date.charAt(2) != '-' || date.charAt(5) != '-') {
            return false;
        }

        String dayStr   = date.substring(0, 2);
        String monthStr = date.substring(3, 5);
        String yearStr  = date.substring(6, 10);

        // Check all are digits
        if (!isAllDigits(dayStr) || !isAllDigits(monthStr) || !isAllDigits(yearStr)) {
            return false;
        }

        int day   = Integer.parseInt(dayStr);
        int month = Integer.parseInt(monthStr);
        int year  = Integer.parseInt(yearStr);

        // Simple range checks
        if (year < 1900 || year > 2100) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }

        int maxDay = daysInMonth(month, year);
        if (day < 1 || day > maxDay) {
            return false;
        }

        return true;
    }

    private static boolean isAllDigits(String s) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int daysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                // leap year check (very simple)
                boolean leap = (year % 400 == 0) ||
                               (year % 4 == 0 && year % 100 != 0); [web:36]
                return leap ? 29 : 28;
            default:
                return 0;
        }
    }
}


}

