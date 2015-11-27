package org.ucl.medicaldb;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.ucl.gui.GUI;

/**
 * the main class for the entire program.
 */
public class Main {
	static Database medDB;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
