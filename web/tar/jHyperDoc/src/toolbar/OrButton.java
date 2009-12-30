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
public class OrButton extends JButton {
	public OrButton() {
		super("OR");
		this.addActionListener(new GateAddingAction(Gate.OR));
	}
}