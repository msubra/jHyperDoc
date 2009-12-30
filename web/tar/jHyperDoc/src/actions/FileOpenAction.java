/*
 * Created on Jan 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package actions;

import gates.Gate;
import gui.DigitalWindow;
import gui.DrawingPad;
import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import util.GlobalUI;
import util.MessageBox;
import circuit.DigitalCircuit;
import circuit.Module;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FileOpenAction extends JFileChooser implements ActionListener {
	DigitalWindow win;
	File f;

	public FileOpenAction(DigitalWindow win, String filter) {
		super(".");
		this.setDialogTitle("jLogics - File Open");
		this.win = win;
		this.setFileFilter(new JLogicsFileFilter(filter));
	}

	public Gate open() {
		int ret = this.showOpenDialog(win);
		f = this.getSelectedFile();
		

		if (f!= null && !f.exists()) {
			MessageBox.showError(GlobalUI.getText("ERR007"));
			return null;
		}

		if (ret == JFileChooser.APPROVE_OPTION) {
			Gate g = (Gate) JLogicsXML.read(f);
			this.setCurrentDirectory(f);
			return g;
		}
		return null;
	}

	public static Gate open(File f) {
		if (!f.exists()) {
			MessageBox.showError(GlobalUI.getText("ERR007"));
			return null;
		}

		Gate g = (Gate) JLogicsXML.read(f);
		return g;
	}

	public void actionPerformed(ActionEvent arg0) {
		Gate g = open();

		if (g instanceof DigitalCircuit) {
			DigitalWindow win = new DigitalWindow();//create a new window
			DrawingPad pad = win.getDrawingPad();
			pad.setCircuit((DigitalCircuit) g);
			MDIWindow.addWindow(win);
			win.show();
		} else if (g instanceof Module) {
			MDIWindow.getModuleWindow().addModule((Module) g, f);
		}
	}

}