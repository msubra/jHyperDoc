package gui;

import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import actions.InternalWindowCloser;

public class DigitalWindow extends JInternalFrame implements FocusListener {
	protected Container pane = this.getContentPane();
	DrawingPad pad;

	public DigitalWindow() {
		super("Window", true, true, true, true);
		this.setSize(700, 400);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addFocusListener(this);
		this.addInternalFrameListener(new InternalWindowCloser());

		pad = new DrawingPad();
		pane.add(pad);

		this.setFocusable(true);

		this.validate();
		this.show();
	}

	public DrawingPad getDrawingPad() {
		return pad;

	}

	public void focusGained(FocusEvent arg0) {
		MDIWindow.currentWindow = this;
		pad.requestFocus();
	}

	public void focusLost(FocusEvent arg0) {
	}

}