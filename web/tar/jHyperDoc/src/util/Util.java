/*
 * Created on Jul 26, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package util;

import gates.Gate;
import gates.LED;
import gui.DrawingPad;
import gui.MDIWindow;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import node.Node;
import parser.EquationCircuit;
import shape.DigitalShape;
import circuit.DigitalCircuit;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Util {

	/**
	 * 
	 * @param g
	 * @param s
	 *            get the boundaryvalues for the shape
	 */
	public static void paintTexture(Graphics2D g, Shape s, DrawingPad pad) {
		Rectangle r = s.getBounds();
		if (r.height <= 0 || r.width <= 0)
			return;
		/**
		 * when the height and width of the symbol is greater than 0 then
		 * GraphicsConfiguration is set to DeviceConnfiguration
		 */

		GraphicsConfiguration config = g.getDeviceConfiguration();
		BufferedImage strip = (BufferedImage) pad.getImage();

		/**
		 * creates an object gstrip for drawing horizontal and vertical strip
		 */
		Graphics2D gStrip = strip.createGraphics();

		horStrip(gStrip, strip, 0, r.width, 0);
		gStrip.dispose();

		Shape prevClip = g.getClip();
		if (!r.equals(s)) {
			g.clip(s);
		}

		vertStrip(g, strip, r.y, r.height, r.x);
		g.setClip(prevClip);
	}

	private static void horStrip(Graphics2D g, BufferedImage src, int x1,
			int x2, int y) {
		int sw = src.getWidth();
		while (x1 < x2 - sw) {
			g.drawImage(src, x1, y, null);
			x1 += sw;
		}

		int sh = src.getHeight();
		if (x2 - x1 > 0)
			g.drawImage(src, x1, y, x2, y + sh, 0, 0, x2 - x1, sh, null);
	}

	/**
	 * 
	 * @param g
	 * @param src
	 *            Refers to the BufferedImage
	 * @param y1
	 * @param y2
	 * @param x
	 */
	private static void vertStrip(Graphics2D g, BufferedImage src, int y1,
			int y2, int x) {
		//get the height for the source
		int sh = src.getHeight();

		while (y1 < y2 - sh) {
			g.drawImage(src, x, y1, null);
			y1 += sh;
		}
		/**
		 * get the width for the source
		 */
		int sw = src.getWidth();
		if (y2 - y1 > 0)
			g.drawImage(src, x, y1, x + sw, y2, 0, 0, sw, y2 - y1, null);
	}

	public static ArrayList getConnectedGates(Gate g) {
		ArrayList v = new ArrayList();

		for (int i = 0; i < g.getOutputCount(); i++) {
			ArrayList cons = null;
			Node n = null;

			if (g instanceof LED) {
				n = g.getInputNode(0);
				if (n.isConnected()) {
					v.add(n.getParent());
				}
			} else {
				n = g.getOutputNode(i);//get outputnode of the gate
				cons = n.getConnections(); //get connections from
				// outputnode
				for (Iterator j = cons.iterator(); j.hasNext();) {
					Node n1 = (Node) j.next();
					if (!v.contains(n1.getParent())) {
						v.add(n1.getParent());//add the inputnode node's parent
					}
				}
			}

		}
		return v;
	}

	static Node getConnectedNode(Gate src, Gate dest) {
		int out = src.getOutputCount();
		for (int k = 0; k < out; k++) {
			Node n1 = src.getOutputNode(k);
			int out1 = dest.getInputCount();
			for (int i = 0; i < out1; i++) {
				if (n1.connections.contains(dest.getInputNode(i))) {
					return n1;
				}
			}
		}
		return null;
	}

	public static void convertEquationToCircuit() {
		String eq = JOptionPane.showInputDialog(MDIWindow.getDrawingPad(),
				"Enter Equation");
		if (eq == null)
			return;
		EquationCircuit eq_cir = new EquationCircuit();

		DigitalCircuit cir = eq_cir.convert(eq);
		MDIWindow.getDrawingPad().setCircuit(cir);
	}

	public static DigitalShape getComp(Point p) {
		return MDIWindow.getDrawingPad().getCircuit().find(p);
	}

	public static void convertToImage() {
		DrawingPad pad = MDIWindow.getDrawingPad();
		
		String path = pad.getCircuit().getLabel();
		if(path == null || path.equals(""))
			path = "circuit" + (int)(Math.random() * 1000);
		
		path += ".png";
		
		Image img = pad.createImage(pad.getWidth(), pad.getHeight());
		Graphics g = img.getGraphics();
		pad.paint(g);
		
		try {
			ImageIO.write( (RenderedImage) img, "PNG" , new File(path) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}