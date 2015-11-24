package org.ucl.gui;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class MainScreen extends JPanel {

    public MainScreen() {
	setLayout(null);
		
	JMenuBar menuBar = createMenu();
	this.add(menuBar);
    }

    private JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        mb.setBounds(0, 0, 1200, 30);

	JMenu mnFile = new JMenu("File");
	mnFile.setMnemonic(KeyEvent.VK_F);
	mb.add(mnFile);


        JMenu mnEdit = new JMenu("Edit");
        mnEdit.setMnemonic(KeyEvent.VK_E);
        EditListener l = new EditListener();

        /* menu items for the Edit menu */
        JMenuItem menuItem;
        menuItem = mnEdit.add(new JMenuItem("Cut", 't'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        menuItem.addActionListener(l);
        menuItem = mnEdit.add(new JMenuItem("Copy", 'c'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        menuItem.addActionListener(l);
        menuItem = mnEdit.add(new JMenuItem("Paste", 'p'));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
        menuItem.addActionListener(l);
        mb.add(mnEdit);

        JMenu mnAbout = new JMenu("About");
        mnAbout.setMnemonic(KeyEvent.VK_A);
        mb.add(mnAbout);

	JMenu mnHelp = new JMenu("Help");
	mnHelp.setMnemonic(KeyEvent.VK_H);
        mb.add(mnHelp);

        return mb;
    }

    private class EditListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }
    
}
