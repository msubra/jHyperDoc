/*
 * Created on May 5, 2004
 */
package toolbar;

import gates.Gate;

import javax.swing.JButton;

import actions.GateAddingAction;

/**
 * @author maheshexp
 */
public class ClockButton extends JButton {
	public ClockButton() {
		super("CLOCK");
		this.addActionListener(new GateAddingAction(Gate.CLOCK));
	}
}