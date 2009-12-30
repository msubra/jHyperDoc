/*
 * Created on May 4, 2004
 */
package gates;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import node.Node;

/**
 * @author maheshexp
 */
public class ByPassGate extends Gate {

	public ByPassGate() {
		super("BYPASS", 1, 1, 1, 1, BYPASS);
	}

	synchronized public void draw(Graphics2D g) {
		simulate();
		super.draw(g);
		g.draw(this);
	}

	synchronized public void simulate() {
		this.getOutputSignal(0).value(this.getInputSignal(0).value());
	}

	public void drawShape(Graphics2D g) {
	}

	public void setOutputComponents(Gate g1) {
		ArrayList cons = g1.getOutputNode(0).connections;
		Node from = this.getOutputNode(0);
		for (Iterator i = cons.iterator(); i.hasNext();) {
			Node to = (Node) i.next();
			from.connect(to);
		}

	}

}