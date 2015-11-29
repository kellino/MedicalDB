package org.ucl.medicaldb;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.UIManager;
import org.ucl.gui.GUI;

/**
 * the main class for the entire program.
 */
public class Main {
	public static Database medDB;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Sans Serif", Font.PLAIN, 14));
		} catch (Throwable e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/**
					 * we need to initialize the database before the GUI in
					 * order to have to data already available. Otherwise the
					 * GUI cannot fill in its fields
					 */
					medDB = new Database();
					GUI gui = new GUI();
					gui.createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
