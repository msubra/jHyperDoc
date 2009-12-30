/*
 * Created on Apr 14, 2004
 */
package shape;

import gui.DrawingPad;
import gui.MDIWindow;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import signal.Signal;
import util.Util;
import actions.MouseSensor;
import circuit.DigitalCircuit;

/**
 * @author maheshexp
 */

public abstract class DigitalShape extends Rectangle implements MouseSensor {
	/**
	 * defines an object DEFAULT_CURSOR DEFAULT_CURSOR defines CROSSHAIR_CURSOR
	 */
	transient public static final Cursor DEFAULT_CURSOR = Cursor
			.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);

	transient public static final Cursor HAND_CURSOR = Cursor
			.getPredefinedCursor(Cursor.HAND_CURSOR);
	/**
	 * declare variables for Color,Signal
	 */
	protected Color border;
	protected Signal signal;

	/**
	 * Refers to the drawing pad on which the action is performed
	 */
	transient protected DrawingPad pad;
	protected DigitalCircuit circuit;

	/**
	 * specifies the parameters for the shape of the digitalcircuits
	 * 
	 * @param width
	 * @param height
	 */
	public DigitalShape(int width, int height) {
		super(0, 0, width, height);
		signal = new Signal();
	}

	public DigitalCircuit getCircuit() {
		return circuit;
	}

	public void setCircuit(DigitalCircuit cir) {
		this.circuit = cir;
		this.pad = cir.getDrawingPad();
	}

	public abstract void delete();

	/**
	 * 
	 * @param g
	 *            implements the Graphics2D if signal is true then color of
	 *            signal is set to green else it is set to bordercolor
	 */
	synchronized public void draw(Graphics2D g) {
		if (signal != null && signal.value()) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(getBorderColor());
		}

		if (pad != null) {
			Util.paintTexture(g, this, pad);
		}
	}
	/**
	 * 
	 * @param p
	 *            is used for locating the position of the symbols
	 * @return
	 */
	public abstract DigitalShape find(Point p);

	public Color getBorderColor() {
		return border;
	}

	public Signal getSignal() {
		return signal;
	}

	/**
	 * This Function can be used in child classes for implementing Mouseclick
	 */

	public void mouseClicked(MouseEvent e) {
	}
	/**
	 * This Function can be used in child classes for implementing
	 * MoussDragedEvent
	 */
	public void mouseDragged(MouseEvent e) {
	}
	/**
	 * This Function can be used in child classes for implementing
	 * MouseEnteredEvent
	 */
	public void mouseEntered(MouseEvent e) {
	}
	/**
	 * This Function can be used in child classes for implementing
	 * MouseExitEvent
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * This Function can be used in child classes for implementing
	 * MouseMovedEvent
	 */

	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * CTRL + Gate => DELETE Object CTRL + Node => DELETE Connections SHIFT =>
	 * Start Moving Object CLICK ON NODE => Start connecting objects
	 * 
	 * else No Action
	 */
	public void mousePressed(MouseEvent e) {
		//if under Simulation donot perform anyother actions
		if (MDIWindow.getDrawingPad().isSimulatingMode())
			return;

		//if CTRL is pressed, then delete the object
		if (e.isControlDown()) {
			this.delete();
			MDIWindow.getDrawingPad().resetMode(); //reset the mode
		} else if (e.isShiftDown()) {
			//if SHIFT is pressed then MOVE the object
			MDIWindow.getDrawingPad().setMode(DrawingPad.MOVING);
		} else {
			//do nothing
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	//abstract function to reposition
	public abstract void reposition();

	/**
	 * @param color
	 */
	public void setBorderColor(Color color) {
		border = color;
	}

	public void setSignal(Signal s) {
		signal = s;
	}

	/**
	 *  
	 */
	public String toString() {
		return super.toString() + ":" + hashCode();
	}

	/**
	 * Refers to the location to which the symbol is to be placed
	 */
	public void setLocation(Point p) {
		this.setLocation(p.x, p.y);
	}
}