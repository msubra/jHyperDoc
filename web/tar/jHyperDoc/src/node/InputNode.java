/*
 * Created on Apr 15, 2004
 */
package node;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import shape.DigitalObject;
import signal.Signal;

/**
 * @author maheshexp
 */
public class InputNode extends Node {

	public InputNode(DigitalObject parent, int i) {
		super(parent);
		setBorderColor(Color.BLUE);
		id = i;
	}

	public void mouseEntered(MouseEvent e) {
		setBorderColor(Color.RED);
	}

	public void mouseExited(MouseEvent e) {
		setBorderColor(Color.BLUE);
	}

	public void reposition() {
		if (parent.getInputCount() == 1) {
			//only one node, so place in the middle
			x = parent.x - Node.HGAP - width;
			y = parent.y + parent.height / 2 - height / 2;
		} else {
			x = parent.x - Node.HGAP - width;
			y = parent.y + id * (height + Node.VGAP);
		}
	}

	synchronized public void draw(Graphics2D g) {
		super.draw(g);
		int ht = y + height / 2;
		if (parent != null)
			g.drawLine(x + width, ht, parent.x, ht);

	}
	/*
	 * since it is input node, delete the connection of the FROM node, reset the
	 * signal
	 * 
	 * @see shape.DigitalShape#delete()
	 */
	public void delete() {
		//if (from != null)
		//	from.connections.remove(this);
		this.setConnected(false);
		this.signal = new Signal();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shape.SObject#getInputCount()
	 */
	public int getInputCount() {
		return from == null ? 0 : 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shape.SObject#getOutputCount()
	 */
	public int getOutputCount() {
		return 0;
	}

}