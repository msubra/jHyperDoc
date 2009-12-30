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
public class NorButton extends JButton {
	public NorButton() {
		super("NOR");
		this.addActionListener(new GateAddingAction(Gate.NOR));
	}
}