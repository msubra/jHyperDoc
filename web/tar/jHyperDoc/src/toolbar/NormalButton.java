/*
 * Created on May 5, 2004
 */
package toolbar;

import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * @author maheshexp
 */
public class NormalButton extends JButton {
	public NormalButton() {
		super("Normal");

		Action action = new AbstractAction("normal") {
			{
				putValue(Action.SHORT_DESCRIPTION, "Normal Mode");
			}

			public void actionPerformed(ActionEvent evt) {
				MDIWindow.getDrawingPad().resetMode();
			}
		};

		this.addActionListener(action);

		// Register keystroke
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				action.getValue(Action.NAME));

		// Register action
		this.getActionMap().put(action.getValue(Action.NAME), action);
	}
}