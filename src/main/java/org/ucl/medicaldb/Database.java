package org.ucl.medicaldb;

import com.opencsv.CSVReader;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private String fileLocation = "/home/david/Programming/Java/medicaldb/db.txt";
    private static final Logger log = Logger.getLogger(Class.class.getName());
    protected ArrayList<Patient> currentPatients = new ArrayList<Patient>();
    private static CSVReader reader;
    private static BufferedWriter writer;
    private static char delim = ':';
    private List<Method> setters;

    public Database() {
        try {
            File db = new File(fileLocation);
            getPatientMethods();
            if (!db.exists()) {
                log.log(Level.INFO, "creating new database file");
                db.createNewFile();
            } else loadDBfromFile();
        } catch (IOException e) {
            log.log(Level.SEVERE, "unable to initialize database");
        }
    }

    void dumpDBtoFile(String patient) {
        writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileLocation)); 
            writer.write(patient);
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

    void loadDBfromFile() {
        reader = null;
        try {
            reader = new CSVReader(new FileReader(fileLocation), delim);
            String[] patient;
	    while ((patient = reader.readNext()) != null) {
            Patient p = new Patient();
	    	for (int i = 0; i < patient.length - 1; i++) {
                    setters.get(i).invoke(p, patient[i]);
                }
	    	currentPatients.add(p);
            }
        } catch (IOException ioe) {
            log.log(Level.SEVERE, ioe.getMessage(), ioe);
        } catch (IllegalAccessException iae) {
            log.log(Level.SEVERE, iae.getMessage(), iae);
        } catch (InvocationTargetException ite) {
            log.log(Level.SEVERE, ite.getMessage(), ite);
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
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

    void getPatientMethods() {
        Class<Patient> patient = Patient.class;
        Method[] allMethods = patient.getMethods();
        setters = new ArrayList<Method>();
        for (Method method : allMethods) {
            if (method.getName().startsWith("set")) {
                setters.add(method);
            }
        }
    }

    void removePatient() {};

    void editPatient() {};
}
