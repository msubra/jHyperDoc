package actions;

import gui.DrawingPad;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;

import shape.DigitalShape;
import circuit.DigitalCircuit;

/**
 * 
 * @author maheshexp
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 * This class is used to create a mouse sensor, which is used to drag/move the
 * whole circuit
 */

public class ScrollSensor implements MouseSensor {

	/**
	 * Referes to which DrawingPad this listener is added
	 */
	transient DrawingPad pad;

	/**
	 * Pivot point for all the components;
	 */
	Point[] pt;

	/**
	 * DigitalCircuit
	 */
	DigitalCircuit circuit;
	/**
	 * contains all the components placed in the circuit
	 */
	Vector comps;

	/**
	 * @param DrawingPad
	 *            for which this listener should be added for scrolling
	 */
	public ScrollSensor(DrawingPad pad) {
		this.pad = pad;
		pad.addMouseListener(this);
		pad.addMouseMotionListener(this);

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Pivot point location is calculated and stored, which will be helpful for
	 * scrolling
	 */
	public void mousePressed(MouseEvent e) {
		DigitalShape sp = pad.getCircuit().find(e.getPoint());
		if (sp == null) {
			circuit = pad.getCircuit();
			comps = circuit.getComponents();
			pt = new Point[comps.size()];

			for (int i = 0; i < pt.length; i++) {
				DigitalShape shape = (DigitalShape) comps.get(i);
				pt[i] = new Point(shape.x - e.getX(), shape.y - e.getY());
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * when mouse is dragged in the circuit component, then the scrolling effect
	 * takes place in the circuit hence the circuit can be scrolled.
	 */
	public void mouseDragged(MouseEvent e) {
		if (pt != null)
			for (int i = 0; i < pt.length; i++) {
				DigitalShape shape = (DigitalShape) comps.get(i);
				int dx = e.getX() + pt[i].x;
				int dy = e.getY() + pt[i].y;
				shape.setLocation(dx, dy);
			}
	}

	public void mouseMoved(MouseEvent e) {
	}

}