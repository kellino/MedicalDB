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

    private static final String DELIM = ":";

    public void setFirstName(String firstName) {
    	if (checker.completedObligatoryField(firstName) && checker.isValid(firstName, "name")) {
    		this.firstName = firstName.trim();
    	} else System.out.println("not a name");
    }

    public String getFirstName() { 
        return firstName; 
    }

    public void setLastName(String lastName) {
	// last names can be very complex, so we won't normalize these
	if (checker.completedObligatoryField(lastName) && checker.isValid(lastName.trim(), "name"))
	    this.lastName = lastName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setPatientID(String patientID) { 
        // do this with hashmap
        if (checker.completedObligatoryField(patientID) && checker.isValid(patientID) && checker.isUniqueID(patientID)) {
            this.patientID = patientID; 
        } else {
        	System.out.println("Not a valid id");
        }
    }

    public String getPatientID() { 
        return patientID; 
    }

    public void setDOB(String DOB) {
    	if (checker.completedObligatoryField(DOB)) {
    		this.DOB = DOB;
    	}
    }

    public String getDOB() {
    	return DOB;
    }

    public void setAddress(String address) {
        if (checker.completedObligatoryField(address)) {
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
        /* there might not be a next appointment, so this is not
         * an obligatory field; it sets a default of "not booked"
         */
    	if (!checker.completedObligatoryField(nextAppointment)) {
    		this.nextAppointment = "not booked";
    	}
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

    /**
     * returns a CSV string of the patient object. Used for writing to the db.txt file.
     * @return String
     */
    @Override
    public String toString() {
        try {
            return String.format(this.getFirstName() + DELIM + this.getLastName() + DELIM +
                             this.getPatientID() + DELIM +  this.getDOB() + DELIM +
                             this.getAddress() + DELIM + this.getCondition() + DELIM +
                             this.getNextAppointment() + DELIM + this.getComments() + DELIM + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* the program should never reach this point, as the previous return will either be
         * successful or result in an exception
         */
        return "";
    }
}
