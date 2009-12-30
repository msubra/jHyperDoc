/*
 * Created on Feb 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package actions;

import gates.Gate;
import gui.MDIWindow;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import node.Node;
import shape.DigitalShape;
import util.Util;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PropertyUpdateAction extends MouseAdapter {

	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		DigitalShape obj = Util.getComp(p);

		if (obj instanceof Gate) {
			MDIWindow.getPropertyWindow().update((Gate) obj);
		} else {
			MDIWindow.getPropertyWindow().update((Node) obj);
		}

	}
}