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
public class ToggleGateButton extends JButton {
	public ToggleGateButton() {
		super("Toggle Gate");
		this.addActionListener(new GateAddingAction(Gate.TOGGLE));
	}
}