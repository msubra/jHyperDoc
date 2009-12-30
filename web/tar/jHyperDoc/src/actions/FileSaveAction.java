/*
 * Created on Jan 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package actions;

import gates.Gate;
import gui.DigitalWindow;
import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FileSaveAction extends JFileChooser implements ActionListener {
	DigitalWindow win;
	String filter;

	public FileSaveAction(DigitalWindow win, String filter) {
		super(".");
		this.setDialogTitle("jLogics - File Save");
		this.win = win;
		this.setFileFilter(new JLogicsFileFilter(filter));
		this.filter = filter;
	}

	public void save(Gate component) {
		int ret = this.showSaveDialog(win);

		if (ret == JFileChooser.APPROVE_OPTION) {
			File f = this.getSelectedFile();
			this.setCurrentDirectory(f);
			String filename = f.getAbsolutePath();
			if (!f.getName().endsWith(filter))
				filename = filename + "." + filter;
			JLogicsXML.write(component, new File(filename));
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		save(MDIWindow.getDrawingPad().getCircuit());
	}

}