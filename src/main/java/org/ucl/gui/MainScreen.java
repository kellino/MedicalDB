package org.ucl.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultEditorKit;
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
	protected static String[] currentIds = Database.idNumbers.stream().toArray(String[]::new);
	protected static final String[][] dateFormat = { Database.days, Database.months, Database.years };
	protected static final String[][] patientData = { currentIds, { "-", "Mr", "Miss", "Mrs", "Ms", "Dr" },
			{ "-", "Male", "Female" } };
	protected static final String[] fields = new String[] { "Patient ID", "Title", "Sex", "Last Name", "First Name(s)",
			"Date of Birth", "dd", "mm", "YY", "Condition(s)", "Address", "Next Appt.", "url", "Photo", "Comments" };
	private static final Color LIGHT_BLUE = new Color(102, 178, 255, 225);
	private JTextField[] inputFields;
	protected Patient chosenResult;
	private JLabel condition;
	private JLabel link;
	private JLabel nextAppointment;
	private JTextArea commentField;
	private String uriStr = "";
	private JLabel picture;
	private static JPanel images;
	private static final int boxHeight = 30;

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
		JMenuItem menuItem;

		mb.setBounds(0, 0, 1200, boxHeight);
		mb.setBorder(BorderFactory.createEtchedBorder(1));

		JMenu mnFile = new JMenu("File");

		mnFile.setMnemonic(KeyEvent.VK_F);
		/* save */
		menuItem = mnFile.add(new JMenuItem("Save", 's'));
		// action listener needed
		/* import database from file */
		menuItem = mnFile.add(new JMenuItem("Import", 'i'));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String result = createPhotoChooser();
		        Main.medDB.loadDBfromFile(result);
		        System.out.println(result);
		    }
		});
		/* exit the program */
		menuItem = mnFile.add(new JMenuItem("Exit", 'x'));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* save function should be called here */
				int reply = confirmationDialog("Are you sure you want to quit?", "Exit confirmation",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					System.out.println("Exiting program");
					System.exit(0);
				}
			}
		});
		mb.add(mnFile);

		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setMnemonic(KeyEvent.VK_E);

		/* cut */
		menuItem = mnEdit.add(new JMenuItem(new DefaultEditorKit.CutAction()));
		menuItem.setText("Cut");
		menuItem.setMnemonic(KeyEvent.VK_T);
		mnEdit.add(menuItem);
		/* copy */
		menuItem = mnEdit.add(new JMenuItem(new DefaultEditorKit.CopyAction()));
		menuItem.setText("Copy");
		menuItem.setMnemonic(KeyEvent.VK_C);
		mnEdit.add(menuItem);
		/* paste */
		menuItem = mnEdit.add(new JMenuItem(new DefaultEditorKit.PasteAction()));
		menuItem.setText("Paste");
		menuItem.setMnemonic(KeyEvent.VK_P);
		mnEdit.add(menuItem);

		mb.add(mnEdit);

		JMenu mnAbout = new JMenu("About");

		mnAbout.setMnemonic(KeyEvent.VK_A);
		mb.add(mnAbout);

		JMenu mnHelp = new JMenu("Help");

		mnHelp.setMnemonic(KeyEvent.VK_H);
		mb.add(mnHelp);

		return mb;
	}

	/** popup confirmation dialog for user-driven decisions */
	private int confirmationDialog(String message, String title, int messageType) {
		int reply = JOptionPane.NO_OPTION;
		try {
			reply = JOptionPane.showConfirmDialog(null, message, title, messageType);
		} catch (HeadlessException he) {
			System.out.println(he.getMessage());
		}
		return reply;
	}

	/** overloading method */
	private int confirmationDialog(Object object, String title, int messageType) {
		int reply = JOptionPane.NO_OPTION;
		try {
			reply = JOptionPane.showConfirmDialog(null, object, title, JOptionPane.YES_NO_OPTION);
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
		JPanel area = new JPanel(null);

		area.setPreferredSize(new Dimension(1200, 300));
		area.setBackground(LIGHT_BLUE);
		area.setBorder(BorderFactory.createEtchedBorder(1));

		JLabel[] titleContainers = new JLabel[fields.length];
		for (int i = 0; i < fields.length; i++) {
			titleContainers[i] = new JLabel();
			switch (i) {
			case 0: /* patient id */
				titleContainers[i].setBounds(50, 40, 150, boxHeight);
				titleContainers[i].setText(fields[i]);
				break;
			case 1: /* title */
				titleContainers[i].setBounds(240, 40, 100, boxHeight);
				titleContainers[i].setText(fields[i]);
				break;
			case 2: /* sex */
				titleContainers[i].setBounds(340, 40, 100, boxHeight);
				titleContainers[i].setText(fields[i]);
				break;
			case 3: /* last name */
				titleContainers[i].setBounds(50, 80, 100, boxHeight);
				titleContainers[i].setText(fields[i]);
				break;
			case 4: /* first name */
				titleContainers[i].setBounds(50, 120, 100, boxHeight);
				titleContainers[i].setText(fields[i]);
				break;
			case 5: /* first name */
				titleContainers[i].setBounds(50, 160, 100, boxHeight);
				titleContainers[i].setText(fields[i]);
				break;
			case 10: /* address */
				titleContainers[i].setBounds(50, 200, 100, boxHeight);
				titleContainers[i].setText(fields[i]);
				break;
			default:
				break;
			}
			area.add(titleContainers[i]);
		}
		inputFields = new JTextField[fields.length];
		for (int i = 0; i < fields.length; i++) {
			inputFields[i] = new JTextField();
			inputFields[i].setEditable(false);
			switch (i) {
			case 0: /* patient id */
				inputFields[i].setBounds(150, 40, 80, boxHeight);
				break;
			case 1: /* title */
				inputFields[i].setBounds(280, 40, 50, boxHeight);
				break;
			case 2: /* sex */
				inputFields[i].setBounds(370, 40, 80, boxHeight);
				break;
			case 3: /* last name */
				inputFields[i].setBounds(150, 80, 150, boxHeight);
				break;
			case 4: /* first name */
				inputFields[i].setBounds(150, 120, 250, boxHeight);
				break;
			case 5: /* date of birth */
				inputFields[i].setBounds(150, 160, 150, boxHeight);
				break;
			case 10: /* address */
				inputFields[i].setBounds(150, 200, 450, boxHeight);
				break;
			default:
				break;
			}
			area.add(inputFields[i]);
		}

		picture = new JLabel();

		picture.setIcon(new ImageIcon("/home/david/Programming/Java/medicaldb/res/placeholder.png"));
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
	 * a JPanel with the database manipulation buttons, for editing a patient,
	 * adding a patient, for launching the search function and other elements.
	 */
	private JPanel createDatabaseChanger() {
		JPanel databaseChanger = new JPanel(new FlowLayout());

		databaseChanger.setPreferredSize(new Dimension(300, 400));
		databaseChanger.setBackground(new Color(100, 100, 100, 100));
		databaseChanger.setBorder(new EmptyBorder(100, 0, 0, 0));

		JButton remover = new JButton();
		remover.setText("<html><b><font color=red>Delete Patient</font></b></html>");
		remover.setMinimumSize(new Dimension(150, boxHeight));
		remover.setPreferredSize(new Dimension(200, boxHeight));
		remover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chosenResult != null) {
					DatabaseEditor pa = new DatabaseEditor(chosenResult);
					int reply = confirmationDialog(
							"<html>Are you  <i><b>sure</b></i> you want to delete the patient? This cannot be undone!",
							"Remove patient", JOptionPane.OK_CANCEL_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						pa.deletePatient(chosenResult);
						fillInputFields();
					}
				} else {
					confirmationDialog("Please choose a patient", "Error deleting", JOptionPane.OK_OPTION);
				}
			}
		});
		databaseChanger.add(remover);

		JButton adder = new JButton();
		adder.setText("<html><b>Add patient</b></html>");
		adder.setMinimumSize(new Dimension(100, boxHeight));
		adder.setPreferredSize(new Dimension(200, boxHeight));
		adder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chosenResult = new Patient();
				DatabaseEditor pa = new DatabaseEditor(chosenResult);
				int result = JOptionPane.showConfirmDialog(null, pa, "Add Patient", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					pa.textFieldsToPatient();
					if (Main.medDB.errors.size() == 0) {
					    pa.appendPatient(chosenResult);
					    fillInputFields(chosenResult);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Errors exist. Cannot save");
                                        }
				} else
					System.out.println("Patient adding cancelled");
			}
		});
		databaseChanger.add(adder);

		JButton editor = new JButton();
		editor.setText("<html><b>Edit patient</></html>");
		editor.setMinimumSize(new Dimension(100, boxHeight));
		editor.setPreferredSize(new Dimension(200, boxHeight));
		editor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    try {
				if (chosenResult.getPatientID().equals("")) {
					confirmationDialog("Choose a patient first", "Editor error", JOptionPane.WARNING_MESSAGE);
				} else {
					DatabaseEditor pa = new DatabaseEditor(chosenResult);
					int result = confirmationDialog(pa, "Edit Patient", JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						pa.textFieldsToPatient();
						pa.editPatient();
						fillInputFields(chosenResult);
					} else
						System.out.println("Patient editing cancelled");
				}
                                /* if edit is pressed as the very first activity after logging in an exception is
                                 * thrown, this catches it and prints out a useful error message */
                            } catch (NullPointerException ne) {
			        confirmationDialog("Please choose a patient", "Editor error", JOptionPane.WARNING_MESSAGE);
                            }
			}
                        
		});
		databaseChanger.add(editor);

		/* search text box */
		JLabel searchBox = new JLabel("<html><b><font color=white>Enter search here</font></b></html>",
				SwingConstants.CENTER);
		searchBox.setBackground(new Color(200, 100, 100, 200));
		searchBox.setPreferredSize(new Dimension(200, boxHeight));
		JTextField searchTxtArea = new JTextField();
		searchTxtArea.setPreferredSize(new Dimension(250, boxHeight));
		databaseChanger.add(searchBox);
		databaseChanger.add(searchTxtArea);

		/* the search function launcher */
		JButton search = new JButton();
		search.setMinimumSize(new Dimension(100, boxHeight));
		search.setPreferredSize(new Dimension(200, boxHeight));
		search.setText("<html><b><font color=red>Search</font></b></html>");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Patient> results = Main.medDB.searchPatients(searchTxtArea.getText());
				String title = "Search Results";
				if (results.size() != 0) {
					try {
						chosenResult = (Patient) JOptionPane.showInputDialog(null, "Search Results", title,
								JOptionPane.QUESTION_MESSAGE, null, results.toArray(), results.get(0));
						fillInputFields(chosenResult);
					} catch (Exception ex) {
						System.out.println("Results cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(null, "No matches found");
				}
				searchTxtArea.setText("");
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
		createPhotoPane();

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
		condition = new JLabel();
		condition.setText("<html><b>Condition</b></html>");
		condition.setForeground(Color.WHITE);
		condition.setPreferredSize(new Dimension(300, boxHeight));
		condition.setMinimumSize(new Dimension(200, boxHeight));
		medicalHistory.add(condition, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 1;
		link = new JLabel();
		link.setText(
				"<html>Click <b><font color=red>here</font></b> for more information on " + "condition" + "</html>");
		link.setForeground(Color.WHITE);
		link.setPreferredSize(new Dimension(250, boxHeight));
		link.setMinimumSize(new Dimension(200, boxHeight));
		link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		link.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					if (Desktop.isDesktopSupported()) {
						Desktop desktop = Desktop.getDesktop();
						try {
							URI uri = new URI(uriStr);
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
		nextAppointment = new JLabel();
		nextAppointment.setForeground(Color.WHITE);
		nextAppointment.setText("<html><b>Next Appointment:</b></html>");
		nextAppointment.setPreferredSize(new Dimension(300, boxHeight));
		nextAppointment.setMinimumSize(new Dimension(200, boxHeight));
		medicalHistory.add(nextAppointment, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 3;
		JLabel comments = new JLabel();
		comments.setText("<html><b>Comments</b></html>");
		comments.setForeground(Color.WHITE);
		comments.setPreferredSize(new Dimension(100, boxHeight));
		comments.setMinimumSize(new Dimension(50, boxHeight));
		medicalHistory.add(comments, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridy = 4;
		c.weighty = 1;
		commentField = new JTextArea();
		commentField.setPreferredSize(new Dimension(600, 200));
		commentField.setMinimumSize(new Dimension(200, 200));
		commentField.setEditable(false);
		commentField.setLineWrap(true);
		commentField.setWrapStyleWord(true);
		medicalHistory.add(commentField, c);

		return medicalHistory;
	}

	/** GridLayout of buttons which hold relevant medical images */
	private void createPhotoPane() {
		images = new JPanel();
		images.setBackground(Color.LIGHT_GRAY);
	}

	private void addPhotosToPhotoPane(String medPhotos) {
		File photoDir = new File(medPhotos);
		File[] directoryListing = photoDir.listFiles();
		if (directoryListing != null) {
			for (File photo : directoryListing) {
				JButton placeHolder = new JButton();
				placeHolder.setIcon(new ImageIcon(photo.toString()));
				placeHolder.setPreferredSize(new Dimension(100, 100));
				placeHolder.setBorder(BorderFactory.createBevelBorder(1));
				placeHolder.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JLabel popup = new JLabel(new ImageIcon(photo.toString()));
						JOptionPane.showMessageDialog(null, popup, "Popup", JOptionPane.PLAIN_MESSAGE, null);
					}
				});
				images.add(placeHolder, BorderLayout.CENTER);
			}
		}
	}

	/** <i>populates</i> the data areas with empty strings, effectively clearing the current
	 * patient for the GUI
	 */
	private void fillInputFields() {
		inputFields[0].setText("");
		inputFields[1].setText("");
		inputFields[2].setText("");
		inputFields[3].setText("");
		inputFields[4].setText("");
		inputFields[5].setText("");
		inputFields[10].setText("");
		condition.setText("<html><b>Condition </b></html>");
		link.setText("<html>Click <b><font color=red>here</font></b> for more information on " + " " + "</html>");
		commentField.setText("");
		uriStr = "";
		nextAppointment.setText("Next Appointment");
		images.removeAll();
		picture.setIcon(new ImageIcon("/home/david/Programming/Java/medicaldb/res/placeholder.png"));
	}

	/** populates the patient data areas */
	private void fillInputFields(Patient p) {
		inputFields[0].setText(p.getPatientID());
		inputFields[1].setText(p.getTitle());
		inputFields[2].setText(p.getSex());
		inputFields[3].setText(p.getLastName());
		inputFields[4].setText(p.getFirstName());
		inputFields[5].setText(p.getDOB());
		inputFields[10].setText(p.getAddress());
		condition.setText("<html><b>Condition </b>" + p.getCondition() + "</html>");
		link.setText("<html>Click <b><font color=red>here</font></b> for more information on " + p.getCondition()
				+ "</html>");
		commentField.setText(p.getComments());
		uriStr = p.getURI();
		nextAppointment.setText("<html><b>Next Appointment</b>" + "  " + p.getNextAppointment() + "</html>");
		picture.setIcon(new ImageIcon(p.getProfilePhoto()));
		images.removeAll();
		addPhotosToPhotoPane(p.getMedPhotos());
	}

	// ugly duplication of code here. TODO fix this
	private String createPhotoChooser() {
		JFileChooser imageChooser = new JFileChooser();
		imageChooser.showOpenDialog(this);
		File file = imageChooser.getSelectedFile();
		return file.toString();
	}
}
