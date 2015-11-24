package org.ucl.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * Creates the main screen gui, initializing the various members, such as the menu bar and tabbed 
 * screen
 */
@SuppressWarnings("serial")
public class MainScreen extends JPanel {
    private static final Color LIGHT_BLUE = new Color(102, 178, 255);

    public MainScreen() {
	setLayout(new BorderLayout());

	UIManager.getLookAndFeelDefaults()
	    .put("defaultFont", new Font("Sans Serif", Font.BOLD, 14));
		
	JMenuBar menuBar = createMenu();
	add(menuBar);

	JPanel patientData = createPatientDataArea();
	add(patientData);

	JTabbedPane medicalDataPane = createMedicalDataPane();
	JPanel panel = new JPanel();
	panel.setBackground(Color.CYAN);
	panel.setMinimumSize(new Dimension(100, 100));

	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, medicalDataPane);
	splitPane.setLocation(0, 400);
	splitPane.setOneTouchExpandable(true);
	splitPane.setDividerLocation(500);
        add(splitPane);
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
            reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        } catch (HeadlessException he) {
            System.out.println(he.getMessage());            
        }
        return reply;
    }


    private JPanel createPatientDataArea() {
        JPanel area = new JPanel();
        area.setBounds(0, 30, 1200, 300);
        area.setLayout(null);
        area.setBackground(LIGHT_BLUE);

        JLabel patientID = new JLabel();
        patientID.setBounds(30, 40, 100, 25);
        patientID.setText("Patient ID");
        area.add(patientID);

        JLabel lastName = new JLabel();
        lastName.setBounds(30, 70, 100, 25);
        lastName.setText("Last Name");
        area.add(lastName);

        JLabel firstNames = new JLabel();
        firstNames.setBounds(30, 100, 100, 25);
        firstNames.setText("First Name(s)");
        area.add(firstNames);

        JLabel dob = new JLabel();
        dob.setBounds(30, 130, 100, 25);
        dob.setText("Date of Birth");
        area.add(dob);

        JLabel address = new JLabel();
        address.setBounds(30, 160, 100, 25);
        address.setText("Address");
        area.add(address);

        JTextField patientIDField = new JTextField();
        patientIDField.setBounds(110, 40, 150, 25);
        area.add(patientIDField);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(110, 70, 150, 25);
        area.add(lastNameField);

        JTextField firstNamesField = new JTextField();
        firstNamesField.setBounds(110, 100, 150, 25);
        area.add(firstNamesField);

        JComboBox<String> date = new JComboBox<String>();
        date.setBounds(110, 130, 50, 25);
        date.setEditable(false);
        area.add(date);

        JComboBox<String> month = new JComboBox<String>();
        month.setBounds(160, 130, 50, 25);
        month.setEditable(false);
        area.add(month);

        JComboBox<String> year = new JComboBox<String>();
        year.setBounds(220, 130, 50, 25);
        year.setEditable(false);
        area.add(year);

        JTextField addressField = new JTextField();
        addressField.setBounds(110, 160, 150, 25);
        area.add(addressField);

        JLabel picture = new JLabel();
        picture.setIcon(new ImageIcon("/home/david/Programming/Java/medicaldb/res/ab100.png"));
        picture.setBounds(900, 40, 200, 200);
        picture.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
        area.add(picture);

        return area;
    }

    private JTabbedPane createMedicalDataPane() {
        JTabbedPane medicalDataPane = new JTabbedPane();
        JPanel condition = new JPanel();
        JPanel comments = new JPanel();
        JPanel photos = new JPanel();
        medicalDataPane.setBounds(500, 400, 750, 300); 
        medicalDataPane.addTab("Condition", condition);
        condition.setBounds(500, 400, 750, 300); 
        medicalDataPane.addTab("Comments", comments);
        comments.setBounds(500, 400, 750, 300); 
        medicalDataPane.addTab("Photos", photos);
        photos.setBounds(500, 400, 750, 300); 

        return medicalDataPane;
    }
}
