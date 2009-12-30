/*
 * Created on May 5, 2004
 */
package gates;

import java.awt.Graphics2D;

/**
 * @author maheshexp
 */
public class Nand extends Gate {

	public Nand() {
		super("NAND", NAND);
	}

	synchronized public void draw(Graphics2D g) {
		simulate();
		super.draw(g);
	}

	protected void drawShape(Graphics2D g) {
		g.drawLine(x, y, x, y + height);
		g.drawArc(x - width, y, 2 * width, height, 90, -180);

		/* bubble */
		g.drawOval(x + width, y + height / 2 - 1, 4, 4);
	}

	synchronized public void simulate() {
		boolean newSignal = getInputSignal(0).value();
		for (int i = 1; i < inputCount; i++) {
			newSignal = newSignal && getInputSignal(i).value();
		}
		this.getOutputSignal(0).value(!newSignal);
		//this.getSignal().value(!newSignal);
	}

}