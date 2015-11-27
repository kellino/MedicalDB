package org.ucl.gui;

import java.awt.Color;
import java.awt.Dimension;
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
    private static final int width = 600, height = 600;
    private static final Dimension screen = new Dimension(width, height);
    private static final int boxHeight = height / 20;
    private static final int widthUnit = width / 60;

    /** constructor for adding a new patient to the database */
    public DatabaseEditor() {
        setLayout(null); 
        setPreferredSize(screen);
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
	int i;
	for (i = 0; i < MainScreen.fields.length; i++) {
	    int y_shift = (i - 2) * 40;
	    titleContainers[i] = new JLabel();
	    switch (i) {
		case 0: /* patient id */
		    titleContainers[i].setBounds(widthUnit, height / 15, 150, boxHeight);
		    break;
		case 1: /* title */
		    titleContainers[i].setBounds(widthUnit * 17, height / 15, 150, boxHeight);
		    break;
		case 2: /* sex */
		    titleContainers[i].setBounds(widthUnit * 28, height / 15, 150, boxHeight);
		    break;
		case 6: /* day */
		    titleContainers[i].setBounds(widthUnit * 10, 160, 100, boxHeight);
		    break;
		case 7: /* month */
		    titleContainers[i].setBounds(widthUnit * 18, 160, 100, boxHeight);
		    break;
		case 8: /* year */
		    titleContainers[i].setBounds(widthUnit * 27, 160, 100, boxHeight);
		    break;
		default:
		    titleContainers[i].setBounds(10, 40 + y_shift, 150, boxHeight);
		    break;
		}
	titleContainers[i].setText(MainScreen.fields[i]);
	add(titleContainers[i]);
	}
    }

    private void createTextFields() {
	JTextField[] inputFields = new JTextField[MainScreen.fields.length];
	    for (int i = 0; i < MainScreen.fields.length; i++) {
		int y_shift = (i - 2) * 40;
		inputFields[i] = new JTextField();
		switch (i) {
		    case 0: /* patient id, title, gender */
			//createComboBoxArray(MainScreen.patientData, 80, 40);
			JComboBox<String> patientID = createComboBox(MainScreen.patientData[0], 80, 40, 80, boxHeight);
			JComboBox<String> titleMenu = createComboBox(MainScreen.patientData[1], 200, 40, 70, boxHeight);
			JComboBox<String> genderMenu = createComboBox(MainScreen.patientData[2], widthUnit * 31, 40, 80, boxHeight);
			add(patientID);
			add(titleMenu);
			add(genderMenu);
			break;
		    case 1: // these positions are filled with the
		    case 2: // createComboBoxArray() function
			break;
		    case 5:
			//createComboBoxArray(MainScreen.dateFormat, width / 60, 160);
			break;
		    case 6: 			
			JComboBox<String> day = createComboBox(MainScreen.dateFormat[0], 120, widthUnit * 16, 50, boxHeight);
			JComboBox<String> month = createComboBox(MainScreen.dateFormat[1], widthUnit * 21, 160, 50, boxHeight);
			JComboBox<String> year = createComboBox(MainScreen.dateFormat[2], widthUnit * 29, 160, 80, boxHeight);
			add(day);
			add(month);
			add(year);
		        break;
		    default:
			inputFields[i].setBounds(150, 40 + y_shift, 300, boxHeight);
		}
	    add(inputFields[i]);
	}
    }

    //private void createComboBoxArray(String[][] list, int x, int y) {
        //ArrayList<JComboBox<String>> jBoxArray = new ArrayList<JComboBox<String>>();
	//for (int i = 0; i < 3; i++) {
            //jBoxArray.add(new JComboBox<String>(list[i]));
	    //jBoxArray.get(i).setBounds(x + (i * 150), y, 50, boxHeight);
	    //add(jBoxArray.get(i));
	//}
    //}

    private JComboBox<String> createComboBox(String[] text, int x, int y, int width, int height) {
        JComboBox<String> newBox = new JComboBox<String>(text);
        newBox.setBounds(x, y, width, height);
        return newBox;
    }
}
