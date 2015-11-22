package org.ucl.medicaldb;

import java.util.Scanner;
import java.util.ArrayList;

public class MainTestDriver {
	static Database medDB;

    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);

        medDB = new Database();
        PatientAdder newPatient = new PatientAdder();
       
        System.out.println("Patient Medical Database");

        boolean running = true;

        do {
            System.out.print("Would you like to add a patient [y/N] => ");
            String choice = in.next().toLowerCase();

            if (choice.equals("y") || choice.equals("yes")) {
                medDB.currentPatients.add(newPatient.addPatient());
            } else if (choice.equals("n") || choice.equals("no")) {
                running = false;
            } else System.out.println("I did not recognize that option...");

        } while (running);


        medDB.dumpDBtoFile();

        showPatients(medDB.currentPatients);
        
        //medDB.removePatient(1);
        
        //showPatients(medDB.currentPatients);
        medDB.dumpDBtoFile();

        System.out.println("Goodbye");
        
        in.close();
    }

    public static void showPatients(ArrayList<Patient> patientDB) {

        for (Patient patient : medDB.currentPatients) {
            System.out.println(patient);
        }
    }
}
