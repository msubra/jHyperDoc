/*
 * Created on May 13, 2004
 */
package gates;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import node.Node;

/**
 * @author maheshexp
 */
public class Clock extends DC { //implements Runnable {

	double freq = 10;
	transient Timer t;

	public Clock() {
		super("CLOCK");
	}

	public void setFrequency(double freq) {
		this.freq = freq;
	}

	public double getFrequency() {
		return freq;
	}

	public void stop() {
		t.stop();
		t = null;
	}

	protected void drawShape(Graphics2D g) {
		int h, w;
		int delta = 2 * Node.HGAP;

		w = x;
		h = y + height - delta;
		g.drawLine(w, h, w + delta, h);

		w += delta;
		g.drawLine(w, h, w, h - delta);
	}

	/**
	 * start a time for the given frequency interval
	 *  
	 */
	public void start() {
		t = new Timer((int) (1000 / freq), new ActionListener() {
			//t = new Timer((int) (freq), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pad.isSimulatingMode()) {
					if (Clock.this.getSignal().isOn()) {
						switchOff();
					} else {
						switchOn();
					}
				}
				pad.repaint();
			}
		});
		t.start();
	}

}