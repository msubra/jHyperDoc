/*
 * Created on Apr 15, 2004
 */
package node;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import shape.DigitalObject;

/**
 * @author maheshexp
 */
public class OutputNode extends Node {

	public OutputNode(DigitalObject parent, int i) {
		super(parent);
		setBorderColor(Color.MAGENTA);
		id = i;
	}

	public void mouseEntered(MouseEvent e) {
		setBorderColor(Color.BLACK);
	}

	public void mouseExited(MouseEvent e) {
		setBorderColor(Color.MAGENTA);
	}

	public void reposition() {
		if (parent.getOutputCount() == 1) {
			//only one node, so place in the middle
			x = parent.x + parent.width + Node.HGAP;
			y = parent.y + parent.height / 2 - height / 2;
		} else {
			x = parent.x + parent.width + Node.HGAP;
			y = parent.y + id * (height + Node.VGAP);
		}
	}

	synchronized public void draw(Graphics2D g) {
		super.draw(g);
		int ht = y + height / 2;
		//Line from the Object to the OutputNode
		if (parent != null && !(this instanceof ByPassNode))
			g.drawLine(x, ht, parent.x + parent.width, ht);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shape.SObject#getInputCount()
	 */
	public int getInputCount() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shape.SObject#getOutputCount()
	 */
	public int getOutputCount() {
		return connections.size();
	}

}