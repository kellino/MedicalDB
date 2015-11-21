package org.ucl.medicaldb;


/**
 * getters and setters for creating a new Patient
 * creates and object of type Patient
 */

public class Patient {
    final static PatientHandler checker = new PatientHandler();
    private String firstName;
    private String lastName;
    private String DOB;
    private String address;
    private String condition;
    private String nextAppointment;
    private String comments;
    private String patientID;

    public void setFirstName(String firstName) {
	if (checker.isValid(firstName.trim(), "name"))
	    this.firstName = checker.normalizeNameCaps(firstName);
    }

    public String getFirstName() { 
        return firstName; 
    }

    public void setLastName(String lastName) {
	// last names can be very complex, so we won't normalize these
	if (checker.isValid(lastName.trim(), "last name"))
	    this.lastName = lastName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setDOB(String DOB) {
	// this will be done with JcomboBoxes in the GUI
	if (checker.isValidDate(DOB))
	    this.DOB = DOB;
    }

    public String getDOB() {
	return DOB;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() { 
        return address; 
    }

    public void setCondition(String condition) { 
        this.condition = condition; 
    }

    public String getCondition() { 
        return condition; 
    }


    public void setNextAppointment(String nextAppointment) { 
        this.nextAppointment = nextAppointment; 
    }

    public String getNextAppointment() { 
        return nextAppointment; 
    }

    public void setComments(String comments) { 
        this.comments = comments; 
    }

    public String getComments() { 
        return comments; 
    }

    public void setPatientID(String patientID) { 
        // do this with hashmap
        if (checker.isValid(patientID, "id") && checker.isUniqueID(patientID)) {
            this.patientID = patientID; 
        } else {
        	System.out.println("Not a valid id");
        }
    }

    public String getPatientID() { 
        return patientID; 
    }

    public String toString() {

        try {
        return String.format(this.getPatientID() + ":" + this.getFirstName() + ":" + 
                             this.getLastName() + ":" + this.getDOB() + ":" +
                             this.getAddress() + ":" + this.getCondition() + ":" +
                             this.getNextAppointment() + ":" + this.getComments() + ":");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* the program should never reach this point, as the previous return will either be
         * successful or result in an exception
         */
        return "";
    }
}
