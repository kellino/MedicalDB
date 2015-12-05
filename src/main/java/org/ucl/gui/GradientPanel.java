package org.ucl.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class GradientPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final float[] fractions = {0.0f, 0.4f, 1.0f};
	final Color[] colours = {new Color(102, 178, 255, 235), new Color(100, 50, 20, 235), new Color(200, 150, 100, 245)};


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int w = getWidth();
		int h = getHeight();
		LinearGradientPaint gp = new LinearGradientPaint(new Point2D.Double(0, 0), new Point2D.Double(w, 0), fractions, colours);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
	}

}
