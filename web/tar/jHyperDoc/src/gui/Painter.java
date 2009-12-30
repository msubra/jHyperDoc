/*
 * Created on Jan 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Painter extends Thread {

	class PaintEvent implements AWTEventListener {
		public void eventDispatched(AWTEvent e) {
			MDIWindow.getDrawingPad().repaint();
		}
	}
	
	class AutoPainter extends Thread{
		public AutoPainter() {
			this.setPriority(Thread.MAX_PRIORITY);
		}
		
		public void run() {
			while(true) {
				try {
					Thread.sleep(250);
					MDIWindow.getDrawingPad().repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void run() {
		Toolkit.getDefaultToolkit().addAWTEventListener(
				new PaintEvent(),
				AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
	}

}