package org.ucl.medicaldb;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.ucl.gui.GUI;

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
	        	GUI gui = new GUI();
	        	gui.createAndShowGUI();
		    medDB = new Database();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }
}
