package org.ucl.gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame {
    public GUI() {}

    static JPanel cardPanel;
    static CardLayout cards = new CardLayout();
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("UCL Medical Database @author David Kelly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        
        GUI gui = new GUI();
        gui.addComponentToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void addComponentToPane(Container pane) {
        LoginScreen login = new LoginScreen();
		
	MainScreen mainscreen = new MainScreen();
		
	cardPanel = new JPanel();
	cardPanel.setLayout(cards);
	cardPanel.add(login);
	cardPanel.add(mainscreen);
		
	pane.add(cardPanel);
    }
}
