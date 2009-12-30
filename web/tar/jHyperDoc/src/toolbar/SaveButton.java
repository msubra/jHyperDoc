/*
 * Created on May 5, 2004
 */
package toolbar;

import gates.Gate;
import gui.MDIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

import javax.swing.JButton;

import circuit.DigitalCircuit;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author maheshexp
 */
public class SaveButton extends JButton implements ActionListener {
	XStream st;

	public SaveButton() {
		super("save");
		this.addActionListener(this);

		st = new XStream(new DomDriver());
		st.alias("gate", Gate.class);
		st.alias("circuit", DigitalCircuit.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream("temp-cir.xml");
			DigitalCircuit cir = MDIWindow.getDrawingPad().getCircuit();
			String s = st.toXML(cir);
			fout.write(s.getBytes());
			fout.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}