package org.ucl.medicaldb;

/**
 * getters and setters for creating a new Patient creates and object of type
 * Patient
 */

public class Patient {
	final static PatientHandler checker = new PatientHandler();
	private String firstName = "";
	private String lastName = "";
	private String title = "";
	private String sex = "";
	/*
	 * this seems strange, but it's the easiest way to load a blank patient in
	 * DatabaseEditor as it is expecting to split() a DOB[] on the symbol "/"
	 */
	private String DOB = " / / ";
	private String address = "";
	private String condition = "";
	private String nextAppointment = "";
	private String comments = "";
	private String patientID = "";
	private String uri = "";
	private String profilePhoto = "";
	private String medPhotos = "";

	/* can't cast from char to String, so this is a workaround */
	private static final String DELIM = Database.DELIM + "";

	public void setFirstName(String firstName) {
		if (checker.completedObligatoryField(firstName) && checker.isValid(firstName, "name")) {
			this.firstName = firstName.trim();
		} else {
			Main.medDB.errorMessages("Incorrect name format");
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		if (checker.completedObligatoryField(lastName) && checker.isValid(lastName.trim(), "name")) {
			this.lastName = lastName;
		} else {
			Main.medDB.errorMessages("Incorrect last name form");
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setTitle(String title) {
		if (title.equals("-")) {
			Main.medDB.errorMessages("Please choose title");
		} else
			this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setSex(String sex) {
	    if (sex.equals("-")) {
	        Main.medDB.errorMessages("Please choose title");
	    } else {
		this.sex = sex;
            }
	}

	public String getSex() {
		return sex;
	}

	public void setPatientID(String patientID) {
		if (checker.completedObligatoryField(patientID) && checker.isValid(patientID)
				&& checker.isUniqueID(patientID)) {
			this.patientID = patientID;
		} else {
		    Main.medDB.errorMessages("Incorrect Patient ID");
		}
	}

	public String getPatientID() {
		return patientID;
	}

	public void setDOB(String DOB) {
		if (checker.completedObligatoryField(DOB)) {
			this.DOB = DOB;
		} else {
		    Main.medDB.errorMessages("DOB missing");
		}
	}

	public String getDOB() {
		return DOB;
	}

	public void setAddress(String address) {
		if (checker.completedObligatoryField(address) && checker.hasValidPostCode(address)) {
			this.address = address;
		} else {
		    Main.medDB.errorMessages("Address missing");
		}
	}

	public String getAddress() {
		return address;
	}

	public void setCondition(String condition) {
		if (checker.completedObligatoryField(condition)) {
			this.condition = condition;
		} else {
		    Main.medDB.errorMessages("Medical condition missing");
		}
	}

	public String getCondition() {
		return condition;
	}

	public void setNextAppointment(String nextAppointment) {
		/*
		 * there might not be a next appointment, so this is not an obligatory
		 * field; it sets a default of "not booked"
		 */
		this.nextAppointment = nextAppointment;
	}

	public String getNextAppointment() {
		return nextAppointment;
	}

	public void setComments(String comments) {
		/* not an obligatory field. */
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public void setURI(String uri) {
		if (checker.isValidURI(uri)) {
		    this.uri = uri;
                } else {
                    Main.medDB.errorMessages("Invalid uri");
                }
	}

	public String getURI() {
		return uri;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setMedPhotos(String medPhotos) {
		this.medPhotos = medPhotos;
	}

	public String getMedPhotos() {
		return medPhotos;
	}

	/**
	 * returns a CSV string of the patient object. Used for writing to the
	 * db.txt file.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		try {
			return String.format(this.getFirstName() + DELIM + this.getLastName() + DELIM + this.getPatientID() + DELIM
					+ this.getTitle() + DELIM + this.getSex() + DELIM + this.getDOB() + DELIM + this.getAddress()
					+ DELIM + this.getCondition() + DELIM + this.getNextAppointment() + DELIM + this.getComments()
					+ DELIM + this.getURI() + DELIM + this.getProfilePhoto() + DELIM + this.getMedPhotos() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * the program should never reach this point, as the previous return
		 * will either be successful or result in an exception
		 */
		return "";
	}
}
