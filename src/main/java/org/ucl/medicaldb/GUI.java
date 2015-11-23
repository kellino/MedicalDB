package org.ucl.medicaldb;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;


@SuppressWarnings("serial")
public class GUI extends JFrame {
    JPanel cardPanel;
    CardLayout cards = new CardLayout();
    //private JTextField usernameField;
    //private JPasswordField passwordField;
	 
    /**
     * @wbp.parser.entryPoint
     */
    void createAndShowGUI() {
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

    private JPanel loginScreen() {
        final LoginHandler lh = new LoginHandler();
        JPanel ls = new JPanel();
        ls.setLayout(null);
        JLabel userNameLbl = new JLabel("Username");
        userNameLbl.setBounds(223, 251, 72, 15);
        ls.add(userNameLbl);

	final JTextField usernameField = new JTextField();
	usernameField.setBounds(352, 249, 104, 19);
	ls.add(usernameField);
	usernameField.setColumns(10);
	
	JLabel lblPassword = new JLabel("Password");
	lblPassword.setBounds(205, 290, 70, 15);
	ls.add(lblPassword);
	
	final JTextField passwordField = new JPasswordField();
	passwordField.setBounds(380, 288, 100, 19);
	ls.add(passwordField);

	JButton button = new JButton("Enter");
	button.setBounds(352, 381, 99, 25);
	button.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (lh.checkLoginDetails()) {
		    cards.next(cardPanel);
                } else {
                    usernameField.setText("");
                    passwordField.setText("");
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
