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
    private static final String[] titles = new String[] {"Patient ID", "Last Name", "First Name(s)", "Date of Birth", "Address"};

    public MainScreen() {
	setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
	JMenuBar menuBar = createMenu();
	add(menuBar, c);
	

	c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
	c.gridx = 0;
	c.gridy = 5;
        c.weightx = 1.0;
        c.weighty = 5.0;
	JPanel patientData = createPatientDataArea();
	add(patientData, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 1.0;
        c.weighty = 6.0;
	JTabbedPane medicalDataPane = createMedicalDataPane();
        add(medicalDataPane, c);
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
            int y_shift = i * 40;
            titleContainers[i] = new JLabel();
            titleContainers[i].setBounds(30, 40 + y_shift, 150, 30);
            titleContainers[i].setText(titles[i]);
            area.add(titleContainers[i]);
        }
        
        JTextField[] inputFields = new JTextField[titles.length];
        for (int i = 0; i < titles.length; i++) {
            int y_shift = i * 40;
            inputFields[i] = new JTextField();
            inputFields[i].setBounds(110, 40 + y_shift, 200, 30);
            area.add(inputFields[i]);
        }

        JLabel picture = new JLabel();
        picture.setIcon(new ImageIcon("/home/david/Programming/Java/medicaldb/res/ab100.png"));
        picture.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
        picture.setBounds(900, 40, 200, 200);
        area.add(picture);

        return area;
    }

    private JTabbedPane createMedicalDataPane() {
        JTabbedPane medicalDataPane = new JTabbedPane();
        JLabel medicalHistory = new JLabel();
        JLabel photos = new JLabel();
        medicalDataPane.add("Medical History", medicalHistory); 
        medicalDataPane.add("Photographs", photos);

        return medicalDataPane;
    }
}
