/*
 * Created on May 16, 2004
 */
package toolbar;

import gui.DigitalWindow;
import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author maheshexp
 */
public class NewButton extends JButton {
	public NewButton() {
		super("NEW");

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MDIWindow.addWindow(new DigitalWindow());
			}
		});
	}
}