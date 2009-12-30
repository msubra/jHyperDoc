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
public class NotButton extends JButton {
	public NotButton() {
		super("NOT");
		this.addActionListener(new GateAddingAction(Gate.NOT));
	}
}