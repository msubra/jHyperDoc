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
public class AndButton extends JButton {
	public AndButton() {
		super("AND");
		this.addActionListener(new GateAddingAction(Gate.AND));
	}
}