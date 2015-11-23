package org.ucl.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;




@SuppressWarnings("serial")
public class GUI extends JFrame {
    private static final Logger log = Logger.getLogger(Class.class.getName());

    JPanel cardPanel;
    CardLayout cards = new CardLayout();
    LoginHandler lh;
    static JTextField usernameField;	 
    static JTextField passwordField;

    /**
     * @wbp.parser.entryPoint
     */
    public void createAndShowGUI() {
        JFrame frame = new JFrame("UCL Medical Database @author David Kelly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 800));
        
        GUI gui = new GUI();
        gui.addComponentToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public void addComponentToPane(Container pane) {
        JPanel LoginScreen = loginScreen();
		
	JPanel MainScreen = mainScreen();
		
	cardPanel = new JPanel();
	cardPanel.setLayout(cards);
	cardPanel.add(LoginScreen);
	cardPanel.add(MainScreen);
		
	pane.add(cardPanel, BorderLayout.CENTER);
    }

    /** method for creating and drawing the login screen
     * @return JPanel
     */
    private JPanel loginScreen() {
        /* initializes the login checker */
        lh = new LoginHandler();

        /* initialize the loginScreen panel */
        JPanel ls = new JPanel();
        ls.setLayout(null);

        JLabel userNameLbl = new JLabel("Username");
        userNameLbl.setBounds(400, 150, 72, 25);
        ls.add(userNameLbl);

	usernameField = new JTextField();
	usernameField.setBounds(400, 240, 104, 25);
	ls.add(usernameField);
	usernameField.setColumns(10);
	
	JLabel lblPassword = new JLabel("Password");
	lblPassword.setBounds(400, 290, 70, 15);
	
	ls.add(lblPassword);
	
	final JTextField passwordField = new JPasswordField();
	passwordField.setBounds(400, 288, 100, 25);
	ls.add(passwordField);

	JButton button = new JButton("Enter");
	button.setBounds(352, 381, 99, 25);
	button.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		lh.setUsername(usernameField.getText());
		lh.setPassword(passwordField.getText());
		if (lh.checkLoginDetails()) {
		    cards.next(cardPanel);
                } else {
        	    usernameField.setText("");
                    passwordField.setText("");
                    try {
                        JOptionPane.showMessageDialog(null, "Incorrect username or password!", "Login Error", JOptionPane.ERROR_MESSAGE);
                    } catch (HeadlessException hle) {
                        log.log(Level.SEVERE, hle.getMessage());
                    }

                }
	    }
	});
	ls.add(button);

        return ls;
    }

    private JPanel mainScreen() {
        JPanel ms = new JPanel();
        JLabel stuff = new JLabel("nothing to see here");
        ms.add(stuff);
        return ms;
    }
}
