/*
 * Created on May 5, 2004
 */
package toolbar;

import gui.DrawingPad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;

import circuit.DigitalCircuit;

/**
 * @author maheshexp
 */
public class LoadButton extends JButton implements ActionListener {
	DrawingPad pad;
	public LoadButton(DrawingPad pad) {
		super("load");
		this.addActionListener(this);
		this.pad = pad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			FileInputStream fin = new FileInputStream("temp1");
			ObjectInputStream in = new ObjectInputStream(fin);
			DigitalCircuit circuit = (DigitalCircuit) in.readObject();
			fin.close();
			pad.setCircuit(circuit);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

}