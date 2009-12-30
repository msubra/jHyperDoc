/*
 * Created on May 5, 2004
 */
package toolbar;

import gates.Gate;
import gui.DigitalWindow;
import gui.DrawingPad;
import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;

import circuit.DigitalCircuit;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author maheshexp
 */
public class OpenButton extends JButton implements ActionListener {
	XStream st;
	public OpenButton() {
		super("Open");
		st = new XStream(new DomDriver());
		st.alias("gate", Gate.class);
		st.alias("circuit", DigitalCircuit.class);

		this.addActionListener(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		FileReader fr = null;
		try {

			DigitalCircuit cir;
			fr = new FileReader("temp-cir.xml");
			cir = (DigitalCircuit) st.fromXML(fr);

			DigitalWindow win = new DigitalWindow();//create a new window
			DrawingPad pad = win.getDrawingPad();
			pad.setCircuit(cir);

			MDIWindow.addWindow(win);
			win.show();

			fr.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			try {
				fr.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}

}