/*
 * Created on May 5, 2004
 */
package gates;

import java.awt.Graphics2D;

/**
 * @author maheshexp
 */
public class Nor extends Gate {

	public Nor() {
		super("NOR", NOR);
	}

	synchronized public void draw(Graphics2D g) {
		simulate();
		super.draw(g);

	}

	protected void drawShape(Graphics2D g) {
		g.drawArc(x - 3, y, 5, height, 90, -180);
		g.drawArc(x - width - 3, y, 2 * width, height, 90, -180);

		/* bubble */
		g.drawOval(x + width - 4, y + height / 2 - 1, 4, 4);
	}

	synchronized public void simulate() {
		boolean newSignal = getInputSignal(0).value();
		for (int i = 1; i < inputCount; i++) {
			newSignal = newSignal || getInputSignal(i).value();
		}
		this.getOutputSignal(0).value(!newSignal);
	}

}