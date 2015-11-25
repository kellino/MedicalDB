package org.ucl.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Creates the main screen gui, initializing the various members, such as the menu bar and tabbed 
 * screen
 */
@SuppressWarnings("serial")
public class MainScreen extends JPanel {
    private static final Color LIGHT_BLUE = new Color(102, 178, 255);
    private static final String[] titles = new String[] {"Patient ID", "Title", "Sex", "Last Name", "First Name(s)", "Date of Birth", "Address"};
    private static final int WIDTH = GUI.WIDTH;
    private static final int HEIGHT = GUI.HEIGHT;

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

        /* a split pane with a nested tabbed pane for displaying comments and medical photos */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 6;
        c.weighty = 1.0;
	JSplitPane medicalHistoryPanel = medicalHistoryPanel();
        add(medicalHistoryPanel, c);
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
        JPanel area = new JPanel(null);
        area.setPreferredSize(new Dimension(1200, 300));
        area.setBackground(LIGHT_BLUE);


        JLabel[] titleContainers = new JLabel[titles.length];
        for (int i = 0; i < titles.length; i++) {
            int y_shift = (i - 2) * 40;
            titleContainers[i] = new JLabel();
            switch (i) {
                case 0: /* patient id */
                    titleContainers[i].setBounds(40, 40, 150, 30);
                    titleContainers[i].setText(titles[i]);
                    break;
                case 1: /* title */
                    titleContainers[i].setBounds(260, 40, 150, 30);
                    titleContainers[i].setText(titles[i]);
                    break;
                case 2: /* sex */
                    titleContainers[i].setBounds(400, 40, 150, 30);
                    titleContainers[i].setText(titles[i]);
                    break;
                default:
                    titleContainers[i].setBounds(40, 40 + y_shift, 150, 30);
                    titleContainers[i].setText(titles[i]);
                    break;
            }
            area.add(titleContainers[i]);
        }
        
        JTextField[] inputFields = new JTextField[titles.length];
        for (int i = 0; i < titles.length; i++) {
            int y_shift = (i - 2) * 40;
            inputFields[i] = new JTextField();
            
            switch (i) {
                case 0: /* patient id */
                    inputFields[i].setBounds(150, 40, WIDTH / 12, 30);
                    break;
                case 1: /* title */
                    //inputFields[i].setBounds(300, 40, WIDTH / 13, 30);
                    break;
                case 2: /* sex */
                    //inputFields[i].setBounds(450, 40, WIDTH / 13, 30);
                    break;
                case 6: /* address */
                    inputFields[i].setBounds(150, 40 + y_shift, WIDTH / 2, 30);
                    break;
                default:
                    inputFields[i].setBounds(150, 40 + y_shift, 300, 30);
            }
            area.add(inputFields[i]);
        }

        JComboBox<String> title = new JComboBox<String>();
        title.setBounds(300, 40, WIDTH / 13, 30);
        area.add(title);

        JComboBox<String> sex = new JComboBox<String>();
        sex.setBounds(420, 40, WIDTH / 13, 30);
        area.add(sex);

        JLabel picture = new JLabel();
        picture.setIcon(new ImageIcon("/home/david/Programming/Java/medicaldb/res/ab100.png"));
        picture.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
        picture.setBounds(900, 40, 200, 200);
        area.add(picture);

        return area;
    }

    private JSplitPane medicalHistoryPanel() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JTabbedPane medicalDataPane = tabbedMedicalDataPane();
	JLabel label = new JLabel();
	label.setMinimumSize(new Dimension(300, 400));

        splitPane.setLeftComponent(label);
        splitPane.setRightComponent(medicalDataPane);
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(true);
        splitPane.setPreferredSize(new Dimension(800, 420));
    
        return splitPane;
    }

    private JTabbedPane tabbedMedicalDataPane() {
        JTabbedPane medicalDataPane = new JTabbedPane();
        medicalDataPane.setMinimumSize(new Dimension(500, 400));
        medicalDataPane.setPreferredSize(new Dimension(800, 400));

        JPanel medicalHistory = new JPanel();
        medicalHistory.setBackground(Color.DARK_GRAY);
        JLabel condition = new JLabel();
        condition.setText("Medical Condition(s)");
        condition.setBackground(Color.LIGHT_GRAY);
        condition.setForeground(Color.WHITE);
        medicalHistory.add(condition);

        JPanel photos = new JPanel();
        photos.setBackground(Color.BLUE);

        medicalDataPane.add("Medical History", medicalHistory); 
        medicalDataPane.add("Photographs", photos);

        return medicalDataPane;
    }
}
