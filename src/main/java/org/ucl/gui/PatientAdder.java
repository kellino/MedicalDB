package org.ucl.gui;

import javax.swing.*;

import org.ucl.medicaldb.Database;

public class PatientAdder {
    JPanel panel;
    //private static String[] currentIds = Database.idNumbers.stream().toArray(String[]::new); // this piece of code was inspired by a comment on a stackoverflow question
    //private static final String[] fields = new String[] {"Patient ID", "Title", "Sex", "Last Name", "First Name(s)", "Date of Birth", "D", "M", "Y", "Address"};
    // static final String[][] patientData = {currentIds, {"Mr", "Mrs", "Ms", "Dr"}, {"Male", "Female"}};
    private static final String[][] dateFormat = {Database.days, Database.months, Database.years};

    public PatientAdder() {
        createAddingDialog();
    };

    private void createAddingDialog() {
        panel = new JPanel();
        JComboBox<String> day = new JComboBox<String>(dateFormat[0]);
        panel.add(day);

        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            //stud
            System.out.println("Patient added");
        } else {
            System.out.println("Patient adding cancelled");
        }
    
    }
}
