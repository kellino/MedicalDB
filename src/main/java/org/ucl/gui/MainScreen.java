package org.ucl.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.ucl.medicaldb.Database;
import org.ucl.medicaldb.Main;
import org.ucl.medicaldb.Patient;

/**
 * Creates the main screen GUI, initializing the various members, such as the
 * menu bar and tabbed screen. The screen is split horizontally into two main
 * areas: the top area contains the main personal data of the loaded patient.
 * This area is not editable. The lower half of the screen features the editing
 * functions (on the left) and a tabbed pane with the specific medical condition
 * information
 */
@SuppressWarnings("serial")
public class MainScreen extends JPanel {
	protected static String[] currentIds = Database.idNumbers.stream()
			.toArray(String[]::new); /*
										 * this code snippet was inspired by a
										 * stackoverflow question
										 */
	protected static final String[][] dateFormat = { Database.days, Database.months, Database.years };
	protected static final String[][] patientData = { currentIds, { "-", "Mr", "Mrs", "Ms", "Dr" },
			{ "-", "Male", "Female" } };
	protected static final String[] fields = new String[] { "Patient ID", "Title", "Sex", "Last Name", "First Name(s)",
			"Date of Birth", "dd", "mm", "YY", "Condition(s)", "Address", "Next Appt.", "url", "Photo", "Comments" };
	private static final Color LIGHT_BLUE = new Color(102, 178, 255, 225);
	private JTextArea searchTxtArea;

	/** constructor for the main screen. */
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

		/* patient data panel, including patient details and profile image */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 5;

		JPanel patientData = createPatientDataArea();

		add(patientData, c);

		/*
		 * a split pane with a nested tabbed pane for displaying comments and
		 * medical images
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

	/**
	 * the upper half of the MainScreen gui. Initialized with a for loop
	 * containing a switch statement
	 */
	private JPanel createPatientDataArea() {
		// we should not use null as a layout
		JPanel area = new JPanel(null);

		area.setPreferredSize(new Dimension(1200, 300));
		area.setBackground(LIGHT_BLUE);
		area.setBorder(BorderFactory.createEtchedBorder(1));

		JLabel[] titleContainers = new JLabel[fields.length];
		for (int i = 0; i < fields.length; i++) {
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
				titleContainers[i].setBounds(340, 40, 100, 30);
				titleContainers[i].setText(fields[i]);
				break;
			case 3: /* last name */
				titleContainers[i].setBounds(50, 80, 100, 30);
				titleContainers[i].setText(fields[i]);
				break;
			case 4: /* first name */
				titleContainers[i].setBounds(50, 120, 100, 30);
				titleContainers[i].setText(fields[i]);
				break;
			case 5: /* first name */
				titleContainers[i].setBounds(50, 160, 100, 30);
				titleContainers[i].setText(fields[i]);
				break;
			case 10: /* address */
				titleContainers[i].setBounds(50, 200, 100, 30);
				titleContainers[i].setText(fields[i]);
				break;
			default:
				break;
			}
			area.add(titleContainers[i]);
		}
		JTextField[] inputFields = new JTextField[fields.length];
		for (int i = 0; i < fields.length; i++) {
			inputFields[i] = new JTextField();
			switch (i) {
			case 0: /* patient id */
				inputFields[i].setBounds(150, 40, 80, 30);
				break;
			case 1: /* title */
				inputFields[i].setBounds(280, 40, 50, 30);
				break;
			case 2: /* sex */
				inputFields[i].setBounds(370, 40, 80, 30);
				break;
			case 3: /* last name */
				inputFields[i].setBounds(150, 80, 150, 30);
				break;
			case 4: /* first name */
				inputFields[i].setBounds(150, 120, 250, 30);
				break;
			case 5: /* date of birth */
				inputFields[i].setBounds(150, 160, 150, 30);
				break;
			case 10: /* address */
				inputFields[i].setBounds(150, 200, 450, 30);
				break;
			default:
				break;
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

	/** bottom split of the MainScreen gui. A JSplitPane */
	private JSplitPane medicalHistoryPanel() {
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JPanel databaseChanger = createDatabaseChanger();
		JTabbedPane medicalDataPane = tabbedMedicalDataPane();

		splitPane.setLeftComponent(databaseChanger);
		splitPane.setRightComponent(medicalDataPane);
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		splitPane.setPreferredSize(new Dimension(600, 470));

		return splitPane;
	}

	/**
	 * a JPanel with three buttons, one for editing a patient, one for adding a
	 * patient, and one for launching the search function
	 */
	private JPanel createDatabaseChanger() {
		JPanel databaseChanger = new JPanel(new FlowLayout());

		databaseChanger.setPreferredSize(new Dimension(300, 400));
		databaseChanger.setBackground(new Color(100, 100, 100, 100));
		databaseChanger.setBorder(new EmptyBorder(100, 0, 0, 0));

		JButton adder = new JButton();
		adder.setText("<html><b>Add patient</b></html>");
		adder.setMinimumSize(new Dimension(100, 30));
		adder.setPreferredSize(new Dimension(200, 30));
		adder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseEditor pa = new DatabaseEditor();
				int result = JOptionPane.showConfirmDialog(null, pa, "Add Patient", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION)
					System.out.println("Patient added");
				else
					System.out.println("Patient adding cancelled");
			}
		});
		databaseChanger.add(adder);

		JButton editor = new JButton();
		editor.setText("<html><b>Edit patient</></html>");
		editor.setMinimumSize(new Dimension(100, 30));
		editor.setPreferredSize(new Dimension(200, 30));
		editor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseEditor pa = new DatabaseEditor("ab100");
				int result = JOptionPane.showConfirmDialog(null, pa, "Edit Patient", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION)
					System.out.println("Patient edited");
				else
					System.out.println("Patient editing cancelled");
			}
		});
		databaseChanger.add(editor);

		/* the search function launcher */
		JButton search = new JButton();
		search.setMinimumSize(new Dimension(100, 30));
		search.setPreferredSize(new Dimension(200, 30));
		search.setText("<html><b>Search</b></html>");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel searchDialog = createSearchDialog();
				int result = JOptionPane.showConfirmDialog(null, searchDialog, "Search the Database",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					ArrayList<Patient> results;
					results = Main.medDB.searchPatients(searchTxtArea.getText());
					for (Patient p : results) {
						System.out.println(p.toString());
					}
				} else {
					System.out.println("Patient search cancelled");
				}
			}

		});
		databaseChanger.add(search);

		return databaseChanger;
	}

	/**
	 * a JTabbedPane which houses the medical history pane (GridBagLayout) and
	 * the image pane (GridLayout)
	 */
	private JTabbedPane tabbedMedicalDataPane() {
		JTabbedPane medicalDataPane = new JTabbedPane();
		medicalDataPane.setMinimumSize(new Dimension(500, 400));
		medicalDataPane.setPreferredSize(new Dimension(800, 400));

		JPanel medicalHistory = createMedicalHistoryPane();
		JPanel images = createPhotoPane();

		medicalDataPane.add("Medical History", medicalHistory);
		medicalDataPane.add("Photographs", images);

		return medicalDataPane;
	}

	/** pane which houses the comments, the clickable uri and medical info */
	private JPanel createMedicalHistoryPane() {
		JPanel medicalHistory = new JPanel();

		medicalHistory.setBackground(new Color(100, 100, 100, 240));
		medicalHistory.setBorder(BorderFactory.createBevelBorder(1));
		medicalHistory.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 0;
		JLabel condition = new JLabel();
		condition.setText("Medical Condition(s):" + " Flatulence");
		condition.setForeground(Color.WHITE);
		condition.setPreferredSize(new Dimension(300, 30));
		condition.setMinimumSize(new Dimension(200, 30));
		medicalHistory.add(condition, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		// c.gridx = 0;
		c.gridy = 1;
		JLabel link = new JLabel();
		link.setText(
				"<html>Click <b><font color=red>here</font></b> for more information on " + "condition" + "</html>");
		link.setForeground(Color.WHITE);
		link.setPreferredSize(new Dimension(250, 30));
		link.setMinimumSize(new Dimension(200, 30));
		link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		link.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					if (Desktop.isDesktopSupported()) {
						Desktop desktop = Desktop.getDesktop();
						try {
							URI uri = new URI("http://www.bbc.co.uk");
							desktop.browse(uri);
						} catch (IOException ioe) {
							ioe.printStackTrace();
						} catch (URISyntaxException ue) {
							ue.printStackTrace();
						}
					}
				}
			}
		});
		medicalHistory.add(link, c);

		c.gridy = 2;
		JLabel nextAppointment = new JLabel();
		nextAppointment.setForeground(Color.WHITE);
		nextAppointment.setText("Next Appointment: " + "30/11/2015");
		nextAppointment.setPreferredSize(new Dimension(250, 30));
		nextAppointment.setMinimumSize(new Dimension(200, 30));
		medicalHistory.add(nextAppointment, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 3;
		JLabel comments = new JLabel();
		comments.setText("Comments");
		comments.setForeground(Color.WHITE);
		comments.setPreferredSize(new Dimension(100, 30));
		comments.setMinimumSize(new Dimension(50, 30));
		medicalHistory.add(comments, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 4;
		c.weighty = 1;
		JTextArea commentField = new JTextArea();
		commentField.setPreferredSize(new Dimension(600, 200));
		commentField.setMinimumSize(new Dimension(200, 200));
		medicalHistory.add(commentField, c);

		return medicalHistory;
	}

	/** GridLayout of buttons which hold relevant medical images */
	private JPanel createPhotoPane() {
		JPanel images = new JPanel();
		images.setLayout(new GridLayout(4, 4));
		images.setBackground(Color.LIGHT_GRAY);

		for (int i = 0; i < 8; i++) {
			JButton placeHolder = new JButton();
			placeHolder.setText("<html><font color=red>Place for Photo</font></html>");
			images.add(placeHolder);
		}
		return images;
	}

	/** search dialog */
	private JPanel createSearchDialog() {
		JPanel cards = new JPanel(new CardLayout());
		cards.setPreferredSize(new Dimension(400, 200));

		JPanel searchString = new JPanel(new BorderLayout());
		searchString.setBackground(LIGHT_BLUE);
		JLabel label = new JLabel();
		label.setText("Enter search here");
		searchString.add(label, BorderLayout.NORTH);
		searchTxtArea = new JTextArea();
		searchTxtArea.setPreferredSize(new Dimension(300, 30));
		searchString.add(searchTxtArea, BorderLayout.CENTER);

		JPanel results = new JPanel();
		results.setBackground(Color.GREEN);

		cards.add(searchString);
		cards.add(results);
		return cards;
	}
}
