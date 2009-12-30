/*
 * Created on Apr 21, 2004
 */
package node;

import gui.MDIWindow;

import java.awt.event.MouseEvent;

import shape.DigitalObject;

/**
 * ByPass node is used to make multiple connections.
 * 
 * @author maheshexp
 */
public class ByPassNode extends OutputNode {
	int x1, y1, x2, y2;

	public ByPassNode(DigitalObject p) {
		super(p, 0);
	}

	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if (MDIWindow.getDrawingPad().isMovingMode()) {
			x2 = x1 + e.getX();
			y2 = y1 + e.getY();
			this.setLocation(x2, y2);
		}
	}

	/*
	 * store the location where the mouse is pressed canMove falg will be TRUE
	 * for one click and FALSE for another click
	 */

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		x1 = x - e.getX();
		y1 = y - e.getY();
	}

}