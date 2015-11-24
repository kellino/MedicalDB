package org.ucl.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginScreen extends JPanel {
    private static final Logger log = Logger.getLogger(Class.class.getName());
    static JTextField usernameField;
    static JPasswordField passwordField;
    LoginHandler lh;
    BufferedImage bgimage;
    
    public LoginScreen() {
        /* initializes the login checker */
    	bgimage = null;
    	try {
    		bgimage = ImageIO.read(new File("/home/david/Programming/Java/medicaldb/res/med_background.png"));
    	} catch (IOException ioe) {}
    	
    	
        lh = new LoginHandler();

        /* initialize the loginScreen panel */
        setLayout(null);
        setBackground(Color.BLUE);

        JLabel userNameLbl = new JLabel("Username");
        userNameLbl.setFont(new Font("Sauce Code Powerline", Font.BOLD, 20));
        userNameLbl.setForeground(Color.WHITE);
        userNameLbl.setBounds(450, 350, 100, 30);
        add(userNameLbl);

        usernameField = new JTextField();
        usernameField.setBounds(550, 350, 136, 30);
        add(usernameField);
        usernameField.setColumns(10);
	
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Sauce Code Powerline", Font.BOLD, 20));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(450, 390, 100, 30);
	
        add(lblPassword);
	
        passwordField = new JPasswordField();
        passwordField.setBounds(550, 390, 136, 30);
        add(passwordField);

        JButton enter = newButton();
        add(enter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(bgimage, 0, 0, null);
    }

    private JButton newButton() {
        JButton enter = new JButton("Enter"); 
        enter.setFont(new Font("Sauce Code Powerline", Font.BOLD, 14));
        enter.setBounds(550, 430, 135, 30);

        /* the ActionListener grabs the next from the username and password fields, runs the password
         * checker, and either loads the next card (the main screen) or produces an error message
         */
        enter.addActionListener(new ActionListener() {
	    @SuppressWarnings("deprecation")
            /* need to change the MD5 method in LoginHandler to get this to work properly */
	    public void actionPerformed(ActionEvent e) {
	        lh.setUsername(usernameField.getText());
	        lh.setPassword(passwordField.getText());
	        if (lh.checkLoginDetails()) {
	    	    GUI.cards.next(GUI.cardPanel);
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
        return enter;
    }
}
