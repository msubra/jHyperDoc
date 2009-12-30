/*
 * Created on May 5, 2004
 */
package toolbar;

import gui.DrawingPad;
import gui.MDIWindow;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

/**
 * @author maheshexp
 */
public class SimulateButton extends JToggleButton {

	public SimulateButton(final JLogicsToolBar tb) {
		super("Simulate");
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component[] c = tb.getComponents();
				DrawingPad pad = MDIWindow.getDrawingPad();
				boolean flag = true;

				if (pad != null) {
					if (pad.isSimulatingMode()) {
						pad.resetMode();
						pad.getCircuit().stop();
						flag = true;
					} else {
						pad.setMode(DrawingPad.SIMULATING);
						pad.getCircuit().start();
						flag = false;
					}

					//repaint the circuit
					pad.repaint();
				}
			}
		});
	}

}