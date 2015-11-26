package org.ucl.medicaldb;

import com.opencsv.CSVReader;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.ucl.medicaldb.Patient;

public class Database {
    private static final Logger log = Logger.getLogger(Class.class.getName());

    private static final String FILELOCATION = "/home/david/Programming/Java/medicaldb/db.txt";
    protected static ArrayList<Patient> currentPatients = new ArrayList<Patient>();
    public static Set<String> idNumbers = new HashSet<String>();
    private static CSVReader reader;
    private static BufferedWriter writer;
    private static int setterCount;

    private static final char DELIM = ':';

    public Database() {
        try {
            File db = new File(FILELOCATION);
            setterCount = getPatientMethods();
            if (!db.exists()) {
                log.log(Level.INFO, "creating new database file");
                db.createNewFile();
            } else loadDBfromFile();
            	   log.log(Level.INFO, "database loaded successfully");
        } catch (IOException e) {
            log.log(Level.SEVERE, "unable to initialize database");
        }
    }

    void dumpDBtoFile() {
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

    /** loads a database file into an ArrayList<Patient> of current patients.
     * @throws IOException, IndexOutOfBounds, Exception
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

    /** Uses java reflection to count the number of Patient setter methods
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
    	temp.setDOB(array[i++]);
    	temp.setAddress(array[i++]);
    	temp.setCondition(array[i++]);
    	temp.setNextAppointment(array[i++]);
    	temp.setComments(array[i++]);

    	if (i != setterCount) {
    	    log.log(Level.SEVERE, "mismatch between setter methods and arrayToPatient()");
    	}
    	return temp;
    }
    
    /** creates a java set of id numbers as read from the database file
     * @param String id
     */
    private void populateIdSet(String id) {
    	try {
    		idNumbers.add(id);
    	} catch (Exception e) {
    		log.log(Level.WARNING, e.getMessage());
    	}
    }
    
    void removePatient(int index) {
    	currentPatients.remove(index);
    };

    void loadPatient() {};

    void searchPatient(Pattern pattern) {};
}
