/*
 * Created on Jul 31, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package main;

import gui.MDIWindow;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Main {
	public static MDIWindow win;
	/*
	 * SHIFT + LEFT CLICK on OBJECT => MOVE SHIFT + LEFT CLICK on BYPASS NODE =>
	 * MOVE ALT + LEFT CLICK on NODE => CONNECT CTRL + LEFT CLICK on OBJECT =>
	 * DELETE the OBJECT CTRL + LEFT CLICK on NODE => DELETE all the CONNECTIONS
	 */
	public static void main(String[] args) {
		win = new MDIWindow();
	}
}