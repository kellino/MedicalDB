package org.ucl.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
import org.ucl.medicaldb.Database;

/**
 * Creates the main screen gui, initializing the various members, such as the
 * menu bar and tabbed screen
 */
@SuppressWarnings("serial")
public class MainScreen extends JPanel {
    protected static String[] currentIds = Database.idNumbers.stream().toArray(String[]::new); /* this code snipped was inspired by a 
	                                                                                      * stackoverflow question */
    protected static final String[][] dateFormat = { Database.days, Database.months, Database.years };
    protected static final String[][] patientData = { currentIds, { "Mr", "Mrs", "Ms", "Dr" }, { "Male", "Female" } };
    protected static final String[] fields = new String[] { "Patient ID", "Title", "Sex", "Last Name", "First Name(s)",
			"Date of Birth", "D", "M", "Y", "Condition(s)", "Address", "Next Appointment", "Comments" };
    private static final Color LIGHT_BLUE = new Color(102, 178, 255, 240);
    private static final int WIDTH = GUI.WIDTH;
	// private static final int HEIGHT = GUI.HEIGHT;

	/** contructor for the main screen. */
	public MainScreen() {
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		/* main horizontal file menu at the top of the screen */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		JMenuBar menuBar = createMenu();
		add(menuBar, c);

		/* patient data panel, including patient details and profile photo */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 5;
		JPanel patientData = createPatientDataArea();
		add(patientData, c);

		/*
		 * a split pane with a nested tabbed pane for displaying comments and
		 * medical photos
		 */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 6;
		c.weighty = 1.0;
		JSplitPane medicalHistoryPanel = medicalHistoryPanel();
		add(medicalHistoryPanel, c);
	}

	/**
	 * overriding the paintComponent(Graphics g) method allows us to set an
	 * image as the background for our JPanel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(LoginScreen.bgimage, 0, 0, null);
	}

	/** creates the main menu at the top of the screen */
	private JMenuBar createMenu() {
		JMenuBar mb = new JMenuBar();
		MenuListener l = new MenuListener();
		JMenuItem menuItem;
		mb.setBounds(0, 0, 1200, 30);
		mb.setBorder(BorderFactory.createEtchedBorder(1));

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
				int reply = confirmationDialog("Are you sure you want to quit?", "Exit confirmation");
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

	/** listener method for the dropdown menus from the main menu bar */
	private class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
		}
	}

	/** popup confirmation dialog for user-driven decisions */
	private int confirmationDialog(String message, String title) {
		int reply = JOptionPane.NO_OPTION;
		try {
			reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		} catch (HeadlessException he) {
			System.out.println(he.getMessage());
		}
		return reply;
	}

	private JPanel createPatientDataArea() {
		JPanel area = new JPanel(null);
		area.setPreferredSize(new Dimension(1200, 300));
		area.setBackground(LIGHT_BLUE);
		area.setBorder(BorderFactory.createEtchedBorder(1));

		JLabel[] titleContainers = new JLabel[fields.length];
		for (int i = 0; i < fields.length; i++) {
			int y_shift = (i - 2) * 40;
			titleContainers[i] = new JLabel();
			switch (i) {
			case 0: /* patient id */
				titleContainers[i].setBounds(50, 40, 150, 30);
				titleContainers[i].setText(fields[i]);
				break;
			case 1: /* title */
				titleContainers[i].setBounds(240, 40, 100, 30);
				titleContainers[i].setText(fields[i]);
				break;
			case 2: /* sex */
				titleContainers[i].setBounds(380, 40, 100, 30);
				titleContainers[i].setText(fields[i]);
				break;
			case 6:
				titleContainers[i].setBounds(100, 4 - +y_shift, 100, 30);
				break;
			case 7:
			case 8:
				break;
			default:
				titleContainers[i].setBounds(50, 40 + y_shift, 150, 30);
				titleContainers[i].setText(fields[i]);
				break;
			}
			area.add(titleContainers[i]);
		}

		JTextField[] inputFields = new JTextField[fields.length];
		for (int i = 0; i < fields.length; i++) {
			int y_shift = (i - 2) * 40;
			inputFields[i] = new JTextField();

			switch (i) {
			case 0: /* patient id */
				createComboBoxArray(area, patientData, 40);
				break;
			case 1: // this positions are filled with the
			case 2: // createComboBoxArray() function
				break;
			case 5:
				createComboBoxArray(area, dateFormat, 160);
				break;
			case 6: /* address */
				inputFields[i].setBounds(150, 40 + y_shift, WIDTH / 2, 30);
				break;
			default:
				inputFields[i].setBounds(150, 40 + y_shift, 300, 30);
			}
			area.add(inputFields[i]);
		}

		JLabel picture = new JLabel();
		picture.setIcon(new ImageIcon("/home/david/Programming/Java/medicaldb/res/ab100.png"));
		picture.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
		picture.setBounds(900, 40, 200, 200);
		area.add(picture);

		return area;
	}

	private void createComboBoxArray(JPanel area, String[][] list, int y) {
		ArrayList<JComboBox<String>> jBoxArray = new ArrayList<JComboBox<String>>();
		for (int i = 0; i < 3; i++) {
			jBoxArray.add(new JComboBox<String>(list[i]));
			jBoxArray.get(i).setBounds(150 + (i * 130), y, WIDTH / 14, 30);
			area.add(jBoxArray.get(i));
		}
	}

	private JSplitPane medicalHistoryPanel() {
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JTabbedPane medicalDataPane = tabbedMedicalDataPane();
		JPanel databaseChanger = new JPanel();
                //databaseChanger.setLayout(null);
                databaseChanger.setPreferredSize(new Dimension(300, 400));

		JButton adder = new JButton();
		adder.setText("Add patient");
		adder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseEditor pa = new DatabaseEditor();
				int result = JOptionPane.showConfirmDialog(null, pa, "Add Patient", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					System.out.println("Patient added");
				} else {
					System.out.println("Patient adding cancelled");
				}
			}
		});
		databaseChanger.add(adder);

		JButton editor = new JButton();
		editor.setText("Edit patient");
		editor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseEditor pa = new DatabaseEditor("ab100");
				int result = JOptionPane.showConfirmDialog(null, pa, "Edit Patient", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					System.out.println("Patient edited");
				} else {
					System.out.println("Patient editing cancelled");
				}
			}
		});
		databaseChanger.add(editor);

		splitPane.setLeftComponent(databaseChanger);
		splitPane.setRightComponent(medicalDataPane);
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		splitPane.setPreferredSize(new Dimension(600, 460));

		return splitPane;
	}

	private JTabbedPane tabbedMedicalDataPane() {
		JTabbedPane medicalDataPane = new JTabbedPane();
		medicalDataPane.setMinimumSize(new Dimension(500, 400));
		medicalDataPane.setPreferredSize(new Dimension(800, 400));

		JPanel medicalHistory = createMedicalHistoryPane();

		JPanel photos = new JPanel();
		photos.setBackground(Color.BLUE);

		medicalDataPane.add("Medical History", medicalHistory);
		medicalDataPane.add("Photographs", photos);

		return medicalDataPane;
	}

	private JPanel createMedicalHistoryPane() {
		JPanel medicalHistory = new JPanel();
		medicalHistory.setBackground(Color.DARK_GRAY);
		medicalHistory.setLayout(new FlowLayout());
		medicalHistory.setBorder(BorderFactory.createBevelBorder(1));

		JLabel condition = new JLabel();
		condition.setText("Medical Condition(s)");
		condition.setBackground(Color.LIGHT_GRAY);
		condition.setForeground(Color.WHITE);
		medicalHistory.add(condition);

		JTextField conditionField = new JTextField();
		conditionField.add(condition);

		return medicalHistory;
	}
}
