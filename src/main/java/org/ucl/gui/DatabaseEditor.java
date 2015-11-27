package org.ucl.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** main dialog for editing and adding patients to the database. It is called in the 
 * form of a JOptionPane from the MainScreen class
 */
@SuppressWarnings("serial")
public class DatabaseEditor extends JPanel {

    /** constructor for adding a new patient to the database */
    public DatabaseEditor() {
        setLayout(null); 
        setPreferredSize(new Dimension(600, 600));
        setBackground(new Color(100, 100, 100, 200));
        setBorder(BorderFactory.createEtchedBorder(1));
        createFieldNames();
        createTextFields();
    } 

    /** constructor for editing an existing patient. Takes an existing id number and
     * loads the appropriate Patient object into the fields for editing.
     * @param String idNumber
     */
    public DatabaseEditor(String idNumber) {
        setLayout(null); 
        setPreferredSize(new Dimension(600, 600));
        createFieldNames();
        createTextFields();
    }

    private void createFieldNames() {
        JLabel[] titleContainers = new JLabel[MainScreen.fields.length];
	for (int i = 0; i < MainScreen.fields.length; i++) {
	    int y_shift = (i - 2) * 40;
	    titleContainers[i] = new JLabel();
	    switch (i) {
		case 0: /* patient id */
		    titleContainers[i].setBounds(10, 40, 150, 30);
		    titleContainers[i].setText(MainScreen.fields[i]);
		    break;
		case 1: /* title */
		    titleContainers[i].setBounds(110, 40, 100, 30);
		    titleContainers[i].setText(MainScreen.fields[i]);
		    break;
		case 2: /* sex */
		    titleContainers[i].setBounds(210, 40, 100, 30);
		    titleContainers[i].setText(MainScreen.fields[i]);
		    break;
		case 6:
		    titleContainers[i].setBounds(100, 4 - +y_shift, 100, 30);
		    break;
		case 7:
		case 8:
		    break;
		default:
		    titleContainers[i].setBounds(50, 40 + y_shift, 150, 30);
		    titleContainers[i].setText(MainScreen.fields[i]);
		    break;
		}
	add(titleContainers[i]);
	}
    }

    private void createTextFields() {
	JTextField[] inputFields = new JTextField[MainScreen.fields.length];
	    for (int i = 0; i < MainScreen.fields.length; i++) {
		int y_shift = (i - 2) * 40;
		inputFields[i] = new JTextField();
		switch (i) {
		    case 0: /* patient id */
			createComboBoxArray(MainScreen.patientData, 40);
			break;
		    case 1: // these positions are filled with the
		    case 2: // createComboBoxArray() function
			break;
		    case 5:
			createComboBoxArray(MainScreen.dateFormat, 160);
			break;
		    case 6: /* address */
			inputFields[i].setBounds(150, 40 + y_shift, WIDTH / 2, 30);
			break;
		    default:
			inputFields[i].setBounds(150, 40 + y_shift, 300, 30);
		}
	    add(inputFields[i]);
	}
    }

    private void createComboBoxArray(String[][] list, int y) {
        ArrayList<JComboBox<String>> jBoxArray = new ArrayList<JComboBox<String>>();
	for (int i = 0; i < 3; i++) {
            jBoxArray.add(new JComboBox<String>(list[i]));
	    jBoxArray.get(i).setBounds(50 + (i * 30), y, 50, 30);
	    add(jBoxArray.get(i));
	}
    }
}
