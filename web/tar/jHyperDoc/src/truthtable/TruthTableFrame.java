/*
 * Created on Feb 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package truthtable;

import gui.MDIWindow;

import java.awt.Container;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import actions.InternalWindowCloser;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TruthTableFrame extends JInternalFrame {
	protected Container pane = this.getContentPane();

	public TruthTableFrame() {
		super("TruthTable", true, true);
		this.setSize(200, 300);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addInternalFrameListener(new InternalWindowCloser());

		this.setFocusable(true);

		TruthTable t = new TruthTable();
		Vector data = t.generateTable(MDIWindow.getDrawingPad().getCircuit());
		JTable tbl = new JTable(data, t.getHeader());

		pane.add(new JScrollPane(tbl));

		this.validate();
		this.show();
	}

}