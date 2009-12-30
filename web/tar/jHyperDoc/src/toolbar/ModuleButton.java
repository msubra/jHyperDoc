/*
 * Created on May 5, 2004
 */
package toolbar;

import gui.DigitalWindow;
import gui.DrawingPad;
import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import actions.FileSaveAction;
import circuit.DigitalCircuit;
import circuit.Module;

/**
 * @author maheshexp
 */
public class ModuleButton extends JButton {
	public ModuleButton() {
		super("Module");

		final FileSaveAction save_action = new FileSaveAction(
				MDIWindow.currentWindow, "mod");

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DrawingPad pad = MDIWindow.getDrawingPad();
				DigitalCircuit circuit = pad.getCircuit();
				Module m = new Module(circuit);
				m.toModule();

				DigitalWindow win = new DigitalWindow();
				win.getDrawingPad().getCircuit().add(m);
				save_action.save(m);
				MDIWindow.addWindow(win);
				win.show();
			}
		});

	}
}