package org.ucl.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
//import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame {
    //private static final Logger log = Logger.getLogger(Class.class.getName());

    static JPanel cardPanel;
    static CardLayout cards = new CardLayout();
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("UCL Medical Database @author David Kelly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        GUI gui = new GUI();
        gui.addComponentToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void addComponentToPane(Container pane) {
        LoginScreen login = new LoginScreen();
		
	JPanel MainScreen = mainScreen();
		
	cardPanel = new JPanel();
	cardPanel.setLayout(cards);
	cardPanel.add(login);
	cardPanel.add(MainScreen);
		
	pane.add(cardPanel, BorderLayout.CENTER);
    }


    private JPanel mainScreen() {
        JPanel ms = new JPanel();
        JLabel stuff = new JLabel("nothing to see here");
        ms.add(stuff);
        return ms;
    }
}
