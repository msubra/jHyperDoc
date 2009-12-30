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
public class XorButton extends JButton {
	public XorButton() {
		super("XOR");
		this.addActionListener(new GateAddingAction(Gate.XOR));
	}
}