package org.ucl.medicaldb;

/**
 * getters and setters for creating a new Patient creates and object of type
 * Patient
 */

public class Patient {
	final static PatientHandler checker = new PatientHandler();
	/*
	 * set everything to empty so we can load a blank patient into the
	 * DatabaseEditor
	 */
	private String firstName = "";
	private String lastName = "";
	private String title = "";
	private String sex = "";
	// this seems strange, but it's the easiest way to load a blank patient in
	// the DatabaseEditor, as it is expecting to split() a DOB[] on the symbol
	// "/"
	private String DOB = " / / ";
	private String address = "";
	private String condition = "";
	private String nextAppointment = "";
	private String comments = "";
	private String patientID = "";
	private String uri = "";
	/* set the placeholder image as a default */
	private String profilePhoto = "/home/david/Programming/Java/medicaldb/src/main/resources/placeholder.png";
	private String medPhotos = "";

	/* can't cast from char to String, so this is a workaround */
	private static final String DELIM = Database.DELIM + "";

	public void setFirstName(String firstName) {
		if (checker.completedObligatoryField(firstName)) {
			if (checker.isValid(firstName, "name")) {
				this.firstName = firstName.trim();
			} else {
				checker.setErrors("<html>Invalid <font color=red>first name</font></html>");
			}
		} else {
			checker.setErrors("<html>Missing <font color=red>first name</font></html>");
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		if (checker.completedObligatoryField(lastName)) {
			if (checker.isValid(lastName, "name")) {
				this.lastName = lastName.trim();
			} else {
				checker.setErrors("<html>Invalid <font color=red>last name</font></html>");
			}
		} else {
			checker.setErrors("<html>Missing <font color=red>last name</font></html>");
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setTitle(String title) {
		if (title.equals("-")) {
			checker.setErrors("<html>Please choose <font color=red>title</font></html>");
		} else this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setSex(String sex) {
		if (sex.equals("-")) {
			checker.setErrors("<html><font color=red>Sex</font> missing</html>");
		} else if (sex.equals("Female") && this.title.equals("Mr")) {
			checker.setErrors(
					"<html>Gender mismatch on<font color=red> title</font> and <font color=red>sex</font></html>");
		} else
			this.sex = sex;
	}

	public String getSex() {
		return sex;
	}

	public void setPatientID(String patientID) {
		if (checker.completedObligatoryField(patientID)) {
			if (checker.isValid(patientID)) {
				this.patientID = patientID;
			} else {
				checker.setErrors("<html>Invalid <font color=red>patient id</font></html>");
			}
		} else {
			checker.setErrors("<html>Missing <font color=red>patient id</font></html>");
		}
	}

	public String getPatientID() {
		return patientID;
	}

	public void setDOB(String DOB) {
		if (checker.completedObligatoryField(DOB)) {
			if (checker.isDateinFuture(DOB)) {
			    checker.setErrors("<html>Patient <b>cannot</b> be born in the future</html>");
			} else {
			    this.DOB = DOB;
			}
		} else {
			checker.setErrors("<html>Missing <font color=red>date of birth</font></html>");
		}
	}

	public String getDOB() {
		return DOB;
	}

	public void setAddress(String address) {
		if (checker.completedObligatoryField(address) && checker.hasValidPostCode(address)) {
			this.address = address;
		}
	}

	public String getAddress() {
		return address;
	}

	public void setCondition(String condition) {
		if (checker.completedObligatoryField(condition)) {
			this.condition = condition;
		}
	}

	public String getCondition() {
		return condition;
	}

	public void setNextAppointment(String nextAppointment) {
		/*
		 * there might not be a next appointment, so this is not an obligatory
		 * field
		 */
		if (nextAppointment.equals("") && this.nextAppointment.equals("")) {
			this.nextAppointment = "";
		} else if (checker.isDateinFuture(nextAppointment)) {
			this.nextAppointment = nextAppointment;
		} else {
			checker.setErrors("The next appointment cannot be in the past");
		}
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
