/*
 * Created on May 3, 2004
 */
package gates;

import java.awt.event.MouseEvent;

/**
 * @author maheshexp
 */
public class ToggleSwitch extends DC {
	
	public ToggleSwitch() {
		super();
	}

	/**
	 * When button is released the signal sets OFF
	 */
	public void mouseReleased(MouseEvent e) {
		if (pad.isSimulatingMode() && MIN_INPUTS == 0) {
			this.switchOff();
		}
	}

	/*
	 * simulate will get the current signal , inverts it and sets to the object
	 * and starts simulating i.e start telling the output nodes that the signal
	 * is changed
	 */
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if (pad.isSimulatingMode() && MIN_INPUTS == 0) {
			switchOn();
		}
	}

}