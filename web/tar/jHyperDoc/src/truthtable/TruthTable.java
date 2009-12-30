/*
 * Created on Feb 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package truthtable;

import gates.DC;
import gates.Gate;
import gates.LED;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import circuit.DigitalCircuit;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TruthTable {

	Vector head;

	public Vector getHeader() {
		return head;
	}

	public Vector generateTable(DigitalCircuit cir) {
		Vector data = new Vector();
		head = new Vector();

		ArrayList dc = cir.getInputComponents();
		ArrayList led = cir.getOutputComponents();

		int input_bits = dc.size();
		int total_inputs = (int) Math.pow(2, input_bits);
		int total_outputs = led.size();

		for (int i = 0; i < total_inputs; i++) {
			Vector row = new Vector();
			String val = "";

			//set the input bits
			for (int k = input_bits - 1; k >= 0; k--) {
				int bit = getBit(i, k);
				DC d = (DC) dc.get(k);
				if (bit == 1) {
					d.switchOn();
				} else {
					d.switchOff();
				}
				row.add(bit + "");
			}

			cir.simulate(); //simulate the circuit after setting all inputs

			//get the output bits
			for (int k = 0; k < total_outputs; k++) {
				LED l = (LED) led.get(k);
				int bit = l.getSignal().isOn() ? 1 : 0;
				row.add(bit + "");
			}

			data.add(row);
		}

		for (Iterator i = dc.iterator(); i.hasNext();) {
			Gate d = (Gate) i.next();
			head.add(d.getLabel());
		}

		for (Iterator i = led.iterator(); i.hasNext();) {
			Gate d = (Gate) i.next();
			head.add(d.getLabel());
		}

		return data;
	}

	int getBit(int num, int pos) {
		return (num & (1 << pos)) >> pos;
	}
}