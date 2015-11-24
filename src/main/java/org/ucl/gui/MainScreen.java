package org.ucl.gui;

import java.awt.Color;
import java.awt.Event;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * Creates the main screen gui, initializing the various members, such as the menu bar and tabbed 
 * screen
 */
@SuppressWarnings("serial")
public class MainScreen extends JPanel {
    private static final Color LIGHT_BLUE = new Color(102, 178, 255);

    public MainScreen() {
	setLayout(null);
		
	JMenuBar menuBar = createMenu();
	this.add(menuBar);

	JPanel patientData = createPatientDataArea();
	this.add(patientData);
    }

    private JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        MenuListener l = new MenuListener();
        JMenuItem menuItem;
        mb.setBounds(0, 0, 1200, 30);

	JMenu mnFile = new JMenu("File");
	mnFile.setMnemonic(KeyEvent.VK_F);
        /* save */
        menuItem = mnFile.add(new JMenuItem("Save", 's'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        menuItem.addActionListener(l);
	/* import database from file */
        menuItem = mnFile.add(new JMenuItem("Import", 'i'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK));
        menuItem.addActionListener(l);
        /* exit the program */
        menuItem = mnFile.add(new JMenuItem("Exit", 'x'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /* save function should be called here */
                int reply = confirmationDialog();
                if (reply == JOptionPane.YES_OPTION) {
                    System.out.println("Exiting program");
                    System.exit(0);
                }
            }
        });
	mb.add(mnFile);


        JMenu mnEdit = new JMenu("Edit");
        mnEdit.setMnemonic(KeyEvent.VK_E);

        /* menu items for the Edit menu */
        menuItem = mnEdit.add(new JMenuItem("Cut", 't'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Event.CTRL_MASK));
        menuItem.addActionListener(l);
        menuItem = mnEdit.add(new JMenuItem("Copy", 'c'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        menuItem.addActionListener(l);
        menuItem = mnEdit.add(new JMenuItem("Paste", 'p'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
        mb.add(mnEdit);

        JMenu mnAbout = new JMenu("About");
        mnAbout.setMnemonic(KeyEvent.VK_A);
        mb.add(mnAbout);

	JMenu mnHelp = new JMenu("Help");
	mnHelp.setMnemonic(KeyEvent.VK_H);
        mb.add(mnHelp);

        return mb;
    }

    private class MenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }

    private int confirmationDialog() {
        int reply = JOptionPane.NO_OPTION;
        try {
            reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave?", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        } catch (HeadlessException he) {
            System.out.println(he.getMessage());            
        }
        return reply;
    }

    private JPanel createPatientDataArea() {
        JPanel area = new JPanel();
        area.setBounds(0, 30, 1200, 330);
        area.setLayout(null);
        area.setBackground(LIGHT_BLUE);

        JLabel patientID = new JLabel();
        patientID.setBounds(10, 40, 100, 25);
        patientID.setText("Patient ID");
        area.add(patientID);

        JLabel lastName = new JLabel();
        lastName.setBounds(10, 70, 100, 25);
        lastName.setText("Last Name");
        area.add(lastName);

        JLabel firstNames = new JLabel();
        firstNames.setBounds(10, 100, 100, 25);
        firstNames.setText("First Name(s)");
        area.add(firstNames);

        JLabel dob = new JLabel();
        dob.setBounds(10, 130, 100, 25);
        dob.setText("Date of Birth");
        area.add(dob);

        JLabel address = new JLabel();
        address.setBounds(10, 160, 100, 25);
        address.setText("Address");
        area.add(address);

        JTextField patientIDField = new JTextField();
        patientIDField.setBounds(110, 40, 150, 25);
        area.add(patientIDField);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(110, 70, 150, 25);
        area.add(patientIDField);

        JTextField firstNamesField = new JTextField();
        firstNamesField.setBounds(110, 100, 150, 25);
        area.add(firstNamesField);

        JTextField dobField = new JTextField();
        dobField.setBounds(110, 130, 150, 25);
        area.add(dobField);

        JTextField addressField = new JTextField();
        addressField.setBounds(110, 160, 150, 25);
        area.add(addressField);

        return area;
    }
}
