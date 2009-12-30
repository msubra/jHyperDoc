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
public class NandButton extends JButton {
	public NandButton() {
		super("NAND");
		this.addActionListener(new GateAddingAction(Gate.NAND));
	}
}