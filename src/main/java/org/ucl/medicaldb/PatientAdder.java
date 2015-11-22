package org.ucl.medicaldb;

import java.util.Scanner;

/**
 * helper class to add a patient to the database through a series of
 * questions. This won't be in the final program
 * @return Patient
 */

public class PatientAdder {
    private Scanner in = new Scanner(System.in);

    public Patient addPatient() {

        Patient patient = new Patient();

        System.out.print("Enter patient first name: ");
        patient.setFirstName(in.nextLine());

        System.out.print("Enter patient last name: ");
        patient.setLastName(in.nextLine());

        System.out.print("Patient ID: ");
        patient.setPatientID(in.nextLine());

        System.out.print("Date Of Birth: ");
        patient.setDOB(in.nextLine());

        System.out.print("Address: ");
        patient.setAddress(in.nextLine());

        System.out.print("Medical condition: ");
        patient.setCondition(in.nextLine());

        System.out.print("Next Appointment: ");
        patient.setNextAppointment(in.nextLine());

        System.out.print("Comments: ");
        patient.setComments(in.nextLine());

        return patient;
    }

}
