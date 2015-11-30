package org.ucl.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import org.jdatepicker.impl.*;
import org.ucl.medicaldb.*;
import org.ucl.medicaldb.Main;

/**
 * main dialog for editing and adding patients to the database. It is called in
 * the form of a JOptionPane from the MainScreen class
 */
@SuppressWarnings("serial")
public class DatabaseEditor extends JPanel {
	private static final int width = 600, height = 600;
	private static final Dimension screen = new Dimension(width, height);
	private static final int boxHeight = height / 20;
	private static final int unit = width / 60;
	private Patient patient;
	private JTextField[] inputFields;
	private JComboBox<String> titleMenu;
	private JComboBox<String> genderMenu;
        private JComboBox<String> day;
        private JComboBox<String> month;
        private JComboBox<String> year;
        private JTextArea commentArea;

	/**
	 * if this constructor is called by the MainScreen adder button, it loads
	 * the various text fields with emtpy strings. If it is loaded from the edit
	 * button, it loads the current patient into the editable areas for
	 * alterations
	 */
	public DatabaseEditor(Patient patient) {
		/**
		 * the DatabaseEditor window is not resizeable, so it's possible to
		 * safely use the null layout here
		 */
		this.patient = patient;
		setLayout(null);
		setPreferredSize(screen);
		setBackground(new Color(200, 200, 100, 200));
		setBorder(BorderFactory.createEtchedBorder(1));
		createFieldNames();
		createAndPopulateFields();
	}

	/** set titles for each text field */
	private void createFieldNames() {
		JLabel[] titleContainers = new JLabel[MainScreen.fields.length];
		int i;
		for (i = 0; i < MainScreen.fields.length; i++) {
			titleContainers[i] = new JLabel();
			switch (i) {
			case 0: /* patient id */
				titleContainers[i].setBounds(unit, unit * 4, 150, boxHeight);
				break;
			case 1: /* title */
				titleContainers[i].setBounds(unit * 20, unit * 4, 150, boxHeight);
				break;
			case 2: /* sex */
				titleContainers[i].setBounds(unit * 31, unit * 4, 150, boxHeight);
				break;
			case 3: /* last name */
				titleContainers[i].setBounds(unit, unit * 8, 150, boxHeight);
				break;
			case 4: /* first name */
				titleContainers[i].setBounds(unit, unit * 12, 150, boxHeight);
				break;
			case 5: /* date of birth */
				titleContainers[i].setBounds(unit, unit * 16, 150, boxHeight);
				break;
			case 6: /* day */
				titleContainers[i].setBounds(unit * 11, unit * 16, 100, boxHeight);
				break;
			case 7: /* month */
				titleContainers[i].setBounds(unit * 20, unit * 16, 100, boxHeight);
				break;
			case 8: /* year */
				titleContainers[i].setBounds(unit * 30, unit * 16, 100, boxHeight);
				break;
			case 9: /* condition */
				titleContainers[i].setBounds(unit, unit * 20, unit * 10, boxHeight);
				break;
			case unit: /* address */
				titleContainers[i].setBounds(unit, unit * 24, unit * 10, boxHeight);
				break;
			case 11: /* next appointment */
				titleContainers[i].setBounds(unit, unit * 28, unit * 20, boxHeight);
				break;
			case 12: /* url */
				titleContainers[i].setBounds(unit, unit * 32, unit * 10, boxHeight);
				break;
			case 13: /* image adder */
				titleContainers[i].setBounds(unit, unit * 36, unit * 10, boxHeight);
				break;
			case 14: /* comments */
				titleContainers[i].setBounds(unit, unit * 40, unit * 10, boxHeight);
				break;
			default:
				/* we should not reach this point */
				break;
			}
			titleContainers[i].setText(MainScreen.fields[i]);
			add(titleContainers[i]);
		}
	}

	/** draw editable text fields, JComboBoxes, JDatePicker, and JFileChooser */
	private void createAndPopulateFields() {
		int i;
		inputFields = new JTextField[MainScreen.fields.length];
		for (i = 0; i < MainScreen.fields.length; i++) {
			inputFields[i] = new JTextField();
			switch (i) {
			case 0: /* patient id */
				inputFields[i].setBounds(unit * 11, unit * 4, unit * 8, boxHeight);
				inputFields[i].setText(patient.getPatientID());
				break;
			case 1: /* title and gender */
				titleMenu = createComboBox(MainScreen.patientData[1], unit * 23, unit * 4, unit * 7,
						boxHeight);
				titleMenu.setSelectedItem(patient.getTitle());
				genderMenu = createComboBox(MainScreen.patientData[2], unit * 34, unit * 4, unit * 8,
						boxHeight);
				genderMenu.setSelectedItem(patient.getSex());
				add(titleMenu);
				add(genderMenu);
				break;
			case 3: /* last name */
				inputFields[i].setBounds(unit * 11, unit * 8, unit * 25, boxHeight);
				inputFields[i].setText(patient.getLastName());
				break;
			case 4: /* first name(s) */
				inputFields[i].setBounds(unit * 11, unit * 12, unit * 25, boxHeight);
				inputFields[i].setText(patient.getFirstName());
				break;
			case 6: /* dd, mm, YY */
				String[] dob = patient.getDOB().split("/");
				day = createComboBox(MainScreen.dateFormat[0], unit * 13, unit * 16, unit * 6,
						boxHeight);
				day.setSelectedItem(dob[0]);
				month = createComboBox(MainScreen.dateFormat[1], unit * 23, unit * 16, unit * 6,
						boxHeight);
				month.setSelectedItem(dob[1]);
				year = createComboBox(MainScreen.dateFormat[2], unit * 32, unit * 16, unit * 8,
						boxHeight);
				year.setSelectedItem(dob[2]);
				add(day);
				add(month);
				add(year);
				break;
			case 9: /* condition(s) */
				inputFields[i].setBounds(unit * 11, unit * 20, unit * 35, boxHeight);
				inputFields[i].setText(patient.getCondition());
				break;
			case 10: /* address */
				inputFields[i].setBounds(unit * 11, unit * 24, unit * 45, boxHeight);
				inputFields[i].setText(patient.getAddress());
				break;
			case 11: /* next appointment */
				JDatePickerImpl datePicker = drawDatePicker();
				datePicker.setBounds(unit * 11, unit * 28, unit * 20, boxHeight);
				add(datePicker);
				break;
			case 12: /* url */
				inputFields[i].setBounds(unit * 11, unit * 32, unit * 35, boxHeight);
				inputFields[i].setText(patient.getURI());
				break;
			case 13: /* image */
				/* read the file location as a string into this text area */
				inputFields[i].setBounds(unit * 25, unit * 36, unit * 31, boxHeight);
				inputFields[i].setText(patient.getProfilePhoto());
				JButton addPhoto = new JButton();
				addPhoto.setBounds(unit * 11, unit * 36, unit * 10, boxHeight);
				addPhoto.setText("<html><b>Choose</b></html>");
				addPhoto.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String fileString = createPhotoChooser();
						inputFields[13].setText(fileString);
					}
				});
				add(addPhoto);
				break;
			case 14: /* comments */
				commentArea = new JTextArea();
				commentArea.setBounds(unit * 11, unit * 40, unit * 35, boxHeight * 5);
				commentArea.setLineWrap(true);
				commentArea.setWrapStyleWord(true);
				commentArea.setText(patient.getComments());
				add(commentArea);
				break;
			default:
				/* case 5: date of birth */
				break;
			}
			add(inputFields[i]);
		}
	}
        
        /** scrape the various textfields and other boxes for their data and use this to edit the
         * patient object that has been sent to the DatabaseEditor
         */
        protected void textFieldsToPatient() {
            patient.setPatientID(inputFields[0].getText());
            patient.setTitle(titleMenu.getSelectedItem().toString());
            patient.setSex(genderMenu.getSelectedItem().toString());
            patient.setLastName(inputFields[3].getText());
            patient.setFirstName(inputFields[4].getText());
            String dob;
            dob = (day.getSelectedItem() + "/" + month.getSelectedItem() + "/" + year.getSelectedItem());
            patient.setDOB(dob);
            patient.setCondition(inputFields[9].getText());
            patient.setAddress(inputFields[10].getText());
            // case 11 needs to be the datepicker
            patient.setURI(inputFields[12].getText());
            patient.setProfilePhoto(inputFields[13].getText());
            patient.setComments(commentArea.getText());
        }

	/**
	 * launches a JFileChooser component for loading an image file to the
	 * database */
	private String createPhotoChooser() {
		JFileChooser imageChooser = new JFileChooser();
		imageChooser.showOpenDialog(this);
		File file = imageChooser.getSelectedFile();
		return file.toString();
	}

	/** helper method to set up JComboBoxes */
	private JComboBox<String> createComboBox(String[] text, int x, int y, int width, int height) {
		JComboBox<String> newBox = new JComboBox<String>(text);
		newBox.setBounds(x, y, width, height);
		return newBox;
	}

	/** helper method to set up JDatePicker */
	private JDatePickerImpl drawDatePicker() {
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePanel.setBounds(unit, unit * 40, unit * 10, unit * 10);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		return datePicker;
	}

        protected void appendPatient(Patient p) {
            Main.medDB.appendPatientToDB(p);
        }

	/**
	 * the following code is from a stackoverflow <i>tutorial</i> on
	 * JDatePicker. Thanks to theMadProgrammer
	 */
	public class DateLabelFormatter extends AbstractFormatter {
		private String datePattern = "dd/MM/yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}
	}
}
