package org.ucl.medicaldb;

import java.util.regex.Pattern;

public final class PatientHandler {

    boolean isValid(String input, String type) {
        Pattern pattern;

        if (type == "name") {
	    pattern = Pattern.compile("^[a-zA-Z]+");
        } else if (type == "last name") {
	    pattern = Pattern.compile("^[a-zA-Z]+[ '-]?[a-zA-Z]+$");
        } else {
            pattern = Pattern.compile("[a-z][a-z][0-9]+");
        }

        try {
            if (!pattern.matcher(input).matches()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(input + " is not valid input.");
        }
        return true;
    }

    String normalizeNameCaps(String string) {
	return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    boolean isValidDate(String DOB) {
	return true;
    }

    boolean isUniqueID(String id) {
        return true;
    }
}
