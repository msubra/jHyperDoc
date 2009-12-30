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
public class Flip1Button extends JButton {
	public Flip1Button() {
		super("Flip1");
		this.addActionListener(new GateAddingAction(Gate.FLIP1));
	}
}