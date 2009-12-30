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
public class ByPassButton extends JButton {
	public ByPassButton() {
		super("BYPASS");
		this.addActionListener(new GateAddingAction(Gate.BYPASS));
	}
}