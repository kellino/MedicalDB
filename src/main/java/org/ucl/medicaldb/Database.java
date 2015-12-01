package org.ucl.medicaldb;

import com.opencsv.CSVReader;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ucl.medicaldb.Patient;

public class Database {
	private static final Logger log = Logger.getLogger(Class.class.getName());
	private static final String FILELOCATION = "/home/david/Programming/Java/medicaldb/db.txt";
	protected static ArrayList<Patient> currentPatients = new ArrayList<Patient>();
	public static Set<String> idNumbers = new HashSet<String>();
	private static CSVReader reader;
	private static BufferedWriter writer;
	private static int setterCount;
	static final char DELIM = '|';
	public static String[] days;
	public static String[] months;
	public static String[] years;

	public Database() {
		try {
			File db = new File(FILELOCATION);
			setterCount = getPatientMethods();
			if (!db.exists()) {
				log.log(Level.INFO, "creating new database file");
				db.createNewFile();
			} else
				loadDBfromFile();
			log.log(Level.INFO, "database loaded successfully");
		} catch (IOException e) {
			log.log(Level.SEVERE, "unable to initialize database");
		} finally {
			days = initializeDays();
			months = initializeMonths();
			years = initializeYears();
		}
	}

	private String[] initializeDays() {
		String[] days = new String[31];
		for (int i = 0; i < days.length; i++) {
			days[i] = String.valueOf(i + 1);
		}
		return days;
	}

	private String[] initializeMonths() {
		String[] months = new String[12];
		for (int i = 0; i < months.length; i++) {
			months[i] = String.valueOf(i + 1);
		}
		return months;
	}

	private String[] initializeYears() {
		String[] years = new String[100];
		for (int i = years.length - 1; i > 0; i--) {
			years[i] = String.valueOf(2016 - i);
		}
		return years;
	}

	public void dumpDBtoFile() {
		writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(FILELOCATION));
			for (Patient patient : currentPatients) {
				writer.write(patient.toString());
			}
		} catch (FileNotFoundException fnf) {
			log.log(Level.SEVERE, fnf.getMessage());
		} catch (IOException ioe) {
			log.log(Level.WARNING, ioe.getMessage());
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException ioe) {
				log.log(Level.SEVERE, ioe.getMessage());
			}
		}
	}

	/**
	 * loads a database file into an ArrayList<Patient> of current patients.
	 * @throws IOException,
	 *             IndexOutOfBounds, Exception
	 * @return ArrayList<Patient>
	 */
	void loadDBfromFile() {
		reader = null;
		try {
			reader = new CSVReader(new FileReader(FILELOCATION), DELIM);
			String[] patient;
			while ((patient = reader.readNext()) != null) {
				Patient p = arrayToPatient(patient);
				populateIdSet(p.getPatientID());
				currentPatients.add(p);
			}
		} catch (IOException ioe) {
			log.log(Level.SEVERE, ioe.getMessage());
		} catch (IndexOutOfBoundsException ibe) {
			log.log(Level.SEVERE, ibe.getMessage());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ioe) {
				log.log(Level.SEVERE, ioe.getMessage());
			}
		}
	}

	/**
	 * Uses java reflection to count the number of Patient setter methods
	 * @return int
	 */
	private static int getPatientMethods() {
		setterCount = 0;
		Class<Patient> patient = Patient.class;
		Method[] allMethods = patient.getMethods();
		for (Method method : allMethods) {
			if (method.getName().startsWith("set")) {
				setterCount++;
			}
		}
		return setterCount;
	}

	/**
	 * converts the CSV string[] to a Patient object
	 * 
	 * @throws IndexOutOfBoundsException
	 * @param String[]
	 * @return Patient
	 */
	Patient arrayToPatient(String[] array) throws IndexOutOfBoundsException {
		Patient temp = new Patient();
		int i = 0;
		temp.setFirstName(array[i++]);
		temp.setLastName(array[i++]);
		temp.setPatientID(array[i++]);
		temp.setTitle(array[i++]);
		temp.setSex(array[i++]);
		temp.setDOB(array[i++]);
		temp.setAddress(array[i++]);
		temp.setCondition(array[i++]);
		temp.setNextAppointment(array[i++]);
		temp.setComments(array[i++]);
		temp.setURI(array[i++]);
		temp.setProfilePhoto(array[i++]);
		temp.setMedPhotos(array[i++]);

		if (i != setterCount) {
			log.log(Level.SEVERE, "mismatch between setter methods and arrayToPatient()");
		}
		return temp;
	}

	/**
	 * creates a java set of id numbers as read from the database file
	 * 
	 * @param String
	 *            id
	 */
	private void populateIdSet(String id) {
		try {
			idNumbers.add(id);
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
		}
	}

	public void removePatient(Patient toRemove) {
		currentPatients.remove(toRemove);
	}

	/** method to add a new patient to the ArrayList of current patients, and
	 * appends this to the db.txt
	 */
	public void appendPatientToDB(Patient newPatient) {
	        currentPatients.add(newPatient);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("db.txt", true));
			bw.write(newPatient.toString());
			bw.flush();
		} catch (IOException ioe) {
			log.log(Level.SEVERE, "unable to append patient to file", ioe.getMessage());
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException ioe) {
			}
		}
	}

	public ArrayList<Patient> searchPatients(String searchTxt) {
		ArrayList<Patient> resultList = new ArrayList<Patient>();
		for (int i = 0; i < currentPatients.size(); i++) {
			if (currentPatients.get(i).toString().toLowerCase().contains(searchTxt.toLowerCase())) {
			    resultList.add(currentPatients.get(i));
			}
		}
		return resultList;
	}

	/** populates an ArrayList of error messages from the patient setter methods
	 * which is feeds to a JOptionPane in the DatabaseEditor class
	 */
	public ArrayList<String> errorMessages(String error) {
	    ArrayList<String> errors = new ArrayList<String>();
	    errors.add(error);
	    return errors;
	}
}
