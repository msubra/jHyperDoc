/*
 * Created on May 3, 2004
 */
package gates;

import java.awt.Color;
import java.awt.Graphics2D;

import signal.Signal;

/**
 * @author maheshexp
 */

public class LED extends Gate {

	public LED() {
		super("LED", 1, 0, 1, 0, LED);
	}

	synchronized public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(Color.BLUE);
		g.drawRect(x, y, width, height);

		Signal sig = this.getSignal();

		if (sig != null && sig.value())
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.BLUE);

		g.fillOval(x, y, width, height);

		simulate();
	}

	protected void drawShape(Graphics2D g) {

	}

	synchronized public void simulate() {
		this.setSignal(getInputSignal(0));
		/*
		 * if LED has Output then act as ByPassGate
		 */
		Signal signal = getOutputSignal(0);
		if (signal != null)
			signal.value(this.getSignal().value());
	}

}