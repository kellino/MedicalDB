package org.ucl.gui;

import java.awt.Graphics;
import javax.swing.JPanel;


public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * overriding the paintComponent(Graphics g) method allows us to set an
	 * image as the background for our JPanel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(LoginScreen.bgimage, 0, 0, getWidth(), getHeight(), this);
	}
}
