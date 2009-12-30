/*
 * Created on Feb 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package actions;

import gui.MDIWindow;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InternalWindowCloser extends InternalFrameAdapter {
	public void internalFrameClosing(InternalFrameEvent e) {
		MDIWindow.removeWindow(e.getInternalFrame());
	}
}