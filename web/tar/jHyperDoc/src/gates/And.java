/*
 * Created on May 4, 2004
 */
package gates;

import java.awt.Graphics2D;

/**
 * @author maheshexp
 */
public class And extends Gate {

	public And() {
		super("AND", AND);
	}

	synchronized public void draw(Graphics2D g) {
		simulate();
		super.draw(g);

	}

	synchronized public void simulate() {
		boolean newSignal = getInputSignal(0).value();
		for (int i = 1; i < inputCount; i++) {
			newSignal = newSignal && getInputSignal(i).value();
		}

		this.getOutputSignal(0).value(newSignal);
	}

	public void drawShape(Graphics2D g) {
		g.drawLine(x, y, x, y + height);
		g.drawArc(x - width, y, 2 * width, height, 90, -180);
	}

}