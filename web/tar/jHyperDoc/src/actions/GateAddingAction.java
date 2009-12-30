/*
 * Created on May 13, 2004
 */
package actions;

import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author maheshexp
 */
public class GateAddingAction implements ActionListener {

	long id;
	public GateAddingAction(long id) {
		this.id = id;
	}

	public void actionPerformed(ActionEvent e) {
		MDIWindow.getDrawingPad().setGateType(id);
	}

}