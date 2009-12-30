/*
 * Created on May 3, 2004
 */
package gates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import node.Node;

/**
 * @author maheshexp
 */
public class DC extends Gate {

	public DC() {
		this("DC");
	}
	
	public DC(String name) {
		super(name, 0, 1, 0, 1, DC);
		setBorderColor(Color.BLUE);
	}

	public void mouseEntered(MouseEvent e) {
		if (pad.isSimulatingMode())
			pad.setCursor(HAND_CURSOR);
	}

	public void mouseExited(MouseEvent e) {
		pad.setCursor(DEFAULT_CURSOR);
	}

	public void switchOn() {
		this.getSignal().value(true);
	}

	public void switchOff() {
		this.getSignal().value(false);
	}

	/*
	 * simulate will get the current signal , inverts it and sets to the object
	 * and starts simulating i.e start telling the output nodes that the signal
	 * is changed
	 */
	public void mousePressed(MouseEvent e) {
		if (pad.isSimulatingMode() && MIN_INPUTS == 0) {
			if (this.getSignal().value() == true) {
				this.switchOff();
			} else {
				this.switchOn();
			}
		} else {
			super.mousePressed(e);
		}
	}

	synchronized public void draw(Graphics2D g) {
		simulate();
		super.draw(g);
		g.drawOval(x, y, width, height);

		drawShape(g);

	}

	protected void drawShape(Graphics2D g) {
		int h, w;
		int delta = Node.HGAP;

		w = x;
		h = y + height - delta;
		g.drawLine(w, h, w + delta, h);

		w += delta;
		g.drawLine(w, h, w, h - delta);

		h -= delta;
		g.drawLine(w, h, w + delta, h);

		w += delta;
		g.drawLine(w, h, w, h + delta);

		h += delta;
		g.drawLine(w, h, w + delta, h);
	}

	synchronized public void simulate() {
		/*
		 * if DC has MIN_INPUTS == 1 then act as ByPassGate
		 */
		if (circuit == null)
			return;

		if (pad == null)
			pad = circuit.getDrawingPad();

		if (pad != null && pad.isSimulatingMode() && MIN_INPUTS == 1) {
			boolean newSignal = getInputSignal(0).value();
			this.getSignal().value(newSignal);
		}
	}
}