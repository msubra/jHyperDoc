/*
 * Created on May 5, 2004
 */
package gates;

import java.awt.Graphics2D;

import node.Node;

/**
 * @author maheshexp
 */
public class Not extends Gate {

	public Not() {
		super("NOT", 1, 1, 1, 1, NOT);
	}

	synchronized public void draw(Graphics2D g) {
		simulate();
		super.draw(g);

		g.drawLine(x, y, x, y + height);
		g.drawLine(x, y, x + width - Node.HGAP / 2, y + height / 2);
		g.drawLine(x, y + height, x + width - 4, y + height / 2);

		/* add the bubble */
		g.drawOval(x + width - Node.HGAP / 2, y + height / 2 - 1, 4, 4);
	}

	protected void drawShape(Graphics2D g) {
		g.drawLine(x, y, x, y + height);
		g.drawLine(x, y, x + width - Node.HGAP / 2, y + height / 2);
		g.drawLine(x, y + height, x + width - 4, y + height / 2);

		/* add the bubble */
		g.drawOval(x + width - Node.HGAP / 2, y + height / 2 - 1, 4, 4);
	}

	synchronized public void simulate() {
		boolean newSignal = getInputSignal(0).value();

		this.getOutputSignal(0).value(!newSignal);
		//this.getSignal().value(!newSignal);
	}
}