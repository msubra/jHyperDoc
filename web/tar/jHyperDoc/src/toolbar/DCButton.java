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
public class DCButton extends JButton {
	public DCButton() {
		super("DC");
		this.addActionListener(new GateAddingAction(Gate.DC));
	}
}