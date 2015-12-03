package org.ucl.medicaldb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public final class PatientHandler {
	/* logger */
	private static final Logger log = Logger.getLogger(Class.class.getName());
	public static ArrayList<String> errors = new ArrayList<String>();

	/**
	 * checks that a compulsory field contains some text, not just whitespace
	 * @param String input
	 * @return boolean
	 */

	boolean completedObligatoryField(String input) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		return true;
	}

	/**
	 * uses a simple regex to ensure that the patient id number is in the
	 * correct form
	 * 
	 * @param String
	 * @return boolean
	 */
	boolean isValid(String input) {
		Pattern pattern = Pattern.compile("[a-z]{2}[0-9]+");
		if (pattern.matcher(input).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * uses a simple regex to check for "reasonable" name forms. It excludes
	 * some symbols which do not appear in any names, such as "?", but makes no
	 * attempt at more complex verification, nor does it check for
	 * capitalization, which can very greatly in usage.
	 * 
	 * @param String
	 * @return boolean
	 */
	boolean isValid(String input, String type) {
	        /* very simple regex, but names are extremely variable, so anything more
	         * sophisticated might exclude a legitimate, but unusual, name
	         */
		Pattern pattern = Pattern.compile("[0-9<>!\"$%\\+&][{}]");
		try {
			if (pattern.matcher(input).matches()) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			log.log(Level.INFO, "incorrect name string entered by user");
			return false;
		}
		return true;
	}

	boolean isValidDate(String DOB) {
		Pattern pattern = Pattern.compile("[1-9][0-9]?/[1-9][0-2]?/[1-9][0-9]{3}");
		try {
			if (pattern.matcher(DOB).matches()) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			log.log(Level.INFO, "incorrect date format");
			return false;
		}

		String[] tempDate = DOB.split("/");
		if (tempDate[1].equals("4") || tempDate[1].equals("6") 
		        || tempDate[1].equals("9") || tempDate[1].equals("11")) {
		    if (Integer.parseInt(tempDate[0]) > 30) {
		        return false;
		    } else if (tempDate[1].equals("2")) {
		        // this is not smart enough to test for a leap year, so let's set the limit
		        // to 29
		        if (Integer.parseInt(tempDate[0]) > 29) {
		            return false;
		        }
		    }
		}
		return true;
	}

	boolean isUniqueID(String id) {
		if (Database.idNumbers.add(id)) {
			return true;
		}
		return false;
	}

	boolean isValidURI(String uri) {
	    Pattern pattern = Pattern.compile("http[s]?://");
	    try {
			if (pattern.matcher(uri).matches()) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			log.log(Level.INFO, "incorrect uri string entered by user");
			return false;
		}
		return true;
	    }
	
	/**
	 * a valid UK Postcode has a complex formula. The regex here checks the end of the address string for a legitimate postcode formation.
	 * The description of a valid postcode was taken from https://www.mrs.org.uk/pdf/postcodeformat.pdf
	 * The regex can test if the form is correct, but not whether the postcode actually exists.
	 * @param address
	 * @return boolean
	 */
	boolean hasValidPostCode(String address) {
	    Pattern pattern = Pattern.compile(".*[A-Z&&[^QVX]][A-Z&&[^IJZ]]?[1-9][0-9]? ?[1-9][A-Z&&[^CIKMOV]]{2}");
		if (pattern.matcher(address.toUpperCase()).matches()) {
			return true;
		} else {
			log.log(Level.INFO, "incorrect postcode format");
			return false;
		}
	}

        /** if the date is before the present day, then this cannot be the next appointment. We will
         * accept next appointment as today, as it could be an emergency
         * @param date
         * @return boolean
         */
	boolean isDateinFuture(String date) {
	    String[] now = LocalDate.now().toString().split("-");
	    String[] d = date.split("/");
	    /* is the year in the past? */
	    if (Integer.parseInt(now[0]) < Integer.parseInt(d[2])) {
	        return false;
	    /* is the month past in the present year? */
	    } else if (Integer.parseInt(now[1]) < Integer.parseInt(d[1]) && Integer.parseInt(now[0]) == Integer.parseInt(d[2])) {
	        return false;           
	    /* is the day in the past? */
	    } else if (Integer.parseInt(now[2]) < Integer.parseInt(d[0])) {
	        return false;
	    } else 
	        return true;
	}
	
	/**
	 * populates an ArrayList of error messages from the patient setter methods
	 * which is feeds to a JOptionPane in the MainScreen class
	 * @param String error
	 */
	public void errorMessages(String error) {
		errors.add(error + "\n");
	}
}
