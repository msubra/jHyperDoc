/*
 * Created on May 13, 2004
 */
package gui;

import gates.Clock;
import gates.Gate;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import node.Node;
import properties.Properties;
import util.GlobalUI;

/**
 * @author maheshexp
 */
public class PropertiesWindow extends JInternalFrame {

	InputOutputSpinButton input, output;
	DigitalTextBox label;
	JLabel name;
	FreqencySpinButton freq;
	JLabel l1, l2, l3, l4, l0;
	static JCheckBox bit;

	public PropertiesWindow() {
		super("Properties");
		this.setSize(200, 150);
		init();
		//intially update the contents of CIRCUIT
		DrawingPad pad = MDIWindow.getDrawingPad();
		if (pad != null)
			update(pad.getCircuit());
		this.show();
	}

	private void init() {
		JPanel panel = new JPanel(new GridLayout(6, 2));

		panel.add(l0 = new JLabel(GlobalUI.getText("type")));
		panel.add(name = new JLabel());

		panel.add(l1 = new JLabel(GlobalUI.getText("label")));
		panel.add(label = new DigitalTextBox());

		panel.add(l2 = new JLabel(GlobalUI.getText("inputs")));
		panel.add(input = new InputOutputSpinButton(true));

		panel.add(l3 = new JLabel(GlobalUI.getText("outputs")));
		panel.add(output = new InputOutputSpinButton(false));

		panel.add(l4 = new JLabel(GlobalUI.getText("pulse")));
		panel.add(freq = new FreqencySpinButton(10));
		freq.setEnabled(false);
		
		
		panel.add(new JLabel("Show Bits"));
		panel.add(bit = new JCheckBox(""));

		GlobalUI.add(l0, "type");
		GlobalUI.add(l1, "label");
		GlobalUI.add(l2, "inputs");
		GlobalUI.add(l3, "outputs");
		GlobalUI.add(l4, "pulse");

		this.getContentPane().add(panel);
	}
	
	public static boolean isShowBit() {
		return bit.isSelected();
	}

	public void update(Properties g) {
		if (g == null) {
			g = MDIWindow.getDrawingPad().getCircuit();
		}

		if (g instanceof Clock) {
			freq.setEnabled(true);
		} else {
			freq.setEnabled(false);
		}

		if (g instanceof Gate) {
			input.update(g);
			output.update(g);
			if (g instanceof Clock) {
				freq.update((Clock) g);
			}
		} else {
			input.update(g);
			output.update(g);
		}

		name.setText(g.getName());
		label.setObject(g);
	}

}

class FreqencySpinButton extends JSpinner implements ChangeListener {
	Clock gate;
	public FreqencySpinButton(double step) {
		setModel(new SpinnerNumberModel(10, 10E-5, 1E5, step));
		this.addChangeListener(this);
	}

	public void update(Clock g) {
		this.gate = g;
		this.getModel().setValue(new Double(g.getFrequency()));
	}

	public void stateChanged(ChangeEvent e) {
		gate.setFrequency(((Double) this.getModel().getValue()).doubleValue());
	}

}

/**
 * This is the Spinner Model for changing Inputs and Outputs
 * 
 * @author maheshexp
 */
class InputOutputSpinButton extends JSpinner implements ChangeListener {

	boolean forInput;
	Gate gate;
	int step;

	public InputOutputSpinButton(boolean forInput) {
		this.forInput = forInput;
		this.addChangeListener(this);
	}

	public void update(Properties p) {
		if (p instanceof Gate) {
			update((Gate) p);
		} else if (p instanceof Node) {
			update((Node) p);
		}
	}

	public void update(Node node) {
		gate = null;
		this.setEnabled(false);
		if (forInput == true)
			this.getModel().setValue(new Integer(node.getInputCount()));
		else
			this.getModel().setValue(new Integer(node.getOutputCount()));
	}

	public void update(Gate gate) {
		SpinnerNumberModel model = null;
		this.gate = gate;
		this.setEnabled(true);
		if (forInput == true) {
			model = new SpinnerNumberModel(gate.getInputCount(),
					gate.MIN_INPUTS, gate.MAX_INPUTS, 1);

		} else {
			model = new SpinnerNumberModel(gate.getOutputCount(),
					gate.MIN_OUTPUTS, gate.MAX_OUTPUTS, 1);
		}

		this.setModel(model);
	}

	public void stateChanged(ChangeEvent e) {
		if (gate != null) {
			int value = ((Integer) this.getModel().getValue()).intValue();
			if (forInput) {
				gate.setInputCount(value);
			} else {
				gate.setOutputCount(value);
			}
		}
	}

}

/**
 * This is the editable label for the gate and circuit
 * 
 * @author maheshexp
 */

class DigitalTextBox extends JTextField implements KeyListener {
	Properties object;

	public DigitalTextBox() {
		super("<nothing>");
		this.addKeyListener(this);
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			String text = this.getText();
			text = text.equals("") ? null : text;
			object.setLabel(text);
		}
	}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * Sets the object.
	 * 
	 * @param object
	 *            The object to set
	 */
	public void setObject(Properties object) {
		this.object = object;
		this.setText(object.getLabel());
	}
}