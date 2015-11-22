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


@SuppressWarnings("serial")
public class GUI extends JFrame {
    JPanel cardPanel;
    CardLayout cards = new CardLayout();
	 
    /**
     * @wbp.parser.entryPoint
     */
    void createAndShowGUI() {
        JFrame frame = new JFrame("Login Screen");
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
        JPanel ls = new JPanel();
        JLabel userNameLbl = new JLabel("Username");
        userNameLbl.setBounds(200, 200, 100, 30);
        ls.add(userNameLbl);

	JButton button = new JButton("Press me");
	button.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		cards.next(cardPanel);
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
