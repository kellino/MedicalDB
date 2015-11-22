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
	 
	public void addComponentToPane(Container pane) {
		JPanel LoginScreen = new JPanel();
		JLabel Username = new JLabel("Username");
		LoginScreen.add(Username);
		
		JButton button = new JButton("Press me");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.next(cardPanel);
			}
		});
		LoginScreen.add(button);
		
		JPanel MainScreen = new JPanel();
		JLabel StuffHere = new JLabel("Stuff");
		MainScreen.add(StuffHere);
		
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		cardPanel.add(LoginScreen);
		cardPanel.add(MainScreen);
		
		pane.add(cardPanel, BorderLayout.CENTER);
	}
	
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
}
